#!/usr/bin/env python3

from pytest import mark, raises

from hypothesis import assume, given
from hypothesis.strategies import integers, floats, text

from fibonacci import (
    Sequence,
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

'''
Property-based tests, using Hypothesis and pytest, for the various Fibonacci implementations.
'''

__author__ = 'Russel Winder'
__date__ = '2016-11-23'
__version__ = '1.1'
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
    calculate,
)

_min_value = 0
_max_value = 300


@mark.parametrize('a', algorithms)
def test_base_cases(a):
    assert a(0) == 0
    assert a(1) == 1


@mark.parametrize('a', algorithms)
@given(integers(min_value=_min_value, max_value=_max_value))
def test_non_negative_integer(a, x):
    # Do not test naïve_recursive for large values due to the exponential time behaviour.
    if x < 20 or a != naïve_recursive:
        assert a(x + 2) == a(x + 1) + a(x)


@mark.parametrize('a', algorithms)
@given(integers(max_value=-1))
def test_negative_integer_causes_ValueError(a, x):
    with raises(ValueError):
        a(x)


@mark.parametrize('a', algorithms)
@given(floats())
def test_float_causes_TypeError(a, x):
    with raises(TypeError):
        a(x)


@mark.parametrize('a', algorithms)
@given(text())
def test_string_causes_TypeError(a, x):
    with raises(TypeError):
        a(x)


def test_sequence_iterator():
    iterator = iter(Sequence())
    minus2 = next(iterator)
    minus1 = next(iterator)
    for i in range(100):
        this = next(iterator)
        assert this == minus1 + minus2
        minus2, minus1 = minus1, this


@given(integers(min_value=_min_value, max_value=_max_value))
def test_sequence_non_negative_integer(i):
    f = Sequence()
    assert f[i + 2] == f[i + 1] + f[i]


@given(integers(max_value=-1))
def test_sequence_negative_integer_causes_ValueError(x):
    f = Sequence()
    with raises(ValueError):
        f[x]


@given(floats())
def test_sequence_float_causes_TypeError(x):
    f = Sequence()
    with raises(TypeError):
        f[x]


@given(text())
def test_sequence_string_causes_TypeError(x):
    f = Sequence()
    with raises(TypeError):
        f[x]


if __name__ == '__main__':
    from pytest import main
    main()
