package fibonacci

import (
	"math/big"
	"math/rand"
	"reflect"
	"testing"
	"testing/quick"
)

var algorithms_uint_ = []func(uint) *big.Int{
	Iterative_uint,
	//NaïveRecursive_uint, // Ignore recursive function due to exponential execution behaviour.
	MemoizedRecursive_uint,
}

func Test_CheckUintValues(t *testing.T) {
	for _, algorithm := range algorithms_uint_ {
		property := func(i uint) bool {
			v := new(big.Int)
			return algorithm(i+2).Cmp(v.Add(algorithm(i+1), algorithm(i))) == 0
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
}

var algorithms_bigInt_ = []func(*big.Int) *big.Int{
	Iterative_bigInt,
	// NaïveRecursive_bigInt, // Ignore recursive function due to exponential execution behaviour.
}

func Test_CheckBigIntValues(t *testing.T) {
	for _, algorithm := range algorithms_bigInt_ {
		property := func(i *big.Int) bool {
			ip2 := big.NewInt(2)
			ip2.Add(i, ip2)
			ip1 := big.NewInt(1)
			ip1.Add(i, ip1)
			vp1 := algorithm(ip1)
			v := algorithm(i)
			return algorithm(ip2).Cmp(v.Add(v, vp1)) == 0
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
}
