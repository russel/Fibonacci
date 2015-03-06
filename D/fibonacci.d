import std.range ;
import std.typetuple ;
import std.typecons ;

long iterative(immutable long n) {
  auto current = 0;
  auto next = 1;
  for (auto i = 0; i < n; ++i) {
    TypeTuple!(current, next) = tuple(next , current + next);
  }
  return current;
}

long declarative(immutable long n) {
  return array(takeExactly(recurrence!("a[n-1] + a[n-2]")(0L, 1L), cast(size_t)(n + 1)))[n];
}

unittest {
  immutable data = [
                    [0, 0],
                    [1, 1],
                    [2, 1],
                    [3, 2],
                    [4, 3],
                    [5, 5],
                    [6, 8],
                    [7, 13],
                    [8, 21],
                    [9, 34],
                    [10, 55],
                    [11, 89],
                    [12, 144],
                    [13, 233],
                    ] ;
  foreach (item; data) {
    assert(iterative(item[0]) == item[1]);
  }
  foreach (item; data) {
    assert(declarative(item[0]) == item[1]);
  }
}
