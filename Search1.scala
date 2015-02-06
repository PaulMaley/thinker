import scala.collection.mutable 

/*
 * This class provides the Search mechanics
 * The Problem class (trait) must provide a certain
 * number of services 
 */
class BasicStrategy extends Strategy {
	def nextNode() = (f: List[SearchNode]) => (f.head, f.tail) 
}


object Search1 {
	def main(args: Array[String]) {
	  
	  // Strategy determines the choice of node ... 
	  // Returns the selected node and the remaining nodes
//	  def strategy(fringe: List[SearchNode]): (SearchNode, List[SearchNode]) = (fringe.head, fringe.tail)
	  val basicStrategy = new BasicStrategy 
	  
	  // We can have different heuristics, so keep it as a function
	  def heuristic(node: SearchNode) : Float = 0f

	  // Cost of going from one state to another is function of initial state and final state
	  // This needs to be provided by the Problem
	  //def transitionCost(si: State, sf: State) : Float = 0f
	  
	  // The fringe
	  def search(problem: Problem, strategy: Strategy): Option[List[Action]] = {
		  var fringe: List[SearchNode] = List(new RootNode(problem.start()))
		  var path: List[Action] = List()
		  
		  def extractSearchPath(n: SearchNode) : List[Action] = {
		    n.parent match {
		      case None => List()
		      case Some(p) => n.action match { case Some(a) => a::extractSearchPath(p)
		        							   case None => List() } // This should never happen
		    }
		  } 
		  
		  // Get the cost function from the problem
		  val cost = problem.getCostFunction()
		  val heuristic = problem.getHeuristicFunction()
		  val successor = problem.getSuccessorFunction()
		  val select = strategy.nextNode()
		  
		  // Replace with recursive call
		  while (true) {
			  // No more nodes ...
			  if (fringe.isEmpty) 
				  return None
			  
			  // Get next node - The Strategy determines how to choose a node
			  // Ignore that for the moment and take the first node
			  val (node, newFringe) = select(fringe) //strategy.nextNode(fringe)

			  // "Debug"
			  System.out.println(node.state.name)
				  
			  // path needs to be built !!
			  if (problem.isGoal(node.state) )
			    return Some(extractSearchPath(node)) 
			  
			  // Not a goal node - expand fringe
			  // For each successor make a new search node object and add it to the fringe
			  // Each new node has the successor state, the current node as parent and
			  // the action taken to get to that state
			  val x = successor(node.state).map( succ => new Node(succ._2, 
					  											  node, 
					  											  succ._1, 
					  											  node.cost + cost(node.state, succ._2)) )  
			  fringe = newFringe:::x  
		  }
		  
		  // We never get here !!
		  Some(path)
	  }
	  
	  search(new ExampleProblem1, basicStrategy) match {
	    case None => println("No solution found")
	    case Some(l) => l.reverse map(e => println(e.name))
	  }
			  		  
	}
}

		  /* This is wrong ... but it's a start worth looking at
		  def recursiveSearch(fringe: List[SearchNode]) : Option[SearchNode] = 
		    fringe match {
		    case Nil => None
		    case _ => {val node = strategy(fringe)
		      			if (problem.isGoal(node.state)) Some(node) 
		      			else recursiveSearch((fringe-node)::successor(node)) 
		      			} 
		  }
		  */
