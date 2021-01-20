object Assignment10 {
  def main(args: Array[String]): Unit = {
    /*Task 1. Implement a generator of pairs (a,b) such as that a is a positive integer and b is its divisor.
    So that the first pairs will be: (1,1),(2,1),(2,2),(3,1),(3,3),(4,1),(4,2),(4,4)…
    Make sure the generator will be lazily evaluated. Using methods take and next print the first 20 pairs.
    */
    Divide.buffered take 20 foreach println

    altDivide take 20 foreach println

    /*Task 2.	Modify the Maybe class from assignment 9 to implement map and flatMap methods. Write code to present their use.*/
    val no = No()
    val yes = Yes(20)

    no.map(funNum2Str)
    no.flatMap(fun)
    yes.map(funNum2Str)
    yes.flatMap(fun)
}

  def Divide: Iterator[(Int, Int)] = for{
    n<-Iterator.from(1)
    m<-1 until (n+1) if n%m==0
  } yield (n,m)

  def altDivide: Iterator[(Int, Int)] = for{
    n <- Iterator.from(1)
    m = findLCD(n)
  } yield(n,m)

  def findLCD(n: Int): Int = {
    var res = 1
    if (n % 2 == 0) {
      res = 2
    } else {
      var i = 3
      var found = false
      while (i <= n && !found) {
        if (n % i == 0) {
          res = i
          found = true
        }
        i += 1
      }
    }
    res
  }

  def YesFlatMap(a:Int): Maybe[Int] = {
    Yes(a + 10)
  }

  trait Maybe[+A]{
    def map[R] (f:A => R): Unit
    def flatMap[R](f:A => Maybe[R]): Unit
  }

  case class No() extends Maybe[Nothing]{
    override def map[R](f: Nothing => R): Unit = println(No())
    override def flatMap[R](f: Nothing => Maybe[R]): Unit = println(No())
  }

  case class Yes[A](value:A) extends Maybe[A]{
    override def map[R](f: A => R): Unit = println(Yes(f(value)))
    override def flatMap[R](f: A => Maybe[R]): Unit = println(f(value))
  }

  def fun(value:Int):Maybe[Int] = Yes(value+100)
  def funNum2Str(value:Int):List[String] =  List(value.toString, value.toString)
}
