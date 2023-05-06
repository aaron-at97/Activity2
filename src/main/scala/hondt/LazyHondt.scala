package hondt

import LazyList.*

object LazyHondt {

  // We'll define a version of the Hondt problem that will use LazyLists in order to compute just
  // the quotients that are needed to get the result.
  // Please study carefully the solution to the previous activity to understand the problem.
  // One of the objectives of this activity is to understand when things are evaluated, so we'll
  // insert some prints in order to trace this.

  // First we create a function which calculates a quotient but, before, it prints that fact
  def quotient(numerator: Int, denominator: Int): Double =
    println(s"computing quotient $numerator / $denominator")
    numerator.toDouble / denominator

  // Giving a party and its votes, we can create the (potentially infinite) LazyList of all quotients
  // All the denominators will be the infinite LazyList 1, 2, 3, 4, ...
  // We'll tag each quotient with the party
  // Hint: Follow the types and map is your friend
  def partyQuotients(party: String, numVotes: Int): LazyList[(String, Double)] =
      from(1).map(i => (party, LazyHondt.quotient(numVotes, i)))


  // Here comes the most difficult method you have to create
  // All the LazyList contains pairs with the name of a party and a quotient.
  // All of them are decreasing on the Double element of the pair.
  // The idea is to LAZILY merge the two LazyList into a single one by selecting the higher first element of
  // the two.
  // In our case it won't happen (all the lazylists are infinite) but there are two simple cases, when one of
  // the lazylists is Empty in wich the result is the other one.
  // Hint: Use pattern-matching over the pair of lazylists to merge !!!
  // The complex case is when both lazylist have at least one element, that is, when we must select which one
  // comes first.
  // Hint: use cons, that is, the smart-constructor to create the result in order to be lazy

  // NOTE: The most straightforward way of merge will evaluate always the first element of each of the list. This
  // is an acceptable solution, but if you want to think more, you can arrive at a (slightly more complex) solution
  // which avoids this initial evaluation. (Hint: lazy val is your friend).

  def merge(
      left: LazyList[(String, Double)],
      right: LazyList[(String, Double)]
  ): LazyList[(String, Double)] = 
    (left, right) match
      case (Empty, r) => right
      case (l, Empty) => left
      case (Cons(head, tail), Cons(head2, tail2)) =>
        if (head()._2 >= head2()._2) cons(head(), merge(tail(), right))
        else cons(head2(), merge(left, tail2()))

  // Now we can create the hondt function. The parameters are:
  // votes: The votes of each party in a Map
  // n: the number of sites to elect
  def hondt(votes: Map[String, Int], n: Int): Map[String, Int] =

    println("pre-quotients") // To understand when things are evaluated

    // We create an Iterator (we could as well create an intermediate List) for
    // the tagged quotients.
    // From a Map we can obtain easily an iterator of the key-value pairs
    // https://www.scala-lang.org/api/3.1.2/scala/collection/Map.html
    // And we can transform the elements inside the iterator
    // https://www.scala-lang.org/api/3.1.2/scala/collection/Iterator.html
    val quotients: Iterator[LazyList[(String, Double)]] = 
      votes.iterator.map { case (party, votes) => partyQuotients(party, votes) }

    println("pre-merge")

    // We can fuse all the LazyList into a single one obtained by fusing two into a pair,
    // then the next one, etc, etc.
    // https://www.scala-lang.org/api/3.1.2/scala/collection/IterableOnceOps.html
    // Hint: combining all by combining pairs seems a task for a fold, isn't it?
    val orderedQuotients: LazyList[(String, Double)] =   
      quotients.foldLeft(LazyList[(String, Double)]())(
        (merged, next) => merge(merged, next))
  
    println("pre-take")

    // One we have a single (and ordered) LazyList will all the tagged-quotients we only
    // have to select the first n
    val selectedQuotients: LazyList[(String, Double)] =
      orderedQuotients.take(n)

    println("start counting ...")

    // And then converting the LazyList to a List and then (using the same idea that in the
    // previous activity) count the results.

    val result: Map[String, Int] = 
      val selectList: List[(String, Double)] = selectedQuotients.toList
      selectList.groupMapReduce((party, _) => party)(_ => 1)(_ + _)


    print("done counting ...")

    result
}

/*
  NOTE: Depending on the type of fold the quotients may appear in a different order.

  - The simple version of merge prints:

        pre-quotients
        pre-merge
        computing quotient 11871 / 1
        computing quotient 7365 / 1
        computing quotient 8876 / 1
        computing quotient 42670 / 1
        computing quotient 24115 / 1
        computing quotient 5175 / 1
        computing quotient 5189 / 1
        computing quotient 45029 / 1
        pre-take
        start counting ...
        computing quotient 45029 / 2
        computing quotient 42670 / 2
        computing quotient 24115 / 2
        computing quotient 45029 / 3
        computing quotient 42670 / 3
        computing quotient 45029 / 4
        computing quotient 42670 / 4
        computing quotient 24115 / 3
        computing quotient 11871 / 2
        computing quotient 45029 / 5
        computing quotient 42670 / 5
        computing quotient 45029 / 6
        computing quotient 8876 / 2
        computing quotient 42670 / 6
        computing quotient 24115 / 4
        done counting ...

  - The fully lazy version of merge prints:

        pre-quotients
        pre-merge
        pre-take
        start counting ...
        computing quotient 11871 / 1
        computing quotient 7365 / 1
        computing quotient 8876 / 1
        computing quotient 42670 / 1
        computing quotient 24115 / 1
        computing quotient 5175 / 1
        computing quotient 5189 / 1
        computing quotient 45029 / 1
        computing quotient 45029 / 2
        computing quotient 42670 / 2
        computing quotient 24115 / 2
        computing quotient 45029 / 3
        computing quotient 42670 / 3
        computing quotient 45029 / 4
        computing quotient 42670 / 4
        computing quotient 24115 / 3
        computing quotient 11871 / 2
        computing quotient 45029 / 5
        computing quotient 42670 / 5
        computing quotient 45029 / 6
        computing quotient 8876 / 2
        computing quotient 42670 / 6
        done counting ...

 */
