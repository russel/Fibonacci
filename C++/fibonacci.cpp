#include <map>

#include "fibonacci.hpp"

namespace Fibonacci {

unsigned long long naiveRecursive(const unsigned long long n) {
  return n == 0 ? 0 : n == 1 ? 1 : naiveRecursive(n -1) + naiveRecursive(n - 2);
}

unsigned long long memoizedRecursive(const unsigned long long n) {
  static std::map<unsigned long long,unsigned long long> memo;
  if (memo.count(n) == 0) {
    switch (n) {
     case 0: memo[0] = 0; break;
     case 1: memo[1] = 1; break;
     default: memo[n] = memoizedRecursive(n -1) + memoizedRecursive(n - 2); break;
    }
  }
  return memo[n];
}

unsigned long long iterative(const unsigned long long n) {
  unsigned long long result = 0;
  unsigned long long next = 1;
  for (unsigned long long i = 0; i < n; ++i) {
    unsigned long long temporary = result;
    result = next;
    next += temporary;
  }
  return result;
}

std::vector<unsigned long long> Sequence::data;

Sequence::Sequence() {
  if (data.size() == 0) {
    data.push_back(0);
    data.push_back(1);
  }
}

unsigned long long Sequence::operator [](const unsigned long long index) {
  if (index >= data.size()) {
    for (unsigned long long i = data.size(); i <= index; ++i) {
      data.push_back(data[i - 1] + data[i - 2]);
    }
  }
  return data[index];
}

}
