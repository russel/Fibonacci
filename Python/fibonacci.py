# -*- coding:utf-8; -*-

'''
A collection of functions each of which returns the nth entry in the Fibonacci Series.

The Fibonacci Series is defined by the recurrence relation:

  F_0 = 0
  F_1 = 1
  F_n = F_{n-1} + F_{n-2}, n > 1
'''

__author__ = 'Russel Winder'
__date__ = '2015-11-01'
__version__ = '1.2'
__copyright__ = 'Copyright © 2015  Russel Winder'
__licence__ = 'GNU Public Licence (GPL) v3'


def _validate(n):
    if not isinstance(n, int):
        raise TypeError('Argument must be an integer.')
    if n < 0:
        raise ValueError('Argument must be a non-negative integer.')


def naïveRecursive(n):
    '''
    The obvious, but naïve, recursive implementation. This implementation has, of course, exponential
    behaviour which means this is a fundamentally useless implementation.
    '''
    _validate(n)
    return n if n < 2 else naïveRecursive(n - 1) + naïveRecursive(n - 2)


def tailRecursive(n):
    '''
    Functional programming generally emphasizes tail recursion as a replacement for iteration.
    Sadly Python does not perform tail recursion optimization, so there is still stack usage.
    '''
    _validate(n)

    def iterate(i, r=0, n=1):
        return r if i == 0 else iterate(i - 1, n, r + n)

    return iterate(n)


memo = {0: 0, 1: 1}


def memoizedRecursive(n):
    '''
    The obvious recursive implementation but using memoization so as to avoid the exponential behaviour
    that is the problem with the recursive implementation in the case that memoization is not used.
    '''
    _validate(n)
    if n not in memo:
        memo[n] = memoizedRecursive(n - 1) + memoizedRecursive(n - 2)
    return memo[n]


def iterative(n):
    '''The obvious iterative implementation using a simple loop.'''
    _validate(n)
    result, next = 0, 1
    for i in range(n):
        result, next = next, result + next
    return result


def generator(n):
    '''An implementation using a nested generator implementing the Fibonacci Sequence.'''
    _validate(n)

    def theGenerator():
        result, next = 0, 1
        while True:
            yield result
            result, next = next, result + next

    fibonacciSequence = theGenerator()
    result = 0
    for i in range(n + 1):
        result = next(fibonacciSequence)
    return result


class Sequence:
    '''A class realizing a lazily evaluated list holding the values comprising the Fibonacci Sequence.'''
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
                returnValue = self.parent[self.index]
                self.index += 1
                return returnValue

        return Iterator(self)


# Something akin to the following, and others not dissimilar, can be found at
# http://stackoverflow.com/questions/4935957/fibonacci-numbers-with-an-one-liner-in-python-3

def reduction(n):
    '''Use the reduce function to realize an iterative solution.'''
    _validate(n)
    if n < 2:
        return n
    from functools import reduce
    return reduce(lambda x, _: (x[1], x[0] + x[1]), ((0, 1) for _ in range(n)))[1]


def lambdaReduce(n):
    '''A second alternative using lambdas and reduce.'''
    _validate(n)
    if n < 2:
        return n
    from functools import reduce
    return (lambda i: reduce(lambda x, _: [x[1], x[0] + x[1]], range(i), [0, 1]))(n)[0]


def calculate(n):
    '''Use the closed form formula.'''
    _validate(n)
    from math import sqrt
    sqrt5 = sqrt(5)
    return int(((1 + sqrt5) / 2) ** n / sqrt5 + 0.5)