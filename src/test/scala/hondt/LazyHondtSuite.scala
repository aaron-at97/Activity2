package hondt

import munit.FunSuite

import LazyHondt.*

class LazyHondtSuite extends FunSuite {

  test("partyQuotients") {
    val pq = partyQuotients("potato", 100)
    assertEquals(pq.take(3).toList, List(100.0/1, 100.0/2, 100.0/3).map(("potato", _)))
  }

  test("merge") {
    val l1 = LazyList(10.0, 8.0, 5.0).map(("a", _))
    val l2 = LazyList(9.0, 7.0, 6.0).map(("b", _))
    val merged = merge(l1, l2)
    assertEquals(merged.toList, List(("a", 10.0), ("b", 9.0), ("a", 8.0), ("b", 7.0), ("b", 6.0), ("a", 5.0)))
  }

  test("lleida") {
    val n: Int = 15
    val votes: Map[String, Int] =
      Map("JxCat" -> 45_029,
        "ERC" -> 42_670,
        "PSC" -> 24_115,
        "CUP-G" -> 11_871,
        "VOX" -> 8_876,
        "PDeCAT" -> 7_365,
        "PP" -> 5_189,
        "Cs" -> 5_175)
    val result =
      Map("CUP-G" -> 1,
        "JxCat" -> 5,
        "VOX" -> 1,
        "ERC" -> 5,
        "PSC" -> 3)

    assertEquals(hondt(votes, n), result)
  }
}
