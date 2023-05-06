package bintree

import munit.FunSuite

import BinaryTree.*
import state.State

class BinaryTreeSuite extends FunSuite {

  test("map3-option") {
    val sa = Some(1)
    val sb = Some(2)
    val sc =  Some(3)
    val n = None
    assertEquals(map3(sa, sb, sc)((_, _, _)), Some(1, 2, 3))
    assertEquals(map3(n, sb, sc)((_, _, _)), None)
    assertEquals(map3(sa, n, sc)((_, _, _)), None)
    assertEquals(map3(sa, sb, n)((_, _, _)), None)
  }

  test("map3-option_2") {
    val sa = Some(1)
    val sb = Some(2)
    val sc =  Some(3)
    val n = None
    assertEquals(map3_2(sa, sb, sc)((_, _, _)), Some(1, 2, 3))
    assertEquals(map3_2(n, sb, sc)((_, _, _)), None)
    assertEquals(map3_2(sa, n, sc)((_, _, _)), None)
    assertEquals(map3_2(sa, sb, n)((_, _, _)), None)
  }

  test("traverse-option") {
    val tree = Branch(Branch(Empty, 1, Empty), 2, Branch(Empty, 3, Empty))
    assertEquals(traverse(tree)(Some.apply), Some(tree))
    def onlyEven(n: Int) = Some(n).filter(_ % 2 == 0)
    assertEquals(traverse(tree)(onlyEven), None)
  }
    test("traverse-option_2") {
    val tree = Branch(Branch(Empty, 1, Empty), 2, Branch(Empty, 3, Empty))
    assertEquals(traverse_2(tree)(Some.apply), Some(tree))
    def onlyEven(n: Int) = Some(n).filter(_ % 2 == 0)
    assertEquals(traverse_2(tree)(onlyEven), None)
  }

    test("traverse-option_3") {
    val tree = Branch(Branch(Empty, 1, Empty), 2, Branch(Empty, 3, Empty))
    assertEquals(traverse_3(tree)(Some.apply), Some(tree))
    def onlyEven(n: Int) = Some(n).filter(_ % 2 == 0)
    assertEquals(traverse_3(tree)(onlyEven), None)
  }

  test("map2-list") {
    val l1 = List(1, 2)
    val l2 = List("a", "b")
    assertEquals(map2(l1, l2)(_ -> _), List((1, "a"), (1, "b"), (2, "a"), (2, "b")))
    assertEquals(map2(Nil, l2)(_ -> _), Nil)
    assertEquals(map2(l1, Nil)(_ -> _), Nil)
  }

  test("map2-list_2") {
    val l1 = List(1, 2)
    val l2 = List("a", "b")
    assertEquals(map2_2(l1, l2)(_ -> _), List((1, "a"), (1, "b"), (2, "a"), (2, "b")))
    assertEquals(map2_2(Nil, l2)(_ -> _), Nil)
    assertEquals(map2_2(l1, Nil)(_ -> _), Nil)
  }

  test("map3-list") {
    val l1 = List(1, 2)
    val l2 = List(10, 20)
    val l3 = List(100, 200)
    assertEquals(map3(l1, l2, l3)(_ + _ + _), List(111, 211, 121, 221, 112, 212, 122, 222))
  }

  test("traverse-list") {
    def mkTree(l: Int, v: Int, r: Int) = Branch(Branch(Empty, l, Empty), v, Branch(Empty, r, Empty))
    def sameAndDouble(n: Int) = List(n, n * 2)
    val tree = mkTree(1, 10, 100)
    assertEquals(traverse(tree)(sameAndDouble),
      List((1, 10, 100), (1, 10, 200), (1, 20, 100), (1, 20, 200),
           (2, 10, 100), (2, 10, 200), (2, 20, 100), (2, 20, 200)).map(mkTree.tupled))
  }

  test("map3-state") {
    val s1 = State[Int, Int](s => (s * 2, s + 1))
    val s2 = State[Int, Int](s => (s + 2, s + 2))
    val s3 = State[Int, Int](s => (s / 2, s - 3))
    val combined = map3(s1, s2, s3)((_, _, _))
    assertEquals(combined.run(1), ((2, 4, 2), 1))
  }

  test("traverse-state") {
    val tree = Branch(Branch(Empty, 1, Empty), 2, Branch(Empty, 3, Empty))
    def push(n: Int): State[List[Int], Int] =
      State(ns => (n, n :: ns))
    assertEquals(traverse(tree)(push).run(Nil), (tree, List(3, 2, 1)))
  }
}
