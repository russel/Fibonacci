package fibonacci

import "math/big"

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

func NaïveRecursive_uint(n uint) *big.Int {
	return NaïveRecursive_bigInt(big.NewInt(int64(n)))
}

func NaïveRecursive_bigInt(n *big.Int) (value *big.Int) {
	if n.Cmp(bigOne) <= 0 {
		value = n
	} else {
		value = new(big.Int).Add(NaïveRecursive_bigInt(new(big.Int).Sub(n, bigOne)), NaïveRecursive_bigInt(new(big.Int).Sub(n, bigTwo)))
	}
	return
}

var memoData = make(map[*big.Int]*big.Int)

func MemoizedRecursive_uint(n uint) *big.Int {
	return MemoizedRecursive_bigInt(big.NewInt(int64(n)))
}

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
