package uk.org.winder.maths

import spock.lang.Specification
import spock.lang.Unroll

class Fibonacci_Spock extends Specification {
  static data = [
      [0, 0G],
      [1, 1G],
      [2, 1G],
      [3, 2G],
      [4, 3G],
      [5, 5G],
      [6, 8G],
      [7, 13G],
      [8, 21G],
      [9, 34G],
      [10, 55G],
      [11, 89G],
      [12, 144G],
      [13, 233G],
      [14, 377G],
      [15, 610G],
      [16, 987G],
      [17, 1597G],
      [18, 2584G],
      [19, 4181G],
      [20, 6765G],
  ]

  static algorithms = [
      [Fibonacci.&iterative, "iterative"],
      [Fibonacci.&naïveRecursive, "naïveRecursive"],
      [Fibonacci.&memoizedRecursive, "memoizedRecursive"],
      [Fibonacci.&closedForm, "closedForm"],
  ]

  @Unroll def '#name(#value) should be #result'() {
    expect: result == algorithm.call(value)
    where: [algorithm, name, value, result] << algorithms.collectMany{a -> data.collect{v -> [*a, *v]}}
  }

  @Unroll def '#name(#value) [negative argument] should raise an exception'() {
    when: algorithm.call(value)
    then: thrown(IllegalArgumentException)
    where:[algorithm, name, value] << algorithms.collectMany{a -> [-1, -2, -5, -10, -15, -20].collect{v -> [*a, v]}}
  }

  def 'iterative of a huge number succeeds'() {
    when: Fibonacci.iterative(10000)
    then: notThrown(StackOverflowError)
  }

  def 'naïve recursive of a huge number fails with a stack overflow'() {
    when: Fibonacci.naïveRecursive(10000)
    then: thrown(StackOverflowError)
  }

  def 'memoizedRecursive of a huge number fails with a stack overflow'() {
    when: Fibonacci.memoizedRecursive(10000)
    then: thrown(StackOverflowError)
  }

  @Unroll def '#name(#value) [floating point argument] should raise an exception'() {
    when: algorithm.call(value)
    then: thrown(MissingMethodException)
    where:[algorithm, name, value] << algorithms.collectMany{a -> [20.5, 10.5, 5.5, 0.5, -0.5, -5.5, -10.5, -20.5].collect{v -> [*a, v]}}
  }

  def 'random access of the sequence works using static accessor'() {
    expect:
    Fibonacci.Sequence.getAt(7) == 13G
    Fibonacci.Sequence.getAt(4) == 3G
    Fibonacci.Sequence.getAt(7) == 13G
    Fibonacci.Sequence.getAt(12) == 144G
    Fibonacci.Sequence.getAt(8) == 21G
  }

  def 'random access of the sequence works using instance accessor'() {
    given: def fs =  new Fibonacci.Sequence()
    expect: fs[i] == expected
    where: [i, expected] << data
  }

  def 'iterator call result delivers the correct sequence'() {
    given: def fs = Fibonacci.Sequence.iterator()
    expect: data.each{datum -> assert fs.next() == datum[1]}
  }

  def 'iterator instance delivers the correct sequence'() {
    given: def fs = new Fibonacci.Sequence.Iterator()
    expect: data.each{datum -> assert fs.next() == datum[1]}
  }
}
