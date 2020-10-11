use BigInteger;
use UnitTest;

use Fibonacci;

// Since the Fibonacci functions are generic we have to use a technique to disambiguate the
// overloads at the call site. So create some procedures that can be function references
// dealing with the choice of overload.
//
// sequence creates an iterator over bigint, it is not a function returning a bigint, so test it
// separately.

proc iterative_f(n: int): bigint throws return iterative(n);
proc naive_recursive_f(n:int): bigint throws return naive_recursive(n);
proc tail_recursive_f(n:int): bigint throws return tail_recursive(n);
proc library_fib_f(n:int): bigint throws return library_fib(n);

const functions = (
  iterative_f,
  naive_recursive_f,
  tail_recursive_f,
  library_fib_f,
);

const positive_data = (
  (0, new bigint(0)),
  (1, new bigint(1)),
  (2, new bigint(1)),
  (3, new bigint(2)),
  (4, new bigint(3)),
  (5, new bigint(5)),
  (6, new bigint(8)),
  (7, new bigint(13)),
  (8, new bigint(21)),
  (9, new bigint(34)),
  (10, new bigint(55)),
  (11, new bigint(89)),
  (12, new bigint(144)),
  (13, new bigint(233)),
  (14, new bigint(377)),
  (15, new bigint(610)),
  (16, new bigint(987)),
  (17, new bigint(1597)),
  (18, new bigint(2584)),
  (19, new bigint(4181)),
  (20, new bigint(6765)),
);

proc positive_arguments(test: borrowed Test) throws {
  for f in functions {
    for (n, r) in positive_data {
      test.assertEqual(f(n), r);
    }
  }
}

proc sequence_positive_arguments(test: borrowed Test) throws {
  for (i, v) in zip(0.., sequence(20)) {
    test.assertEqual(v, positive_data[i][1]);
  }
}

const negative_data = (-1, -2, -5, -10, -20, -100);

proc negative_arguments(test: borrowed Test) throws {
  for f in functions {
    for n in negative_data {
      try { f(n); }
      catch iae: IllegalArgumentError { }
    }
  }
}

proc sequence_negative_arguments(test: borrowed Test) throws {
  for n in negative_data {
    try { sequence(n); }
    catch iae: IllegalArgumentError { }
  }
}

UnitTest.main();
