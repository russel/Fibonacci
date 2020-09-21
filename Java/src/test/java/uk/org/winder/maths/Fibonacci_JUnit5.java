package uk.org.winder.maths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Fibonacci_JUnit5 {
    private static final List<Map.Entry<Integer, BigInteger>> positiveValues = Arrays.asList(
            Map.entry(0, BigInteger.valueOf(0)),
            Map.entry(1, BigInteger.valueOf(1)),
            Map.entry(2, BigInteger.valueOf(1)),
            Map.entry(3, BigInteger.valueOf(2)),
            Map.entry(4, BigInteger.valueOf(3)),
            Map.entry(5, BigInteger.valueOf(5)),
            Map.entry(6, BigInteger.valueOf(8)),
            Map.entry(7, BigInteger.valueOf(13)),
            Map.entry(8, BigInteger.valueOf(21)),
            Map.entry(9, BigInteger.valueOf(34)),
            Map.entry(10, BigInteger.valueOf(55)),
            Map.entry(11, BigInteger.valueOf(89)),
            Map.entry(12, BigInteger.valueOf(144)),
            Map.entry(13, BigInteger.valueOf(233)),
            Map.entry(14, BigInteger.valueOf(377)),
            Map.entry(15, BigInteger.valueOf(610)),
            Map.entry(16, BigInteger.valueOf(987)),
            Map.entry(17, BigInteger.valueOf(1597)),
            Map.entry(18, BigInteger.valueOf(2584)),
            Map.entry(19, BigInteger.valueOf(4181)),
            Map.entry(20, BigInteger.valueOf(6765))
    );

    private static final List<Long> negativeValues = Arrays.asList(-1L, -2L, -5L, -10L, -20L, -100L);

    private static final List<LongFunction<BigInteger>> functions = Arrays.asList(
            (LongFunction<BigInteger>) Fibonacci::iterative,
            (LongFunction<BigInteger>) Fibonacci::naiveRecursive,
            (LongFunction<BigInteger>) Fibonacci::memoizedRecursive
            // (LongFunction<BigInteger>) Fibonacci::closedForm  // There seems to be a problem with the closedForm implementation.
    );

	private static Stream<Arguments> negativeData() {
		return functions.stream().flatMap(f -> negativeValues.stream().map(v -> Arguments.of(f, v)));
	}

	private static Stream<Arguments> positiveData() {
		return functions.stream().flatMap(f -> positiveValues.stream().map(v -> Arguments.of(f, v)));
	}

    @ParameterizedTest
    @MethodSource("positiveData")
    void positiveArgumentsShouldWork(LongFunction<BigInteger> function, Map.Entry<Integer, BigInteger> datum) {
        assertEquals(datum.getValue(), function.apply(datum.getKey()));
    }

    @ParameterizedTest
	@MethodSource("negativeData")
    void negativeArgumentsShouldThrowException(LongFunction<BigInteger> function, Long datum) {
        assertThrows(IllegalArgumentException.class, () -> function.apply(datum));
    }

    @Test
    void javaStaticAccess() {
        assertEquals(Fibonacci.Sequence.getAt(7), BigInteger.valueOf(13));
        assertEquals(Fibonacci.Sequence.getAt(4), BigInteger.valueOf(3));
        assertEquals(Fibonacci.Sequence.getAt(7), BigInteger.valueOf(13));
        assertEquals(Fibonacci.Sequence.getAt(12), BigInteger.valueOf(144));
        assertEquals(Fibonacci.Sequence.getAt(8), BigInteger.valueOf(21));
    }

    @Test
    void javaInstanceAccess() {
        final Fibonacci.Sequence fs = new Fibonacci.Sequence();
        assertEquals(fs.getAt(7), BigInteger.valueOf(13));
        assertEquals(fs.getAt(4), BigInteger.valueOf(3));
        assertEquals(fs.getAt(7), BigInteger.valueOf(13));
        assertEquals(fs.getAt(12), BigInteger.valueOf(144));
        assertEquals(fs.getAt(8), BigInteger.valueOf(21));
    }

    @Test
    void javaIteratorFunction() {
        final var fs = Fibonacci.Sequence.iterator();
        for (var positiveDatum : positiveValues) {
            assertEquals(fs.next(), positiveDatum.getValue());
        }
    }

    @Test
    void javaIteratorClassInstance() {
        final var fs = new Fibonacci.Sequence.Iterator();
        for (var positiveDatum : positiveValues) {
            assertEquals(fs.next(), positiveDatum.getValue());
        }
    }
}
