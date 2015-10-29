// Package fibonacci provides a number of implementations of a function to return the nth item in the
// Fibonacci Sequence. The Fibonacci Sequence is defined by the recurrence relation:
//
//  f(0) = 0
//  f(1) = 1
//  f(n) = f(n -1) + f(n -2)
//
// The variants show different algorithms. For each algorithm there are two implementations, one for a uint,
// one for a big.Int. The return value in all cases is a big.Int.
package fibonacci

import "math/big"

// Iterative_uint is an imperative, loop-based implementation with the parameter being an uint value.
func Iterative_uint(n uint) (value *big.Int) {
	value = big.NewInt(0)
	if n == 0 {
		return
	}
	previous := value
	value = big.NewInt(1)
	for i := uint(1); i < n; i++ {
		previous, value = value, previous.Add(previous, value)
	}
	return
}

// Iterative_bigInt is an imperative, loop-based implementation with the parameter being a big.Int value.
func Iterative_bigInt(n *big.Int) (value *big.Int) {
	value = big.NewInt(0)
	if n.Cmp(bigZero) == 0 {
		return
	}
	previous := value
	value = big.NewInt(1)
	for i := big.NewInt(1); i.Cmp(n) < 0; i.Add(i, bigOne) {
		previous, value = value, previous.Add(previous, value)
	}
	return
}

// NaïveRecursive_uint is the naïve recursive implementation, i.e. it is not tail recursive, with a uint as
// parameter. The performance of this implementation is exponential and so it is not at all useful.
func NaïveRecursive_uint(n uint) *big.Int {
	return NaïveRecursive_bigInt(big.NewInt(int64(n)))
}

// NaïveRecursive_bigInt is the naïve recursive implementation, i.e. it is not tail recursive, with a big.Int
// as parameter. The performance of this implementation is exponential and so it is not at all useful.
func NaïveRecursive_bigInt(n *big.Int) (value *big.Int) {
	if n.Cmp(bigOne) <= 0 {
		value = n
	} else {
		value = new(big.Int).Add(NaïveRecursive_bigInt(new(big.Int).Sub(n, bigOne)), NaïveRecursive_bigInt(new(big.Int).Sub(n, bigTwo)))
	}
	return
}

var memoData = make(map[*big.Int]*big.Int)

// MemoizedRecursive_uint is the naïve recursive implementation with a uint parameter but with memoization
// so that the performance is not exponential, making this implementation a useful one.
func MemoizedRecursive_uint(n uint) *big.Int {
	return MemoizedRecursive_bigInt(big.NewInt(int64(n)))
}

// MemoizedRecursive_big.Int is the naïve recursive implementation with a big.Int parameter but with
// memoization so that the performance is not exponential, making this implementation a useful one.
func MemoizedRecursive_bigInt(n *big.Int) (value *big.Int) {
	value, ok := memoData[n]
	if !ok {
		if n.Cmp(bigOne) <= 0 {
			value = n
		} else {
			value = new(big.Int).Add(MemoizedRecursive_bigInt(new(big.Int).Sub(n, bigOne)), MemoizedRecursive_bigInt(new(big.Int).Sub(n, bigTwo)))
		}
		memoData[n] = value
	}
	return
}
