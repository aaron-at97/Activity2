package factorial

import state.State

def multByN(n: Int): State[Int, Unit] = State.modify(_ * n)

def factorial(n: Int): Int =
  State.traverse((1 to n).toList)(multByN).run(1)._2
