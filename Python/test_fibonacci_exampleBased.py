"""
Example-based tests, using pytest, for the various Fibonacci implementations.
"""

from pytest import mark, raises

from fibonacci import (
    Sequence,
    naïve_recursive,
    tail_recursive,
    memoized_recursive,
    iterative,
    generator,
    reduction,
    lambda_reduce,
    calculate,
)

__author__ = 'Russel Winder'
__date__ = '2016-06-16'
__version__ = '1.2.0'
__copyright__ = 'Copyright © 2015, 2016  Russel Winder'
__licence__ = 'GNU Public Licence (GPL) v3'

algorithms = (
    naïve_recursive,
    tail_recursive,
    memoized_recursive,
    iterative,
    generator,
    reduction,
    lambda_reduce,
    calculate,
)

data = (
    (0, 0),
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 5),
    (6, 8),
    (7, 13),
    (8, 21),
    (9, 34),
    (10, 55),
    (11, 89),
    (12, 144),
    (13, 233),
    (14, 377),
    (15, 610),
    (16, 987),
    (17, 1597),
    (18, 2584),
    (19, 4181),
    (20, 6765),
)

dataset = [(a, i, r) for a in algorithms for i, r in data]


@mark.parametrize(('a', 'x', 'r'), dataset)
def test_non_negative_integer(a, x, r):
    assert a(x) == r


@mark.parametrize(('a', 'x', 'r'), dataset)
def test_negative_integer(a, x, r):
    if r != 0:
        with raises(ValueError):
            a(-x)
    else:
        assert a(-x) == r


def test_sequence_iterator():
    iterator = iter(Sequence())
    for index, datum in data:
        assert next(iterator) == datum


def test_sequence_positiveIndex_randomSequenceOnSameInstance():
    f = Sequence()
    for i in (7, 4, 7, 12, 8, 20, 13, 7, 4):
        assert f[i] == data[i][1]


@mark.parametrize(('index', 'value'), data)
def test_positive_sequence(index, value):
    assert Sequence()[index] == value


@mark.parametrize(('index', 'value'), data)
def test_negative_sequence(index, value):
    if value != 0:
        with raises(ValueError):
            Sequence()[-index]
    else:
        assert Sequence()[-index] == value
