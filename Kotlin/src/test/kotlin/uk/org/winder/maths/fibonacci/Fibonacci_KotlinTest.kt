package uk.org.winder.maths.fibonacci

import io.kotlintest.specs.StringSpec
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.properties.table
import io.kotlintest.properties.headers
import io.kotlintest.properties.row

val random = java.util.Random()

val mediumSizeWholeNumbers = object: Gen<Int> {
    override fun generate() = random.nextInt(1000)
}

class Fibonacci_KotlinTest: StringSpec({

    val algorithms = table(
        headers("name", "f"),
        row("iterative", {x: Int -> iterative(x)}),
        // row("naïveRecursive", {x: Int -> naïveRecursive(x)}),  // Exponential time and space behaviour. :-(
        row("tailRecursive", {x: Int -> tailRecursive(x)}),
        row("sequence", {x: Int -> sequence(x)}),
        row("foldive", {x: Int -> foldive(x)})
    )

    forAll(algorithms){name, f ->

      "$name: zeroth Fibonacci Number is 0" { f(0) == zero }

      "$name: first Fibonacci Number is 1" { f(1) == one }

      "$name: non-negative arguments obey the recurrence relation" {
            forAll(mediumSizeWholeNumbers){i -> f(i + 2) == f(i + 1) + f(i)}
        }

      "$name: negative argument causes exception" {
        forAll(Gen.negativeIntegers()){i:Int ->
          shouldThrow<IllegalArgumentException>{ f(i) }
          true
        }
      }

    }

})
