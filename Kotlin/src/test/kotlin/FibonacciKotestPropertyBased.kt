package uk.org.winder.maths.fibonacci

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.checkAll
import io.kotest.property.forAll

class FibonacciKotestPropertyBased: FreeSpec({
	// Cannot use ::iterative, etc. here as each is an overloaded function and there
	// is no way of disambiguating. So create lambdas, to force correct types and
	// hence function selection.

	val functions = table(
			headers("name", "f"),
			row("iterative", {x: Int -> iterative(x)}),
			// row("naiveRecursive", {x: Int -> naiveRecursive(x)}),  // Takes far too long for sanity.
			row("tailRecursive", {x: Int -> tailRecursive(x)}),
			row("sequence", {x: Int -> sequence(x)}),
			row("foldive", {x: Int -> foldive(x)}),
			row("coroutine", {x: Int -> coroutine(x)}),
	)

	"base cases" - {
		forAll(functions) { name, f ->
			"$name(0) == 0" {
				f(0) == zero
			}
			"$name(1) == 1" {
				f(1) == one
			}
		}
	}

	"positive arguments obey the recurrence relation" - {
		forAll(functions) { name, f ->
			name {
				forAll(Arb.int(2, 2000)) { i ->
					f(i + 2) == f(i + 1) + f(i)
				}
			}
		}
	}

	"negative argument causes exception" - {
		forAll(functions) { name, f ->
			name {
				checkAll(Arb.negativeInts()) { i: Int ->
					shouldThrow<IllegalArgumentException> {
						f(i)
					}
				}
			}
		}
	}

})
