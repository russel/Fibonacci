import ceylon.test{test, testExecutor, assertEquals, assertThatException}
import ceylon.math.whole{Whole, parseWhole, wholeNumber}
import ceylon.language.meta.model{IncompatibleTypeException}

import com.vasileff.ceylon.random.api{LCGRandom}

import com.athaydes.specks{errorCheck, feature,  propertyCheck, Specification, SpecksTestExecutor }
import com.athaydes.specks.assertion{expect, expectToThrow}
import com.athaydes.specks.matcher{equalTo}

import uk.org.winder.maths{
  fibonacci_iterative,
  fibonacci_naïveRecursive,
  fibonacci_tailRecursive,
  ValueException
}

Whole(Integer|Whole)[] algorithms = [
  fibonacci_iterative,
  fibonacci_naïveRecursive,
  fibonacci_tailRecursive
];

Whole parseStringToWhole(String s) {
	value x = parseWhole(s);
	if (exists x) { return x; }
	throw IncompatibleTypeException("Unexpected Null value.");
}

[Integer|Whole, Whole][] positiveValues = [
  [0, wholeNumber(0)],
  [1, wholeNumber(1)],
  [2, wholeNumber(1)],
  [3, wholeNumber(2)],
  [4, wholeNumber(3)],
  [5, wholeNumber(5)],
  [6, wholeNumber(8)],
  [7, wholeNumber(13)],
  [8, wholeNumber(21)],
  [9, wholeNumber(34)],
  [10, wholeNumber(55)],
  [11, wholeNumber(89)],
  [12, wholeNumber(144)],
  [13, wholeNumber(233)],
  [14, wholeNumber(377)]
];

Integer[] negativeValues = [-1, -2, -5, -10, -20, -100];

test
void fibonacci_positiveValues() {
	// This way of testing exits on the first error, later tests are not run.
  for (algorithm in algorithms) {
    for (item in positiveValues) {
      assertEquals(algorithm(item[0]), item[1], "executing ``algorithm``(``item[0]``)");
    }
  }
}

test
void fibonacci_negativeValues() {
 	// This way of testing exits on the first error, later tests are not run.
  for (algorithm in algorithms) {
    for (val in negativeValues) {
      assertThatException(() => algorithm(val)).hasType(`ValueException`);
    }
  }
}

testExecutor(`class SpecksTestExecutor`)
test shared Specification fibonacci_specks() {

	value upperBound = 200;

	class IntegerInRange(shared Integer val){}
	value random = LCGRandom();
	function generateIntegerInRange() => IntegerInRange(random.nextElement(1..upperBound));

	return Specification{
  	feature{
    	description = "Positive arguments give correct result";
    	when(Whole(Integer|Whole) a, Integer|Whole i, Whole r) => [a(i), r];
    	examples = [for (a in algorithms) for (x in positiveValues) [a, *x]];
    	(Whole v, Whole r) => expect(v, equalTo(r))
    },
 		errorCheck{
    	description = "Negative arguments throw exception";
 			when(Whole(Integer|Whole) a, Integer|Whole i) => a(i);
			examples = [for (a in algorithms) for (x in negativeValues) [a, x]];
			expectToThrow(`ValueException`)
    },
    propertyCheck{
    	description = "Implementations obey the recurrence relation for input in the range [1, ``upperBound``].";
    	sampleCount = 20;
    	generators = [generateIntegerInRange];
    	when(IntegerInRange n) => [n.val];
    	(Integer n) => expect(fibonacci_iterative(n), equalTo(fibonacci_iterative(n - 1) + fibonacci_iterative(n - 2))),
    	// Unwise to test naïveRecursive this way due to exponential time behaviour.
    	//(Integer n) => expect(fibonacci_naïveRecursive(n), equalTo(fibonacci_naïveRecursive(n - 1) + fibonacci_naïveRecursive(n - 2))),
    	(Integer n) => expect(fibonacci_tailRecursive(n), equalTo(fibonacci_tailRecursive(n - 1) + fibonacci_tailRecursive(n - 2)))
    }
 	};
}
