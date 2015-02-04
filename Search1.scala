import scala.collection.mutable 

class Problem(world: Map[State,List[(Action, State)]], start: State) {
  def start(): State = start
}

object Search1 {
	def main(args: Array[String]) {
	  // Define some states
	  val s1 = new State("S1")
	  val s2 = new State("S2")
	  val s3 = new State("S3")
	  val s4 = new State("S4")
	  val s5 = new State("S5")
	  val s6 = new State("S6")
	  val s7 = new State("S7")

	  // And some actions
	  val a1 = new Action("A1")
	  val a2 = new Action("A2")
	  val a3 = new Action("A3")
	  
	  // Make a state space
	  val world = Map(s1 -> List((a1,s2), (a2, s3)), 
			  		  s2 -> List((a1,s4)), 
			  		  s4 -> List((a2, s5), (a3, s6)),
			  		  s3 -> List((a1,s7)))

	  val goals = List(s5)		  		  
	  def isGoal(s: State):Boolean =  goals.contains(s)
			 
	  // Successor ... Refactor into closure ..
	  def successor(s: State) = world getOrElse(s,List())
	  
	  // Strategy determines the choice of node ... 
	  def strategy(fringe: List[SearchNode]): SearchNode = fringe.head
	  
	  // The fringe
	  def search(problem: Problem): Option[List[Action]] = {
		  var fringe: List[SearchNode] = List(new RootNode(problem.start()))
		  var path: List[Action] = List()
		  
		  def extractSearchPath(n: SearchNode) : List[Action] = {
		    n.parent match {
		      case None => List()
		      case Some(p) => n.action match { case Some(a) => a::extractSearchPath(p)
		        							   case None => List() } // This should never happen
		    }
		  } 
		  
		  // Replace with recursive call
		  while (true) {
			  // No more nodes ...
			  if (fringe.isEmpty) 
				  return None
			  
			  // Get next node - The Strategy determines how to choose a node
			  // Ignore that for the moment and take the first node
			  val node = fringe.head

			  // "Debug"
			  System.out.println(node.state.name)
				  
			  // path needs to be built !!
			  if ( isGoal(node.state) )
			    return Some(extractSearchPath(node)) 
			  
			  // Not a goal node - expand fringe
			  // For each successor make a new search node object and add it to the fringe
			  // Each new node has the successor state, the current node as parent and
			  // the action taken to get to that state
			  val x = successor(node.state).map( succ => new Node(succ._2, node, succ._1) )  
			  fringe = fringe.tail:::x  
		  }
		  
		  // We never get here !!
		  Some(path)
	  }
	  
	  search(new Problem(world, s1)) match {
	    case None => println("No solution found")
	    case Some(l) => l.reverse map(e => println(e.name))
	  }
			  		  
	}
}

