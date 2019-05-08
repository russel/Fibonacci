import std.bigint: BigInt;
import std.range: drop, recurrence, takeOne;
import std.typecons: tuple ;

immutable BigInt zero = BigInt(0);
immutable BigInt one = BigInt(1);
immutable BigInt two = BigInt(2);

BigInt iterative(immutable ulong n) { return iterative(BigInt(n)); }
BigInt iterative(immutable BigInt n) {
    BigInt current = zero;
    BigInt next = one;
    foreach (i; zero .. n) {
        immutable temporary = next;
        next += current;
        current = temporary;
    }
    return current;
}

BigInt declarative(immutable ulong n) { return declarative(BigInt(n)); }
BigInt declarative(immutable BigInt n) {
    if (n == 0 || n == 1) { return n; }
    // Cannot use const or immutable values as parameters to recurrence.
    // Parameter to drop must be size_t.
    assert(n < long.max, "Parameter to declarative too large for implementation to deal with.");
    return recurrence!"a[n-1] + a[n-2]"(BigInt(0), BigInt(1)).drop(n.toLong()).takeOne.front;
}

BigInt recursive(immutable ulong n) { return iterative(BigInt(n)); }
BigInt recursive(immutable BigInt n) {
    if (n == 0 || n == 1) { return n; }
    return recursive(n - 2) + recursive(n - 1);
}

version(unittest) {
    import unit_threaded;

    immutable algorithms = [
                            tuple(&iterative, "iterative"),
                            tuple(&declarative, "declarative"),
                            tuple(&recursive, "recursive"),
                            ];
}

@("Example-based testing")
unittest {

  import std.conv: to;

  immutable data = [
                    tuple(0, zero),
                    tuple(1, one),
                    tuple(2, one),
                    tuple(3, two),
                    tuple(4, immutable BigInt(3)),
                    tuple(5, immutable BigInt(5)),
                    tuple(6, immutable BigInt(8)),
                    tuple(7, immutable BigInt(13)),
                    tuple(8, immutable BigInt(21)),
                    tuple(9, immutable BigInt(34)),
                    tuple(10, immutable BigInt(55)),
                    tuple(11, immutable BigInt(89)),
                    tuple(12, immutable BigInt(144)),
                    tuple(13, immutable BigInt(233)),
                    tuple(14, immutable BigInt(377)),
                    ] ;

  foreach (immutable a; algorithms) {
      foreach (immutable item; data) {
          immutable result = a[0](item[0]);
          assert(result == item[1], a[1] ~ "(" ~ to!string(item[0]) ~ ") = " ~ to!string(result) ~ " should be " ~ to!string(item[1]));
      }
  }

}

@("Check the base case for all algorithms.")
unittest {
    foreach(a; algorithms) {
        assert(a[0](0) == 0, a[1] ~ "(0) == 0 failed");
        assert(a[0](1) == 1, a[1] ~ "(1) == 1 failed");
    }
}

@("Check the Fibonacci property for all algorithms.")
unittest {
    foreach(a; algorithms) {
        immutable f = a[0];
        // Recursive implementation has dreadful run time performance so only use small
        // numbers. [0, 255] for the parameter is probably good enough for all algorithms.
        check!((ubyte a) => f(a + 2) == f(a + 1) + f(a));
    }
}
