#if ! defined(FIBONACCI_CPP)
#define FIBONACCI_CPP

#include <vector>

namespace Fibonacci {

unsigned long long naiveRecursive (const unsigned long long n);
unsigned long long memoizedRecursive (const unsigned long long n);
unsigned long long iterative (const unsigned long long n);

class Sequence {
 private:
  static std::vector<unsigned long long> data;
 public:
  Sequence();
  unsigned long long operator[] (const unsigned long long index);
};

class Iterator {
 private:
  unsigned long long index;
  Sequence datum;
 public:
  Iterator(): index(0) {}
  unsigned long long operator*() { return datum[index]; }
  Iterator & operator ++() { ++index; return *this; }
  Iterator operator ++(int) { Iterator t = *this; ++*this; return t; }
};

}

#endif
