package factorial

import munit.FunSuite

class FibonacciSuite extends FunSuite {

  val factssequence = List(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880)
  val facts = List(1, 1, 2, 6, 24, 120, 720)
  val fibEmpty = List()

  test("factorial") {
    val results = (0 until facts.length).map(factorial)

    assertEquals(results.toList, facts)
  }

  test("factorialSequence") {
    val results = (0 until factssequence.length).map(factorial)

    assertEquals(results.toList, factssequence)
  }

  test("factorialEmpty") {
    val results = (0 until fibEmpty.length).map(factorial)

    assertEquals(results.toList, fibEmpty)
  }

}
