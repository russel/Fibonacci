package uk.org.winder.maths.fibonacci

import io.kotlintest.specs.StringSpec

class Fibonacci_KotlinTest: StringSpec() {
  init {

    val algorithms = listOf(
        "iterative" to { x: Int -> iterative(x) },
        "naïveRecursive" to { x: Int -> naïveRecursive(x) },
        "tailRecursive" to { x: Int -> tailRecursive(x) },
        "sequence" to ::sequence,
        "foldive" to { x: Int -> foldive(x) }
    )

    algorithms.forEach{a ->
      val name = a.first
      val f = a.second

      (name + ": zeroth Fibonacci Number is 0") { f(0) == zero }

      (name + ": first Fibonacci Number is 1") { f(1) == one }

      (name + ": non-negative arguments obey the recurrence relation") {
        forAll{i: Int ->
          if (1 < i && i < 100000) { f(i) == f(i - 1) + f(i - 2) }
          else { true }
        }
      }

      (name + ": negative argument causes exception") {
        forAll{i:Int ->
          if (i < 0) { shouldThrow<IllegalArgumentException>{ f(i) } }
          true
        }
      }

    }

  }
}
