import ceylon.math.whole{Whole, wholeNumber, zero, one, two}

"Type of exception thrown due to incorrect parameter type."
shared class ValueException() extends Exception("Value must be a non-negative integer", null){}

"Validate that the value is reasonable for the functions.
 Converts the parameter to Whole if necessary."
by("Russel Winder")
Whole validate(Integer|Whole x) {
	switch (x)
	case (is Whole) {
		if (x < zero) { throw ValueException(); }
		return x;
	}
	case (is Integer) {
		if (x < 0) { throw ValueException(); }
		return wholeNumber(x);
	}
}

"Classic iterative implementation."
by("Russel Winder")
shared Whole fibonacci_iterative(Integer|Whole x) {
	value n = validate(x);
	if (n < two) { return n; }
	variable Whole result = zero;
	variable Whole next = one;
	for (i in zero..(n - one)) {
		value temporary = result;
		result = next;
		next += temporary;
	}
	return result;
}

"Naïve recursive implementation."
by("Russel Winder")
shared Whole fibonacci_naïveRecursive(Integer|Whole x) {
	value n = validate(x);
	return if (n < two) then n else fibonacci_naïveRecursive(n - one) + fibonacci_naïveRecursive(n - two);
}

"Tail recursive implementation."
by("russel Winder")
shared Whole fibonacci_tailRecursive(Integer|Whole x) {
	Whole iterate(Whole n, Whole result = zero, Whole next = one) {
		return if (n == zero) then result else iterate(n - one, next, result + next);
	}
	return iterate(validate(x));
}
