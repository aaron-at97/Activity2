package hondt

import LazyList.* // To use the definitions of the companion in the enum

enum LazyList[+A]:
  case Empty
  case Cons(head: () => A, tail: () => LazyList[A])

  def foldRight[B](z: => B)(f: (=> A, => B) => B): B =
    this match
      case Cons(h, t) => f(h(), t().foldRight(z)(f))
      case _          => z

  def take(n: Int): LazyList[A] =
    this match
      case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
      case _                   => Empty

  def map[B](f: A => B): LazyList[B] =
    foldRight(empty)((a, b) => cons(f(a), b))

  def toList: List[A] =
    foldRight(Nil: List[A])(_ :: _)

object LazyList:

  def cons[A](head: => A, tail: => LazyList[A]): LazyList[A] =
    lazy val h = head
    lazy val t = tail
    Cons(() => h, () => t)

  def empty[A]: LazyList[A] = Empty

  def from(n: Int): LazyList[Int] = cons(n, from(n + 1))

  def apply[A](as: A*): LazyList[A] =
    as.foldRight(empty)(cons)
