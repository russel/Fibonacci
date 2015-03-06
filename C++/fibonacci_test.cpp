#define CATCH_CONFIG_MAIN
#include <catch.hpp>

#include <array>
#include <vector>

#include "fibonacci.hpp"

std::vector<std::array<unsigned long long, 2>> data = {
  {0ull, 0ull},
  {1ull, 1ull},
  {2ull, 1ull},
  {3ull, 2ull},
  {4ull, 3ull},
  {5ull, 5ull},
  {6ull, 8ull},
  {7ull, 13ull},
  {8ull, 21ull},
  {9ull, 34ull},
  {10ull, 55ull},
  {11ull, 89ull},
  {12ull, 144ull},
};

TEST_CASE("NaiveRecursive") {
  for (auto && datum: data) {
    REQUIRE(datum[1] == Fibonacci::naiveRecursive(datum[0]));
	}
}

TEST_CASE("MemoizedRecursive") {
  for (auto && datum: data) {
    REQUIRE(datum[1] == Fibonacci::memoizedRecursive(datum[0]));
	}
}

TEST_CASE("Iterative") {
  for (auto && datum: data) {
    REQUIRE(datum[1] == Fibonacci::iterative(datum[0]));
	}
}

TEST_CASE("SequenceGetAt") {
  Fibonacci::Sequence fibonacci;
  REQUIRE(13ull == fibonacci[7]);
  REQUIRE(3ull == fibonacci[4]);
  REQUIRE(13ull == fibonacci[7]);
  REQUIRE(144ull == fibonacci[12]);
  REQUIRE(21ull == fibonacci[8]);
}

TEST_CASE("SequenceIterator") {
  Fibonacci::Iterator iterator;
	for (auto && datum: data) {
		REQUIRE(datum[1] == *iterator++);
	}
}
