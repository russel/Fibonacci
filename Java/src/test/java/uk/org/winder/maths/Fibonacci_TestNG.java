package uk.org.winder.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.LongFunction;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;

public class Fibonacci_TestNG {
  private Object[][] positiveData = new Object[][] {
      {0, BigInteger.valueOf(0)},
      {1, BigInteger.valueOf(1)},
      {2, BigInteger.valueOf(1)},
      {3, BigInteger.valueOf(2)},
      {4, BigInteger.valueOf(3)},
      {5, BigInteger.valueOf(5)},
      {6, BigInteger.valueOf(8)},
      {7, BigInteger.valueOf(13)},
      {8, BigInteger.valueOf(21)},
      {9, BigInteger.valueOf(34)},
      {10, BigInteger.valueOf(55)},
      {11, BigInteger.valueOf(89)},
      {12, BigInteger.valueOf(144)},
      {13, BigInteger.valueOf(233)},
      {14, BigInteger.valueOf(377)},
      {15, BigInteger.valueOf(610)},
      {16, BigInteger.valueOf(987)},
      {17, BigInteger.valueOf(1597)},
      {18, BigInteger.valueOf(2584)},
      {19, BigInteger.valueOf(4181)},
      {20, BigInteger.valueOf(6765)},
  };

  private Object[] negativeData = new Object[] {-1, -2, -5, -10, -20, -100};

  private Object[] algorithms = new Object[] {
      (LongFunction<BigInteger>)Fibonacci::iterative,
      (LongFunction<BigInteger>)Fibonacci::naïveRecursive,
      (LongFunction<BigInteger>)Fibonacci::memoizedRecursive,
      (LongFunction<BigInteger>)Fibonacci::closedForm,
  };

  @DataProvider
  private Object[][] algorithmsAndPositiveData() {
    final ArrayList<Object[]> values = new ArrayList<>();
    for (Object f: algorithms) {
      for (Object[] items: positiveData) {
        if (items.length != 2) { throw new RuntimeException("positiveData array borked."); }
        values.add(new Object[] {f, items[0], items[1]});
      }
    }
    return values.toArray(new Object[3][0]);
  }

  @DataProvider
  private Object[][] algorithmsAndNegativeData() {
    final ArrayList<Object[]> values = new ArrayList<>();
    for (Object f: algorithms) {
      for (Object item: negativeData) {
        values.add(new Object[] {f, item});
      }
    }
    return values.toArray(new Object[3][0]);
  }

  @Test(dataProvider= "algorithmsAndPositiveData")
  public void positiveArgumentsShouldWork(LongFunction<BigInteger> f, long n, BigInteger expected) {
    assertEquals(f.apply(n), expected);
  }

  @Test(dataProvider= "algorithmsAndNegativeData", expectedExceptions={IllegalArgumentException.class})
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
    final Iterator<BigInteger> fs = Fibonacci.Sequence.iterator();
    for (int i = 0; i < positiveData.length; ++i) {
      assertEquals(fs.next(), positiveData[i][1]);
    }
  }

  @Test
  public void javaIteratorClassInstance() {
    final Iterator<BigInteger> fs = new Fibonacci.Sequence.Iterator();
    for (int i = 0; i < positiveData.length; ++i) {
      assertEquals(fs.next(), positiveData[i][1]);
    }
  }
}
