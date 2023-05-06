package fibonacci

import munit.FunSuite

class FiboActionSuite extends FunSuite {

  val fibossequence = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597)
  val fibos = List(0, 1, 1, 2, 3, 5, 8, 13, 21)
  val fibEmpty = List()

  test("fiboIter") {
    val results = (0 until fibos.length).map(fibonacciIter)

    assertEquals(results.toList, fibos)
  }

  test("fibonacci") {
    val results = (0 until fibos.length).map(fibonacci)

    assertEquals(results.toList, fibos)
  }

  test("fibonacciLarge") {
    val results = (0 until fibossequence.length).map(fibonacci)

    assertEquals(results.toList, fibossequence)
  }
  test("fibonacciEmpty") {
    val results = (0 until fibEmpty.length).map(fibonacci)

    assertEquals(results.toList, fibEmpty)
  }

  test("fibonacci_2") {
    val results = (0 until fibos.length).map(fibonacci_2)

    assertEquals(results.toList, fibos)
  }

  test("fibonacciLarge_2") {
    val results = (0 until fibossequence.length).map(fibonacci_2)

    assertEquals(results.toList, fibossequence)
  }
  test("fibonacciEmpty_2") {
    val results = (0 until fibEmpty.length).map(fibonacci_2)

    assertEquals(results.toList, fibEmpty)
  }

}
