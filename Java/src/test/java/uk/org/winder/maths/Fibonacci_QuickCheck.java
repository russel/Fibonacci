package uk.org.winder.maths;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.Property;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import static uk.org.winder.maths.Fibonacci.iterative;
import static uk.org.winder.maths.Fibonacci.naiveRecursive;
import static uk.org.winder.maths.Fibonacci.memoizedRecursive;
import static uk.org.winder.maths.Fibonacci.closedForm;

@RunWith(JUnitQuickcheck.class)
public final class Fibonacci_QuickCheck {

  // Keep the Integer argument range relatively small so that the tests run in reasonable time.
  // [0, 500] is seen as reasonable.

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Property public void iterativePositive(@InRange(min = "0", max = "500") final Long i) {
    assertEquals(iterative(i + 2), iterative(i +1).add(iterative(i)));
  }

  @Property public void iterativeNegative(@InRange(min = "-500", max = "-1") final Long i) {
    thrown.expect(RuntimeException.class);
    iterative(i);
  }

  // We all know that this implementation has dreadful performance characteristics, so
  // DO NOT use even medium sized values for the argument.
  @Property public void naïveRecursivePositive(@InRange(min="0", max="10") final Long i) {
    assertEquals(Fibonacci.naiveRecursive(i + 2),  Fibonacci.naiveRecursive(i + 1).add(Fibonacci.naiveRecursive(i)));
  }

  @Property public void naïveRecursiveNegative(@InRange(min="-500", max="-1") final Long i) {
    thrown.expect(RuntimeException.class);
    Fibonacci.naiveRecursive(i);
  }

  @Property public void memoizedRecursivePositive(@InRange(min="0", max="500") final Long i) {
    assertEquals(memoizedRecursive(i + 2), memoizedRecursive(i + 1).add(memoizedRecursive(i)));
  }

  @Property public void memoizedRecursiveNegative(@InRange(min="-500", max="-1") final Long i) {
    thrown.expect(RuntimeException.class);
    memoizedRecursive(i);
  }

  /*
   * TODO There is a problem with the closedForm implementation.
   *
  @Property public void closedFormPositive(@InRange(min="0", max="500") final Long i) {
     assertEquals(closedForm(i + 2), closedForm(i + 1).add(closedForm(i)));
   }
   */

   @Property public void closedFormNegative(@InRange(min="-500", max="-1") final Long i) {
     thrown.expect(RuntimeException.class);
     closedForm(i);
   }

}
