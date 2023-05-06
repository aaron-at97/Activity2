package sum

import bintree.BinaryTree
import state.State

// Define an action which represents the accumulation of the
// integer passed as parameter (n) into the state (which is
// also an Int). The returned value of the action is Unit (its
// only effect is changing the value of the current state).

// You can implement it various ways, for example:
// - creating the action via State.apply passing an Int => (Unit, Int)
// - using map/flatMap and the State.get / State.set actions
//   - and the combination of map/flatMap can be expressed via for-comprehension
// - using the State.modify and a function Int => Int

def accumulateAction(n: Int): State[Int, Unit] = 
  State.apply((i: Int) => ((), i + n))

def accumulateAction_2(n: Int): State[Int, Unit] =
  State.get.flatMap(acc => State.set(acc + n).map(_ => ()))

def accumulateAction_3(n: Int): State[Int, Unit] = 
  for
    s <- State.get[Int]
    _ <- State.set(s + n)
  yield ()
 
def accumulateAction_4(n: Int): State[Int, Unit] = 
  State.modify((acc: Int) => acc + n)

// Implement a method that sums the elements of a list (which are integers)
// - use the accumulateAction
// - use State.traverse over the list
def sumList(ints: List[Int]): Int =
  State.traverse(ints)(i => accumulateAction(i)).run(0)._2

// Using the same ideas as before, implement a method that sums the elements of a tree
// Use the implementation of BinaryTree.traverse that you have defined
def sumTree(bt: BinaryTree[Int]): Int =
  BinaryTree.traverse(bt)(i => accumulateAction(i)).run(0)._2
