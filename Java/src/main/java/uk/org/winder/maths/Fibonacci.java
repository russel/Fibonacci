package uk.org.winder.maths;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;

public class Fibonacci {
    private static final BigInteger zero = BigInteger.ZERO;
    private static final BigInteger one = BigInteger.ONE;
    private static final BigInteger two = BigInteger.TWO;

    private static void validate(final BigInteger n) {
        if (n.compareTo(zero) < 0) {
            throw new IllegalArgumentException("index must be positive.");
        }
    }

    public static BigInteger iterative(final Long n) {
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

    public static BigInteger naiveRecursive(final Long n) {
        return naiveRecursive(BigInteger.valueOf(n));
    }

    public static BigInteger naiveRecursive(final BigInteger n) {
        validate(n);
        return
                n.compareTo(zero) == 0 ? zero :
                        n.compareTo(one) == 0 ? one :
                                naiveRecursive(n.subtract(one)).add(naiveRecursive(n.subtract(two)));
    }

    private static Map<BigInteger, BigInteger> memo = new HashMap<>();
    static {
        memo.put(zero, zero);
        memo.put(one, one);
    }

    public static BigInteger memoizedRecursive(final Long n) {
        return memoizedRecursive(BigInteger.valueOf(n));
    }

    public static BigInteger memoizedRecursive(final BigInteger n) {
        validate(n);
        if (!memo.containsKey(n)) {
            memo.put(n, memoizedRecursive(n.subtract(one)).add(memoizedRecursive(n.subtract(two))));
        }
        return memo.get(n);
    }

    public static BigInteger closedForm(final Long n) {
        return closedForm(BigInteger.valueOf(n));
    }

    public static BigInteger closedForm(final BigInteger n) {
        validate(n);
        //  Use what is known as Binet's Formula. cf. https://en.wikipedia.org/wiki/Fibonacci_number#Binet's_formula
        final var one = BigDecimal.ONE;
        final var two = BigDecimal.valueOf(2);
        final var half = BigDecimal.valueOf(0.5);
        final var sqrt5 = BigDecimal.valueOf(sqrt(5));
        final var n_i = n.intValue();
        final var phi = sqrt5.add(one).divide(two).pow(n_i);
        final var psi = sqrt5.subtract(one).divide(two).pow(n_i);
        return phi.subtract(psi).divide(sqrt5, RoundingMode.HALF_UP).add(half).toBigInteger();
    }

    public static class Sequence {
        private final static ArrayList<BigInteger> numbers = new ArrayList<>();
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
