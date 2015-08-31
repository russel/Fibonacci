package fibonacci

import (
	"github.com/stretchr/testify/assert"
	"math/big"
	"testing"
)

var test_data = map[uint]string{
	0:  "0",
	1:  "1",
	2:  "1",
	3:  "2",
	4:  "3",
	5:  "5",
	6:  "8",
	7:  "13",
	8:  "21",
	9:  "34",
	10: "55",
	11: "89",
	12: "144",
	13: "233",
	14: "377",
	15: "610",
	16: "987",
	17: "1597",
	18: "2584",
	19: "4181",
	20: "6765",
}

// #########################################################

func allCorrectUintValues(t *testing.T, function func(uint) *big.Int) {
	for parameter, expectedString := range test_data {
		expected, success := new(big.Int).SetString(expectedString, 10)
		if !success {
			t.Errorf("Failed to create the expected %s value.\n", expectedString)
		}
		assert.Equal(t, expected, function(parameter))
	}
}

func Test_iterative_uint(t *testing.T) {
	allCorrectUintValues(t, Iterative_uint)
}

func Test_recursive_uint(t *testing.T) {
	allCorrectUintValues(t, NaïveRecursive_uint)
}

func Test_memoizedRecursive_uint(t *testing.T) {
	allCorrectUintValues(t, MemoizedRecursive_uint)
}

func allCorrectBigIntValues(t *testing.T, function func(*big.Int) *big.Int) {
	for parameter, expectedString := range test_data {
		expected, success := new(big.Int).SetString(expectedString, 10)
		if !success {
			t.Errorf("Failed to create the expected %s value.\n", expectedString)
		}
		assert.Equal(t, expected, function(big.NewInt(int64(parameter))))
	}
}

func Test_iterative_bigInt(t *testing.T) {
	allCorrectBigIntValues(t, Iterative_bigInt)
}

func Test_recursive_bigInt(t *testing.T) {
	allCorrectBigIntValues(t, NaïveRecursive_bigInt)
}

func Test_memoizedRecursive_bigInt(t *testing.T) {
	allCorrectBigIntValues(t, MemoizedRecursive_bigInt)
}
