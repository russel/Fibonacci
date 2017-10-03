package uk.org.winder.maths

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks

import org.scalacheck.Gen

class Fibonacci_ScalaTest_PropertyBased extends PropSpec with PropertyChecks {
	val algorithms = Table(
		("algorithm", "name"),
		(Fibonacci.iterativeWhile _, "iterative"),
		(Fibonacci.iterativeFor _, "iterativeFor"),
		//(Fibonacci.naïveRecursive _, "naïveRecursive"),  // Do not do this because of exponential behaviour.
		(Fibonacci.tailRecursive _, "tailRecursive"),
		(Fibonacci.memoizedRecursive _, "memoizedRecursive"),
		(Fibonacci.foldLeftive _, "folded"),
		(Fibonacci.zipive _, "zipive")
			//(Fibonacci.closedForm _, "closed form")  // DO not do this due to use of hardware ints and overflow.
	)

	forAll(algorithms) { (f: Function1[Int, BigInt], name: String) =>
		property("ForAll: Fibonacci using " + name + " obeys the recurrence relation for non-negative integers") {
			val positiveIntSample = for (n <- Gen.choose(0, 1000)) yield n
			forAll (positiveIntSample) { (n: Int) => assert(f(n + 2) == f(n + 1) + f(n)) }
		}
		property("Factorial using " + name + " throws an exception for negative integers") {
			forAll { (n:Int) => whenever (n < 0) { intercept [IllegalArgumentException] { f(n) } } }
		}
	}

}
