package uk.org.winder.maths.fibonacci

import io.kotlintest.specs.StringSpec

class BigInteger_Extras_KotlinTest : StringSpec() {
  init {

    "value is in a range it is supposed to be" {
      (5.bigint in 0.bigint rangeTo 10.bigint) shouldBe true
    }

    "value is not in a range it is not supposed to be in" {
      (-5.bigint in 0.bigint rangeTo 10.bigint) shouldBe false
    }

    "upward range generates the expected values" {
      (0.bigint rangeTo 5.bigint).toList() shouldEqual listOf(0, 1, 2, 3, 4, 5).map { it.bigint }
    }

    "upward stridded range generates the expected values" {
      (0.bigint rangeTo 5.bigint step 2).toList() shouldEqual listOf(0, 2, 4).map { it.bigint }
    }

    "downward range generates the expected values" {
      (5.bigint downTo 0.bigint).toList() shouldEqual listOf(5, 4, 3, 2, 1, 0).map { it.bigint }
    }

    "downward strided range generates the expected values" {
      (5.bigint downTo 0.bigint step 2).toList() shouldEqual listOf(5, 3, 1).map { it.bigint }
    }

  }
}