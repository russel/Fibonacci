use GMP;

const zero = new BigInt(0);
const one = new BigInt(1);

proc iterative(n: int) {
  var previous = zero;
  var current = one;
  if n == 0 { return previous; }
  else if n == 1 { return current; }
  else {
    for i in 0..(n-2) {
      const previousPrevious = previous;
      previous = current;
      current = new BigInt;
      current.add(previous, previousPrevious);
    }
    return current;
  }
}

iter sequence(n: int) {
  var current = 0;
  var next = 1;
  for i in 1..n {
    yield current;
    (current, next) = (next, current+next);
  }
}
