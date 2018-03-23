package uk.org.winder.maths;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;

public class Fibonacci {
    private static final BigInteger zero = BigInteger.ZERO;
    private static final BigInteger one = BigInteger.ONE;
    private static final BigInteger two = one.add(one);

    private static void validate(final BigInteger n) {
        if (n.compareTo(zero) < 0) {
            throw new IllegalArgumentException("index must be positive.");
        }
    }

    public static BigInteger iterative(final long n) {
        return iterative(BigInteger.valueOf(n));
    }

    public static BigInteger iterative(final BigInteger n) {
        validate(n);
        var result = zero;
        var next = one;
        for (var i = zero; i.compareTo(n) < 0; i = i.add(one)) {
            var temporary = result;
            result = next;
            next = temporary.add(next);
        }
        return result;
    }

    public static BigInteger naïveRecursive(final long n) {
        return naïveRecursive(BigInteger.valueOf(n));
    }

    public static BigInteger naïveRecursive(final BigInteger n) {
        validate(n);
        return
                n.compareTo(zero) == 0 ? zero :
                        n.compareTo(one) == 0 ? one :
                                naïveRecursive(n.subtract(one)).add(naïveRecursive(n.subtract(two)));
    }

    private static Map<BigInteger, BigInteger> memo = new HashMap<BigInteger, BigInteger>();
    static {
        memo.put(zero, zero);
        memo.put(one, one);
    }

    public static BigInteger memoizedRecursive(final long n) {
        return memoizedRecursive(BigInteger.valueOf(n));
    }

    public static BigInteger memoizedRecursive(final BigInteger n) {
        validate(n);
        if (!memo.containsKey(n)) {
            memo.put(n, memoizedRecursive(n.subtract(one)).add(memoizedRecursive(n.subtract(two))));
        }
        return memo.get(n);
    }

    private static final BigDecimal bd_one = BigDecimal.ONE;
    private static final BigDecimal bd_two = bd_one.add(bd_one);
    private static final BigDecimal bd_half = BigDecimal.valueOf(0.5);

    public static BigInteger closedForm(final long n) {
        return closedForm(BigInteger.valueOf(n));
    }

    public static BigInteger closedForm(final BigInteger n) {
        validate(n);
        final var sqrt5 = BigDecimal.valueOf(sqrt(5));
        return sqrt5
                .add(bd_one)
                .divide(bd_two)
                .pow(n.intValue())
                .divide(sqrt5, BigDecimal.ROUND_HALF_DOWN)
                .add(bd_half)
                .toBigInteger();
    }

    public static class Sequence {
        private final static ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
        static {
            numbers.add(zero);
            numbers.add(one);
        }

        public static class Iterator implements java.util.Iterator<BigInteger> {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() { return true; }

            @Override
            public BigInteger next() { return Sequence.getAt(currentIndex++); }

            @Override
            public void remove() { throw new UnsupportedOperationException(); }
        }

        public static Iterator iterator() { return new Iterator(); }

        public static BigInteger getAt(final int index) {
            if (index < 0) {
                throw new IllegalArgumentException("Negative index not allowed.");
            }
            if (index >= numbers.size()) {
                numbers.ensureCapacity(index + 1);
                for (var i = numbers.size(); i <= index; ++i) {
                    numbers.add(numbers.get(i - 1).add(numbers.get(i - 2)));
                }
            }
            return numbers.get(index);
        }
    }
}
