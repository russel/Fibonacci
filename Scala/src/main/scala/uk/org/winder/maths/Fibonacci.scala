package uk.org.winder.maths

import scala.annotation.tailrec
import scala.math.sqrt

object Fibonacci {

  val zero = new BigInt(java.math.BigInteger.ZERO)
  val one = new BigInt(java.math.BigInteger.ONE)
  val two = new BigInt(new java.math.BigInteger("2"))

 private def validate(n:BigInt):Unit = {
    if (n < zero) throw new IllegalArgumentException("Parameter must be a non-negative integer.")
  }

  def iterativeWhile(n:Int):BigInt = iterativeWhile(BigInt(n))
  def iterativeWhile(n:BigInt):BigInt = {
    validate(n)
    var i = n
    var result = zero
    var next = one
    while (i > zero) {
      val temporary = result
      result = next
      next = temporary + next
      i = i - one
    }
    result
  }

  def iterativeFor(n:Int):BigInt = iterativeFor(BigInt(n))
  def iterativeFor(n:BigInt):BigInt = {
    validate(n)
    var result = zero
    var next = one
    for (i <- zero until n) {
      val temporary = result
      result = next
      next = temporary + next
    }
    result
  }

  def naïveRecursive(n:Int):BigInt = naïveRecursive(BigInt(n))
  def naïveRecursive(n:BigInt):BigInt = {
    validate(n)
    if (n < two) n
    else naïveRecursive(n - one) + naïveRecursive(n - two)
  }

  def tailRecursive(n:Int):BigInt = tailRecursive(BigInt(n))
  def tailRecursive(n:BigInt):BigInt = {
    validate(n)
    @tailrec def iteration(n:BigInt, next:BigInt, result:BigInt):BigInt = n match {
      case `zero` => result
      case _ => iteration(n - one, next + result, next)
    }
    iteration(n, one, zero)
   }

  val memo = scala.collection.mutable.Map(BigInt(0) -> BigInt(0), BigInt(1) -> BigInt(1))
  def memoizedRecursive(n:Int):BigInt = memoizedRecursive(BigInt(n))
  def memoizedRecursive(n:BigInt):BigInt = {
    validate(n)
    if (! memo.contains(n)) {
      memo += n -> (memoizedRecursive(n - one) + memoizedRecursive(n - two))
    }
    memo(n)
  }

  def folded(n:Int):BigInt = folded(BigInt(n))
  def folded(n:BigInt):BigInt = {
    validate(n)
    if (n < two) n
    else ((zero, one) /: (one until n))((a, _) => (a._2, a._1 + a._2))._2
  }

  def closedForm(n:Int):BigInt = closedForm(BigInt(n))
  def closedForm(n:BigInt):BigInt = {
    validate(n)
    val sqrt5 = BigDecimal(sqrt(5))
    return (((1 + sqrt5) / 2).pow(n.intValue) / sqrt5 + 0.5).toBigInt
  }

}
