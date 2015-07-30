package uk.org.winder.maths

import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class Fibonacci_ScalaTest_ExampleBased extends FunSuite with Matchers with TableDrivenPropertyChecks {
  val algorithms = Table(
    ("algorithm", "name"),
    (Fibonacci.iterativeWhile _, "iterative"),
    (Fibonacci.iterativeFor _, "iterativeFor"),
    (Fibonacci.naïveRecursive _, "naïveRecursive"),
    (Fibonacci.tailRecursive _, "tailRecursive"),
    (Fibonacci.memoizedRecursive _, "memoizedRecursive"),
    (Fibonacci.folded _, "folded"),
    (Fibonacci.closedForm _, "closed form")
  )

  val inputData = Table(
    ("n", "f"),
    (0, BigInt(0)),
    (1, BigInt(1)),
    (2, BigInt(1)),
    (3, BigInt(2)),
    (4, BigInt(3)),
    (5, BigInt(5)),
    (6, BigInt(8)),
    (7, BigInt(13)),
    (8, BigInt(21)),
    (9, BigInt(34)),
    (10, BigInt(55)),
    (11, BigInt(89)),
    (12, BigInt(144)),
    (13, BigInt(233)),
    (14, BigInt(377)),
    (15, BigInt(610)),
    (16, BigInt(987)),
    (17, BigInt(1597)),
    (18, BigInt(2584)),
    (19, BigInt(4181)),
    (20, BigInt(6765))
  )
  forAll (inputData) {(n:Int, f:BigInt) =>
    forAll (algorithms) {(algorithm:Function[Int,BigInt], name:String) =>
      test(name + " " + n) { algorithm(n) should equal (f) }
    }
  }

  val negativeValues = Table("n", -20, -10, -1)
  forAll (negativeValues) {(n:Int) =>
    forAll (algorithms) {(algorithm:Function[Int,BigInt], name:String) =>
      test(name + " " + n) { an [IllegalArgumentException] should be thrownBy  { algorithm(n) } }
    }
  }

  test("iterative 10000") { Fibonacci.iterativeWhile(10000) }
  // These two will take exponential time so are not actually tried for unit testing.
  //test("naïveRecursive 9000") { Fibonacci.naïveRecursive(9000) }
  //test("naïveRecursive 10000") { an [StackOverflowError] should be thrownBy { Fibonacci.naïveRecursive(10000) } }
  test("tailRecursive 10000") { Fibonacci.tailRecursive(10000) }
  test("folded 10000") { Fibonacci.folded(10000) }
  // The memo is persistent for the execution of the program so there is a data coupling between the following two tests.
  test("memoizedRecursive 3000") { Fibonacci.memoizedRecursive(3000) }
  test("memoizedRecursive 7000") { an [StackOverflowError] should be thrownBy { Fibonacci.memoizedRecursive(7000) } }
}
