package fibonacci

import (
	"math/big"
	"math/rand"
	"reflect"
	"testing"
	"testing/quick"
)

func checkCorrectUintValues(t *testing.T, function func(uint) *big.Int) {
	property := func(i uint) bool {
		v := new(big.Int)
		return function(i+2).Cmp(v.Add(function(i+1), function(i))) == 0
	}
	values := func(slice []reflect.Value, rand *rand.Rand) {
		for i := 0; i < len(slice); i++ {
			slice[i] = reflect.ValueOf(uint(rand.Int63n(1000)))
		}
	}
	if err := quick.Check(property, &quick.Config{100, 0.0, nil, values}); err != nil {
		t.Error(err)
	}
}

func Test_check_iterative_uint(t *testing.T) {
	checkCorrectUintValues(t, Iterative_uint)
}

// NaïveRecursive has exponential behaviour so do not test it.

//func Test_check_naïveRecursive_uint(t *testing.T) {
//	checkCorrectUintValues(t, NaïveRecursive_uint)
//}

//func Test_check_memoizedRecursive_uint(t *testing.T) {
//	checkCorrectUintValues(t, MemoizedRecursive_uint)
//}

func checkCorrectBigIntValues(t *testing.T, function func(*big.Int) *big.Int) {
	property := func(i *big.Int) bool {
		ip2 := big.NewInt(2)
		ip2.Add(i, ip2)
		ip1 := big.NewInt(1)
		ip1.Add(i, ip1)
		vp1 := function(ip1)
		v := function(i)
		return function(ip2).Cmp(v.Add(v, vp1)) == 0
	}
	values := func(slice []reflect.Value, rand *rand.Rand) {
		for i := 0; i < len(slice); i++ {
			slice[i] = reflect.ValueOf(big.NewInt(rand.Int63n(1000)))
		}
	}
	if err := quick.Check(property, &quick.Config{100, 0.0, nil, values}); err != nil {
		t.Error(err)
	}
}

func Test_check_iterative_BigInt(t *testing.T) {
	checkCorrectBigIntValues(t, Iterative_bigInt)
}

// NaïveRecursive has exponential behaviour so do not test it.

//func Test_check_naïveRecursive_BigInt(t *testing.T) {
//	checkCorrectBigIntValues(t, NaïveRecursive_bigInt)
//}

//func Test_check_memoizedRecursive_BigInt(t *testing.T) {
//	checkCorrectBigIntValues(t, MemoizedRecursive_bigInt)
//}
