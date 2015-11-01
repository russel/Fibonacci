#! /usr/bin/env python3
# -*- coding:utf-8; -*-

import unittest

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
Example-based tests, using unittest, for the various Fibonacci implementations.
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


class Fibonacci_Test(unittest.TestCase):
    def test_sequenceIterator(self):
        iterator = iter(Sequence())
        for d in data:
            self.assertEqual(next(iterator), d[1])

    def test_sequence_positiveIndex_randomSequenceOnSameInstance(self):
        f = Sequence()
        for i in (7, 4, 7, 12, 8, 20, 13, 7, 4):
            self.assertEqual(f[i], data[i][1])


def installPositiveTest(a, d):
    def _test(self):
        self.assertEqual(a(d[0]), d[1])
    setattr(Fibonacci_Test, 'test_positive_{}_{}'.format(a.__name__, d[0]), _test)


def installNegativeTest(a, d):
    def _test(self):
        self.assertRaises(ValueError, a, -d)
    setattr(Fibonacci_Test, 'test_negative_{}_{}'.format(a.__name__, d), _test)


def installPositiveSequenceTest(d):
    def _test(self):
        self.assertEqual(Sequence()[d[0]], d[1])
    setattr(Fibonacci_Test, 'test_positive_Sequence_{}'.format(d), _test)


def installNegativeSequencetest(d):
    def _test(self):
        self.assertRaises(ValueError, Sequence().__getitem__, -d)
    setattr(Fibonacci_Test, 'test_negative_Sequence_{}'.format(d), _test)


for a in algorithms:
    for d in data:
        installPositiveTest(a, d)
        installPositiveSequenceTest(d)
    for d in range(1, 20):
        installNegativeTest(a, d)
        installNegativeSequencetest(d)


if __name__ == '__main__':
    unittest.main()
