import std.range: drop, recurrence, takeOne;
import std.typetuple: TypeTuple ;
import std.typecons: tuple ;

long iterative(immutable long n) {
  auto current = 0;
  auto next = 1;
  for (auto i = 0; i < n; ++i) {
    TypeTuple!(current, next) = tuple(next , current + next);
    // The above works, despite comments on the email list, the following does not work as needed.
    //tuple(current, next) = tuple(next , current + next);
  }
  return current;
}

long declarative(immutable long n) {
  return takeOne(drop(recurrence!"a[n-1] + a[n-2]"(0L, 1L), n)).front;
}

unittest {

  import std.conv: to;

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
                    [14, 377],
                    ] ;


  string message(immutable string functionName, immutable int value, immutable long actual, immutable long expected) {
    return functionName ~ "(" ~ to!string(value) ~ ") = " ~ to!string(actual) ~ " should be " ~ to!string(expected);
  }

  foreach (immutable item; data) {
    immutable iterative_result = iterative(item[0]);
    assert(iterative_result == item[1], message("iterative", item[0], iterative_result, item[1]));

    immutable declarative_result = declarative(item[0]);
    assert(declarative_result == item[1], message("declarative", item[0], declarative_result, item[1]));
  }
}
