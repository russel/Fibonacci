use BigInteger;

const zero = new bigint(0);
const one = new bigint(1);

proc iterative(n: int): bigint {
    var previous = zero;
    var current = one;
    if n == 0 { return previous; }
    else if n == 1 { return current; }
    else {
          for i in 0..(n-2) {
               const previousPrevious = previous;
               previous = current;
               current = previous + previousPrevious;
          }
          return current;
    }
}

iter sequence(n: int): bigint {
    var current = zero;
    var next = one;
    for i in 1..n {
        yield current;
        (current, next) = (next, current+next);
    }
}

proc recursive(n: int): bigint {
    if (n == 0) { return zero; }
    if (n == 1) { return one; }
    return recursive(n - 1) + recursive(n - 2);
}
