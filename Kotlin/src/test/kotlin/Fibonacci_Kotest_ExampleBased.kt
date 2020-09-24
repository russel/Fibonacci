package uk.org.winder.maths.fibonacci

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.checkAll

class Fibonacci_Kotest_ExampleBased: StringSpec({
    // Cannot use ::iterative, etc. here as each is an overloaded function and there
    // is no way of disambiguating. So create lambdas, to force correct types and
    // hence function selection.

    val functions = table(
            headers("name", "f"),
            row("iterative", {x: Int -> iterative(x)}),
            row("naïveRecursive", {x: Int -> naiveRecursive(x)}),
            row("tailRecursive", {x: Int -> tailRecursive(x)}),
            row("sequence", {x: Int -> sequence(x)}),
            row("foldive", {x: Int -> foldive(x)}),
            row("coroutine", {x: Int -> coroutine(x)}),
    )

    "positive arguments deliver the correct result" {
        forAll(functions) { name, f ->
            forAll(
                    row(0, zero),
                    row(1, one),
                    row(2, one),
                    row(3, two),
                    row(4, 3.bigint),
                    row(5, 5.bigint),
                    row(6, 8.bigint),
                    row(7, 13.bigint),
                    row(8, 21.bigint),
                    row(9, 34.bigint),
                    row(10, 55.bigint),
                    row(11, 89.bigint),
                    row(12, 144.bigint),
                    row(13, 233.bigint),
                    row(14, 377.bigint),
                    row(15, 610.bigint),
                    row(16, 987.bigint),
                    row(17, 1597.bigint),
                    row(18, 2584.bigint),
                    row(19, 4181.bigint),
                    row(20, 6765.bigint),
            ) { n, r ->
                f(n) == r
            }
        }
    }

    "negative arguments throw IllegalArgumentException" {
        forAll(functions) { name, f ->
            checkAll(Arb.negativeInts()) { n ->
                shouldThrow<IllegalArgumentException> {
                    f(n)
                }
            }
        }
    }

    "iterative of a huge number succeeds" { iterative(26000) }

    "naïve recursive of a huge number fails with a stack overflow" {
        shouldThrow<StackOverflowError> { naiveRecursive(26000) }
    }

    "tail recursive of a huge number succeeds" { tailRecursive(26000) }

    "sequence of a huge number succeeds" { sequence(26000) }

    "foldive of a huge number succeeds" { foldive(26000) }

    "coroutine of a huge number succeeds" { coroutine(26000) }

})
