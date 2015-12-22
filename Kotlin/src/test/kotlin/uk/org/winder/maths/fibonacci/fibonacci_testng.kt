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
      0 to BigInteger.ZERO,
      1 to BigInteger.ONE,
      2 to BigInteger.ONE,
      3 to 2.bigint,
      4 to 3.bigint,
      5 to 5.bigint,
      6 to 8.bigint,
      7 to 13.bigint,
      8 to 21.bigint,
      9 to 34.bigint,
      10 to 55.bigint,
      11 to 89.bigint,
      12 to 144.bigint,
      13 to 233.bigint,
      14 to 377.bigint,
      15 to 610.bigint,
      16 to 987.bigint,
      17 to 1597.bigint,
      18 to 2584.bigint,
      19 to 4181.bigint,
      20 to 6765.bigint
  )

  val negativeData = arrayOf(-1, -2, -5, -10, -20, -100)

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
