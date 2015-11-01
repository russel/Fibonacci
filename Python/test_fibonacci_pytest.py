#!/usr/bin/env py.test-3
# -*- coding:utf-8; -*-

import pytest
from fibonacci import (
    Sequence,
    naïveRecursive,
    tailRecursive,
    memoizedRecursive,
    iterative,
    generator,
    reduction,
    lambdaReduce,
    calculate,
)

'''
Example-based tests, using pytest, for the various Fibonacci implementations.
'''

__author__ = 'Russel Winder'
__date__ = '2015-11-01'
__version__ = '1.1'
__copyright__ = 'Copyright © 2015  Russel Winder'
__licence__ = 'GNU Public Licence (GPL) v3'

algorithms = (
    naïveRecursive,
    tailRecursive,
    memoizedRecursive,
    iterative,
    generator,
    reduction,
    lambdaReduce,
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


def test_sequence_iterator():
    iterator = iter(Sequence())
    for index, datum in data:
        assert next(iterator) == datum


def test_sequence_positiveIndex_randomSequenceOnSameInstance():
    f = Sequence()
    for i in (7, 4, 7, 12, 8, 20, 13, 7, 4):
        assert f[i] == data[i][1]


@pytest.mark.parametrize(('index', 'value'), data)
def test_positive_sequence(index, value):
    assert Sequence()[index] == value


@pytest.mark.parametrize(('index', 'value'), data)
def test_negative_sequence(index, value):
    if value != 0:
        with pytest.raises(ValueError):
            Sequence()[-index]
    else:
        assert Sequence()[-index] == value


dataset = [(a, i, r) for a in algorithms for i, r in data]


@pytest.mark.parametrize(('algorithm', 'index', 'value'), dataset)
def test_positive_algorithm(algorithm, index, value):
    assert algorithm(index) == value


@pytest.mark.parametrize(('algorithm', 'index', 'value'), dataset)
def test_negative_algorithm(algorithm, index, value):
    if value != 0:
        with pytest.raises(ValueError):
            algorithm(-index)
    else:
        assert algorithm(-index) == value
