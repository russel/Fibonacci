package uk.org.winder.maths;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import net.jqwik.api.constraints.LongRange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static uk.org.winder.maths.Fibonacci.iterative;
import static uk.org.winder.maths.Fibonacci.naiveRecursive;
import static uk.org.winder.maths.Fibonacci.memoizedRecursive;
import static uk.org.winder.maths.Fibonacci.closedForm;

final class Fibonacci_Jqwik {

  // Keep the Long argument range relatively small so that the tests run in reasonable time.
  // [0, 500] is seen as reasonable.

  @Provide("0 to 500")
  Arbitrary<Long> positiveNumbers() {
    return Arbitraries.longs().between(0, 500);
  }

  @Provide("-500 to -1")
  Arbitrary<Long> negativeNumbers() {
    return Arbitraries.longs().between(-500, -1);
  }

  @Property
  void iterativePositive(@ForAll("0 to 500") final Long i) {
    assertEquals(iterative(i + 2), iterative(i +1).add(iterative(i)));
  }

  @Property
  void iterativeNegative(@ForAll("-500 to -1") final Long i) {
    assertThrows(RuntimeException.class, () -> iterative(i));
  }

  // We all know that this implementation has dreadful performance characteristics, so
  // DO NOT use even medium sized values for the argument.
  @Property
  public void naiveRecursivePositive(@ForAll @LongRange(min=0L, max=10L) final Long i) {
    assertEquals(Fibonacci.naiveRecursive(i + 2),  naiveRecursive(i + 1).add(naiveRecursive(i)));
  }

  @Property
  public void naiveRecursiveNegative(@ForAll("-500 to -1") final Long i) {
    assertThrows(RuntimeException.class, () -> naiveRecursive(i));
  }

  @Property
  public void memoizedRecursivePositive(@ForAll("0 to 500") final Long i) {
    assertEquals(memoizedRecursive(i + 2), memoizedRecursive(i + 1).add(memoizedRecursive(i)));
  }

  @Property
  public void memoizedRecursiveNegative(@ForAll("-500 to -1") final Long i) {
    assertThrows(RuntimeException.class, () -> memoizedRecursive(i));
  }

  /*
   * TODO There is a problem with the closedForm implementation.
   *
  @Property
  void closedFormPositive(@ForAll("0 to 500") final Long i) {
     assertEquals(closedForm(i + 2), closedForm(i + 1).add(closedForm(i)));
   }
   */

   @Property
   void closedFormNegative(@ForAll("-500 to -1") final Long i) {
     assertThrows(RuntimeException.class, () -> closedForm(i));
   }

}
