package uk.org.winder.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.LongFunction;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;

public class Fibonacci_TestNG {
    private Object[][] positiveData = new Object[][]{
            {0L, BigInteger.valueOf(0)},
            {1L, BigInteger.valueOf(1)},
            {2L, BigInteger.valueOf(1)},
            {3L, BigInteger.valueOf(2)},
            {4L, BigInteger.valueOf(3)},
            {5L, BigInteger.valueOf(5)},
            {6L, BigInteger.valueOf(8)},
            {7L, BigInteger.valueOf(13)},
            {8L, BigInteger.valueOf(21)},
            {9L, BigInteger.valueOf(34)},
            {10L, BigInteger.valueOf(55)},
            {11L, BigInteger.valueOf(89)},
            {12L, BigInteger.valueOf(144)},
            {13L, BigInteger.valueOf(233)},
            {14L, BigInteger.valueOf(377)},
            {15L, BigInteger.valueOf(610)},
            {16L, BigInteger.valueOf(987)},
            {17L, BigInteger.valueOf(1597)},
            {18L, BigInteger.valueOf(2584)},
            {19L, BigInteger.valueOf(4181)},
            {20L, BigInteger.valueOf(6765)},
    };

    private Object[] negativeData = new Object[]{-1L, -2L, -5L, -10L, -20L, -100L};

    private Object[] algorithms = new Object[]{
            (LongFunction<BigInteger>) Fibonacci::iterative,
            (LongFunction<BigInteger>) Fibonacci::naiveRecursive,
            (LongFunction<BigInteger>) Fibonacci::memoizedRecursive,
            // (LongFunction<BigInteger>) Fibonacci::closedForm,  // There seems to be a problem with the closedForm implementation.
    };

    @DataProvider
    private Object[][] algorithmsAndPositiveData() {
        final var values = new ArrayList<Object[]>();
        for (var f: algorithms) {
            for (var items: positiveData) {
                if (items.length != 2) {
                    throw new RuntimeException("positiveData array borked.");
                }
                values.add(new Object[]{f, items[0], items[1]});
            }
        }
        return values.toArray(new Object[3][0]);
    }

    @DataProvider
    private Object[][] algorithmsAndNegativeData() {
        final var values = new ArrayList<Object[]>();
        for (var f: algorithms) {
            for (var item: negativeData) {
                values.add(new Object[]{f, item});
            }
        }
        return values.toArray(new Object[3][0]);
    }

    @Test(dataProvider = "algorithmsAndPositiveData")
    public void positiveArgumentsShouldWork(LongFunction<BigInteger> f, long n, BigInteger expected) {
        assertEquals(f.apply(n), expected);
    }

    @Test(dataProvider = "algorithmsAndNegativeData", expectedExceptions = {IllegalArgumentException.class})
    public void negativeArgumentsShouldThrowException(LongFunction<BigInteger> f, long n) {
        f.apply(n);
    }

    @Test
    public void javaStaticAccess() {
        assertEquals(Fibonacci.Sequence.getAt(7), BigInteger.valueOf(13));
        assertEquals(Fibonacci.Sequence.getAt(4), BigInteger.valueOf(3));
        assertEquals(Fibonacci.Sequence.getAt(7), BigInteger.valueOf(13));
        assertEquals(Fibonacci.Sequence.getAt(12), BigInteger.valueOf(144));
        assertEquals(Fibonacci.Sequence.getAt(8), BigInteger.valueOf(21));
    }

    @Test
    public void javaInstanceAccess() {
        final Fibonacci.Sequence fs = new Fibonacci.Sequence();
        assertEquals(fs.getAt(7), BigInteger.valueOf(13));
        assertEquals(fs.getAt(4), BigInteger.valueOf(3));
        assertEquals(fs.getAt(7), BigInteger.valueOf(13));
        assertEquals(fs.getAt(12), BigInteger.valueOf(144));
        assertEquals(fs.getAt(8), BigInteger.valueOf(21));
    }

    @Test
    public void javaIteratorFunction() {
        final var fs = Fibonacci.Sequence.iterator();
        for (var i = 0; i < positiveData.length; ++i) {
            assertEquals(fs.next(), positiveData[i][1]);
        }
    }

    @Test
    public void javaIteratorClassInstance() {
        final var fs = new Fibonacci.Sequence.Iterator();
        for (var i = 0; i < positiveData.length; ++i) {
            assertEquals(fs.next(), positiveData[i][1]);
        }
    }
}
