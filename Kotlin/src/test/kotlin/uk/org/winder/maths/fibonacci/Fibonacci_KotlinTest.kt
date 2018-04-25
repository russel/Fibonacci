package uk.org.winder.maths.fibonacci

import kotlin.coroutines.experimental.buildIterator
import kotlin.coroutines.experimental.buildSequence

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.tables.forAll as tableForAll
import io.kotlintest.tables.table
import io.kotlintest.tables.headers
import io.kotlintest.tables.row

val random = java.util.Random()

val smallishWholeNumbers = object: Gen<Int> {
	override fun constants() = Iterable<Int> { buildIterator { yield(0) } }
	override fun random() = generateSequence { random.nextInt(1000) }
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

			tableForAll(algorithms){name, f ->

				"$name: zeroth Fibonacci Number is 0" { f(0) == zero }

				"$name: first Fibonacci Number is 1" { f(1) == one }

				"$name: non-negative arguments obey the recurrence relation" {
					forAll(smallishWholeNumbers){i -> f(i + 2) == f(i + 1) + f(i)}
				}

				"$name: negative argument causes exception" {
					forAll(Gen.negativeIntegers()){i:Int ->
						shouldThrow<IllegalArgumentException>{f(i)}
						true
					}
				}

			}

		})
