package uk.org.winder.math

import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.transform.TypeCheckingMode
import groovy.transform.TailRecursive

import static java.lang.Math.sqrt

@CompileStatic
class Fibonacci {

  private static void validateParameter(final BigInteger x) {
    if (x < 0) throw new IllegalArgumentException('Negative index not allowed.')
  }

  //------------------------------------------------------------------------------------------------------------------------

  static BigInteger iterative(final BigInteger n) {
    validateParameter(n)
    if (n < 2) { n }
    else {
      def result = 0G
      def next = 1G
      for (i in 0G..<n) {
        (result, next) = [next, next + result]
      }
      result
    }
  }
  static BigInteger iterative(final Integer n) { iterative(n as BigInteger) }
  static BigInteger iterative(final Long n) { iterative(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  static BigInteger recursive(final BigInteger n) {
    validateParameter(n)
    n < 2 ? n : recursive(n - 1) + recursive(n - 2)
  }
  static BigInteger recursive(final Integer n) { recursive(n as BigInteger) }
  static BigInteger recursive(final Long n) { recursive(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  // Closure cannot be tail recursive via the AST transform
  @TailRecursive
  private static BigInteger iterate(final BigInteger i, final BigInteger a, final BigInteger b) {
    i < 1 ? a : iterate(i - 1, b, a + b)
  }

  static BigInteger tailRecursive(final BigInteger n) {
    validateParameter(n)
    if (n < 2) { n }
    else { iterate(n, 0G, 1G) }
  }
  static BigInteger tailRecursive(final Integer n) { tailRecursive(n as BigInteger) }
  static BigInteger tailRecursive(final Long n) { tailRecursive(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

 // trampoline returns a Serializable which is not compatible with Closure so we cannot do compile time
  // type checking on this one :-(
  @CompileStatic(TypeCheckingMode.SKIP)
  static BigInteger tailRecursiveTrampoline(final BigInteger n) {
    validateParameter(n)
    if (n < 2) { n }
    else {
      /*final*/ Closure<BigInteger> iterate
      iterate = {BigInteger i, BigInteger a, BigInteger b ->  i < 1 ? a : iterate.trampoline(i - 1, b, a + b) }.trampoline()
      iterate(n, 0G, 1G)
    }
  }
  static BigInteger tailRecursiveTrampoline(final Integer n) { tailRecursiveTrampoline(n as BigInteger) }
  static BigInteger tailRecursiveTrampoline(final Long n) { tailRecursiveTrampoline(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  private static Map<BigInteger, BigInteger> memo = [0G: 0G, 1G: 1G]
  static BigInteger manualMemoizedRecursive(final BigInteger n) {
    validateParameter(n)
    if (! memo.containsKey(n)) {
      memo[n] = memoizedRecursive(n - 1)  + memoizedRecursive(n - 2)
    }
    memo[n]
  }
  static BigInteger manualMemoizedRecursive(final Integer n) { manualMemoizedRecursive(n as BigInteger) }
  static BigInteger manualMemoizedRecursive(final Long n) { manualMemoizedRecursive(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  @Memoized static BigInteger memoizedRecursive(final BigInteger n) {
    validateParameter(n)
    n < 2 ? n : recursive(n - 1) + recursive(n - 2)
  }
  static BigInteger memoizedRecursive(final Integer n) { memoizedRecursive(n as BigInteger) }
  static BigInteger memoizedRecursive(final Long n) { memoizedRecursive(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  static final BigDecimal sqrt5 = BigDecimal.valueOf(sqrt(5))

  static BigInteger closedForm(final BigInteger n) {
    validateParameter(n)
    (((((1.0 + sqrt5) / 2.0) ** n) / sqrt5) + 0.5) as BigInteger
  }
  static BigInteger closedForm(final Integer n) { closedForm(n as BigInteger) }
  static BigInteger closedForm(final Long n) { closedForm(n as BigInteger) }

  //------------------------------------------------------------------------------------------------------------------------

  private static class Sequence {
    Sequence() {}
    private static ArrayList<BigInteger> numbers = [0G, 1G] as ArrayList
    // The use of numbers in the closure means that it's use cannot be typechecked at compile time.
    @CompileStatic(TypeCheckingMode.SKIP)
    static BigInteger getAt(final int index) {
      Fibonacci.validateParameter(index as BigInteger)
      if (index >= numbers.size()) {
        numbers.ensureCapacity(index + 1)
        (numbers.size() .. index).each{int i -> numbers << numbers[i - 1] + numbers[i - 2]}
      }
      numbers[index]
    }
    static BigInteger getAt(final BigInteger index) { getAt(index as int) }
    static BigInteger getAt(final Long index) { getAt(index as int) }
  }

  private static class Iterator implements java.util.Iterator {
    private BigInteger currentIndex = 0G
    private final Sequence sequence
    Iterator(final Sequence s) { sequence = s }
    boolean hasNext() { return true }
    BigInteger next() { sequence[currentIndex++] }
    void remove() { throw new UnsupportedOperationException() }
  }

  static Sequence sequence() { new Sequence() }
  static Iterator iterator() { return new Iterator(new Sequence()) }
}
