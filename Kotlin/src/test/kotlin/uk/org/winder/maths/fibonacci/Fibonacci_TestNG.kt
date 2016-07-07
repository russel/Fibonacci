package uk.org.winder.maths.fibonacci

import java.math.BigInteger

import org.testng.annotations.Test
import org.testng.annotations.DataProvider

import org.testng.Assert.assertEquals

class Fibonacci_TestNG {

  val algorithms = arrayOf(
      "iterative" to {x:Long -> iterative(x)},
      "naïveRecursive" to {x:Long -> naïveRecursive(x)},
      "tailRecursive" to {x:Long -> tailRecursive(x)},
      "sequence" to ::sequence,
      "foldive" to {x:Long -> foldive(x)}
        )

  val positiveData = arrayOf(
      0L to BigInteger.ZERO,
      1L to BigInteger.ONE,
      2L to BigInteger.ONE,
      3L to 2.bigint,
      4L to 3.bigint,
      5L to 5.bigint,
      6L to 8.bigint,
      7L to 13.bigint,
      8L to 21.bigint,
      9L to 34.bigint,
      10L to 55.bigint,
      11L to 89.bigint,
      12L to 144.bigint,
      13L to 233.bigint,
      14L to 377.bigint,
      15L to 610.bigint,
      16L to 987.bigint,
      17L to 1597.bigint,
      18L to 2584.bigint,
      19L to 4181.bigint,
      20L to 6765.bigint
  )

  val negativeData = arrayOf(-1L, -2L, -5L, -10L, -20L, -100L)

  @DataProvider
  fun algorithmsAndPositiveData() = algorithms.flatMap({a -> positiveData.map({d -> arrayOf(a.first, a.second, d.first, d.second)})}).toTypedArray()

  @Test(dataProvider="algorithmsAndPositiveData")
  fun nonNegativeArgument(name:String, algorithm:(Long)->BigInteger, value:Long, expected:BigInteger) {
    assertEquals(algorithm(value), expected, "$name($value)")
  }

  @DataProvider
  fun algorithmsAndNegativeData() = algorithms.flatMap({a -> negativeData.map({d -> arrayOf(a.first, a.second, d)})}).toTypedArray()

  @Test(dataProvider="algorithmsAndNegativeData", expectedExceptions=arrayOf(IllegalArgumentException::class))
  fun negativeArgument(name:String, algorithm:(Long)->BigInteger, value:Long) {
    algorithm(value)
  }

}
