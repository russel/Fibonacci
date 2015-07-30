package uk.org.winder.maths

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers

import org.scalacheck.Gen

class Fibonacci_ScalaTest_PropertyBased extends PropSpec with PropertyChecks with Matchers {
  val algorithms = Table(
    ("algorithm", "name"),
    (Fibonacci.iterativeWhile _, "iterative"),
    (Fibonacci.iterativeFor _, "iterativeFor"),
    //(Fibonacci.naïveRecursive _, "naïveRecursive"),  // Do not do this because of exponential behaviour.
    (Fibonacci.tailRecursive _, "tailRecursive"),
    (Fibonacci.memoizedRecursive _, "memoizedRecursive"),
    (Fibonacci.folded _, "folded")
    //(Fibonacci.closedForm _, "closed form")  // DO not do this due to use of hardware ints and overflow.
  )

  forAll(algorithms) { (f: Function1[Int, BigInt], name: String) =>
    property("ForAll: Fibonacci using " + name + " obeys the recurrence relation for non-negative integers") {
      val positiveIntSample = for (n <- Gen.choose(0, 1000)) yield n
      forAll (positiveIntSample) { (n: Int) => f(n + 2) should equal (f(n + 1) + f(n)) }
    }
    property("Factorial using " + name + " throws an exception for negative integers") {
      forAll { (n:Int) => whenever (n < 0) { an [IllegalArgumentException] should be thrownBy { f(n) } } }
    }
  }

}
