package uk.org.winder.math

import spock.lang.Specification
import spock.lang.Unroll

class Fibonacci_Spock extends Specification {
  static data = [
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
      [15, 610],
      [16, 987],
      [17, 1597],
      [18, 2584],
      [19, 4181],
      [20, 6765],
  ]

  static algorithms = [
      [Fibonacci.&iterative,'iterative'],
      [Fibonacci.&recursive,'recursive'],
      [Fibonacci.&tailRecursive,'tailRecursive'],
      [Fibonacci.&tailRecursiveTrampoline, 'taiRecursiveTrampoline'],
      [Fibonacci.&manualMemoizedRecursive, 'manualMemoizedRecursive'],
      [Fibonacci.&memoizedRecursive, 'memoizedRecursive'],
      [Fibonacci.&closedForm, 'closedForm'],
  ]

  @Unroll def "#name(#value) [positive argument] should be #result"() {
    expect: algorithm.call(value) == result
    where: [algorithm, name, value, result] << algorithms.collectMany{algorithm -> data.collect{datum -> [*algorithm, *datum]}}
  }

  @Unroll def "#name(#value) [negative argument] should throw an exception"() {
    when: algorithm.call(value)
    then: thrown(IllegalArgumentException)
    where: [algorithm, name, value] << algorithms.collectMany{algorithm -> [-1, -2, -5, -10, -20].collect{datum -> [*algorithm, datum]}}
  }

  @Unroll def "test indexing on sequence for correct behaviour"() {
    given: def f = Fibonacci.sequence()
    expect: f[i] == expected
    where: [i, expected] << data
  }

  def "test iteration over sequence for correct behaviour"() {
    given: def i = Fibonacci.iterator()
    expect: data.each{datum -> assert i.next() == datum[1]}
  }

}
