"""
A collection of functions each of which returns the nth entry in the Fibonacci Series.

The Fibonacci Series is defined by the recurrence relation:

  F_0 = 0
  F_1 = 1
  F_n = F_{n-1} + F_{n-2}, n > 1
"""

from typing import Iterator

__author__ = 'Russel Winder'
__date__ = '2016-06-28'
__version__ = '1.4.1'
__copyright__ = 'Copyright © 2015, 2016  Russel Winder'
__licence__ = 'GNU Public Licence (GPL) v3'


def _validate(n: int) -> None:
    if not isinstance(n, int):
        raise TypeError('Argument must be an integer.')
    if n < 0:
        raise ValueError('Argument must be a non-negative integer.')


def naïve_recursive(n: int) -> int:
    """
    The obvious, but naïve, recursive implementation. This implementation has, of course, exponential
    behaviour which means this is a fundamentally useless implementation.
    """
    _validate(n)
    return n if n < 2 else naïve_recursive(n - 1) + naïve_recursive(n - 2)


def tail_recursive(n: int) -> int:
    """
    Functional programming generally emphasizes tail recursion as a replacement for iteration.
    Sadly Python does not perform tail recursion optimization, so there is still stack usage.
    """
    _validate(n)

    def iterate(i: int, r: int = 0, n: int = 1) -> int:
        return r if i == 0 else iterate(i - 1, n, r + n)

    return iterate(n)


# For a memoize decorator or three see
# https://wiki.python.org/moin/PythonDecoratorLibrary#Memoize
#
# Just use a manual memo for here though.

memo = {0: 0, 1: 1}


def memoized_recursive(n: int) -> int:
    """
    The obvious recursive implementation but using memoization so as to avoid the exponential behaviour
    that is the problem with the recursive implementation in the case that memoization is not used.
    """
    _validate(n)
    if n not in memo:
        memo[n] = memoized_recursive(n - 1) + memoized_recursive(n - 2)
    return memo[n]


def iterative(n: int) -> int:
    """The obvious iterative implementation using a simple loop."""
    _validate(n)
    result, next = 0, 1
    for i in range(n):
        result, next = next, result + next
    return result


def generator(n: int) -> int:
    """An implementation using a nested generator implementing the Fibonacci Sequence."""
    _validate(n)

    def the_generator() -> Iterator[int]:
        result, next = 0, 1
        while True:
            yield result
            result, next = next, result + next

    fibonacci_sequence = the_generator()
    for i in range(n):
        next(fibonacci_sequence)
    return next(fibonacci_sequence)


class Sequence:
    """A class realizing a lazily evaluated list holding the values comprising the Fibonacci Sequence."""
    _sequence = [0, 1]

    def __getitem__(self, index):
        _validate(index)
        if index >= len(Sequence._sequence):
            for i in range(len(Sequence._sequence), index + 1):
                Sequence._sequence.append(Sequence._sequence[i - 1] + Sequence._sequence[i - 2])
        return Sequence._sequence[index]

    def __iter__(self):

        class Iterator:
            def __init__(self, parent):
                self.index = 0
                self.parent = parent

            def __iter__(self):
                return self

            def __next__(self):
                return_value = self.parent[self.index]
                self.index += 1
                return return_value

        return Iterator(self)


# Something akin to the following, and others not dissimilar, can be found at
# http://stackoverflow.com/questions/4935957/fibonacci-numbers-with-an-one-liner-in-python-3

def reduction(n: int) -> int:
    """Use the reduce function to realize an iterative solution."""
    _validate(n)
    if n < 2:
        return n
    from functools import reduce
    return reduce(lambda x, _: (x[1], x[0] + x[1]), ((0, 1) for _ in range(n)))[1]


def lambda_reduce(n: int) -> int:
    """A second alternative using lambdas and reduce."""
    _validate(n)
    if n < 2:
        return n
    from functools import reduce
    return (lambda i: reduce(lambda x, _: [x[1], x[0] + x[1]], range(i), [0, 1]))(n)[0]


# TODO This appears to fail for n == 69
def calculate(n: int) -> int:
    """Use the closed form formula, Binet's Formula."""
    _validate(n)
    from math import sqrt
    sqrt5 = sqrt(5)
    return int(((1 + sqrt5) ** n - (1 - sqrt5) ** n) / (2 ** n * sqrt5) + 0.5)
