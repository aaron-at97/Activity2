package state

opaque type State[S, +A] = S => (A, S)

object State:

  def apply[S, A](f: S => (A, S)): State[S, A] = f

  def unit[S, A](a: A): State[S, A] = s => (a, s)

  def get[S]: State[S, S] = s => (s, s)

  def set[S](s: S): State[S, Unit] = _ => ((), s)

  def modify[S](f: S => S): State[S, Unit] =
    for { s <- get; _ <- set(f(s)) } yield ()

  extension [S, A](underlying: State[S, A])

    def run(s: S): (A, S) = underlying(s)

    def flatMap[B](f: A => State[S, B]): State[S, B] =
      s =>
        val (a, s2) = underlying(s)
        f(a)(s2)

    def map[B](f: A => B): State[S, B] =
      underlying.flatMap(a => unit(f(a)))

    def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
      underlying.flatMap(a => sb.map(b => f(a, b)))

  def traverse[S, A, B](as: List[A])(f: A => State[S, B]): State[S, List[B]] =
    as.foldRight(unit(Nil):State[S, List[B]])((a , acc) => f(a).map2(acc)(_ :: _))

  def sequence[S, A](sas: List[State[S, A]]): State[S, List[A]] =
    traverse(sas)(identity)
