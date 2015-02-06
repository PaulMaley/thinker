import scala.collection.mutable 

object AStarSearch {
	def main(argv: Array[String]) {
		val A = new AStarSearch(new TouringRomaniaProblem('Arad))
		
		A.search() match {
	    	case None => println("No solution found")
	    	case Some(l) => l.reverse map(e => println(e.name))
		}
	}
}

class AStarSearch(problem: Problem) {
	// Get the necessary functions from the problem
	val cost = problem.getCostFunction()
	val heuristic = problem.getHeuristicFunction()
	val successor = problem.getSuccessorFunction()

	// Could modify to pass in a start state rather than get it from the problem
	def search(): Option[List[Action]] = {
		var fringe: List[SearchNode] = List(new RootNode(problem.start()))
		var path: List[Action] = List()

		// Replace with selection based on cost and heuristic
		//def select(f: List[SearchNode]): (SearchNode, List[SearchNode]) = (f.head, f.tail)
		def value(n: SearchNode) = n.cost + heuristic(n.state)
		
		// Dropping the chosen entry from fringe is problematic ...
		// Massive amounts of inefficiency ... !!
		// This should be implemented with a Set 
		def select(f: List[SearchNode]): (SearchNode, List[SearchNode]) = {
			val v = f.map (n => value(n))
			//System.out.println(v)
			val idx = v.indexOf(v.min)
			//System.out.println(idx)
			(f(idx), f.take(idx):::f.drop(idx+1)) 
		}
		
		// Replace with recursive call
		while (true) {
			// No more nodes ...
			if (fringe.isEmpty) 
				return None
			  
			// Get next node - The Strategy determines how to choose a node
			// Ignore that for the moment and take the first node
			val (node, newFringe) = select(fringe) //strategy.nextNode(fringe)

			// "Debug"
			//System.out.println(node.state.name)
			System.out.println(fringe)
				  
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
			// Expand the fringe		  											
			fringe = newFringe:::x  
		}
		  
		// We never get here !!
		Some(path)
	}
	  
	def extractSearchPath(n: SearchNode) : List[Action] = {
		n.parent match {
			case None => List()
		    case Some(p) => n.action match { case Some(a) => a::extractSearchPath(p)
		        							 case None => List() } // This should never happen
		}
	} 
}



