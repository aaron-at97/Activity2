package fibonacci

def fibonacciIter(n: Int): Int =
  var i = 0
  var a = 0
  var b = 1
  while (i < n) {
    val tmp = a
    a = b
    b = tmp + b
    i = i + 1
  }
  a

import state.State
import State.*

def fibonacci(n: Int): Int =
  case class FiboState(i: Int, a: Int, b: Int)
  lazy val fibonacciAction: State[FiboState, Int] =
    if (n == 0) unit(0)
    else modify[FiboState](s => FiboState(s.i + 1, s.b, s.a + s.b))
      .flatMap(_ => get[FiboState])
      .flatMap(s => if (s.i < n) fibonacciAction else unit(s.a))

  fibonacciAction.run(FiboState(0, 0, 1))._1

def fibonacci_2(n: Int): Int =
  case class FiboState(i: Int, a: Int, b: Int)
  lazy val fibonacciAction: State[FiboState, Int] =
    get[FiboState].flatMap ( s =>
      if (s.i < n)
        set(FiboState(s.i + 1, s.b, s.a + s.b)).flatMap(_ => fibonacciAction)
      else 
        get.map(_.a))
    
  fibonacciAction.run(FiboState(0, 0, 1))._1

