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

func Test_SpecificUintValues(t *testing.T) {
	for _, algorithm := range algorithms_uint {
		for parameter, expectedString := range test_data {
			expected, success := new(big.Int).SetString(expectedString, 10)
			if !success {
				t.Errorf("Failed to create the expected %s value.\n", expectedString)
			}
			assert.Equal(t, expected, algorithm(parameter))
		}
	}
}

func Test_SpecificBigIntValues(t *testing.T) {
	for _, algorithm := range algorithms_bigInt {
		for parameter, expectedString := range test_data {
			expected, success := new(big.Int).SetString(expectedString, 10)
			if !success {
				t.Errorf("Failed to create the expected %s value.\n", expectedString)
			}
			assert.Equal(t, expected, algorithm(big.NewInt(int64(parameter))))
		}
	}
}
