abstract class Tree
case class Sum(l: Tree, r: Tree) extends Tree
case class Var(n: String) extends Tree
case class Const(v: Int) extends Tree


object Test extends App {

  def derive(t: Tree, v: String): Tree = t match {
  case Sum(l, r) => Sum(derive(l, v) , derive(r, v))
  case Var(n) if (v==n) =>  Const(1)
	  case _=> Const(0)
	}	  
}	