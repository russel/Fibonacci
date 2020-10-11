/*
 * A collection of functions each of which returns the nth entry in the Fibonacci Series.
 *
 * The Fibonacci Series is defined by the recurrence relation:
 *
 *  F_0 = 0
 *  F_1 = 1
 *  F_n = F_{n-1} + F_{n-2}, n > 1
 */

use BigInteger;

const zero = new bigint(0);
const one = new bigint(1);

private proc validate(n:integral) throws {
  if n < 0 { throw new IllegalArgumentError(); }
}

proc iterative(n: integral): bigint throws {
  validate(n);
  var (previous, current) = (zero, one);
  select n {
    when 0 do return previous;
    when 1 do return current;
    otherwise {
      for i in 0 .. (n - 2) {
        const previousPrevious = previous;
        previous = current;
        current = previous + previousPrevious;
      }
      return current;
    }
  }
}

iter sequence(n: integral): bigint throws {
  validate(n);
  var (current, next) = (zero, one);
  for i in 1 .. n {
    yield current;
    (current, next) = (next, current + next);
  }
}

proc naive_recursive(n: integral): bigint throws {
  validate(n);
  select n {
    when 0 do return zero;
    when 1 do return one;
    otherwise return naive_recursive(n - 1) + naive_recursive(n - 2);
  }
}

proc tail_recursive(n: integral): bigint throws {
  validate(n);
  proc iterate(i: int, r: bigint = zero, n: bigint = one): bigint throws {
    return if i < 1 then r else iterate(i - 1, n, r + n);
  }
  select n {
    when 0 do return zero;
    when 1 do return one;
    otherwise return iterate(n);
  }
}

proc library_fib(n: integral): bigint throws {
  validate(n);
  var x = new bigint();
  x.fib(n);
  return x;
}
