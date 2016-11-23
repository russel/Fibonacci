"""
Benchmarks for the various algorithms.
"""

from pytest import mark

from fibonacci import (
    naïve_recursive,
    tail_recursive,
    memoized_recursive,
    iterative,
    generator,
    reduction,
    lambda_reduce,
    zipping,
    calculate,
)

__author__ = 'Russel Winder'
__date__ = '2016-11-23'
__version__ = '1.0'
__copyright__ = 'Copyright © 2016  Russel Winder'
__licence__ = 'GNU Public Licence (GPL) v3'


algorithms = (
    naïve_recursive,
    tail_recursive,
    memoized_recursive,
    iterative,
    generator,
    reduction,
    lambda_reduce,
    zipping,
    calculate,
)


@mark.parametrize('a', algorithms)
def test_benchmark(a, benchmark):
    benchmark(a, 18)
