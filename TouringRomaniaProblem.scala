/*
 *  Attempt number 1 .... Hmm. Just build another object model 
 *  						   based on the original!
 *            
 */

// Excude the pun ...
class CityState(_symbol: Symbol, distFromBucharest: Float) extends State(_symbol.name) {
	val symbol = _symbol
	override def toString() = name
}	

class driveTo(name: String) extends Action(name)

class TouringRomaniaProblem(depart: Symbol) extends Problem {
	// Construct a list of states
	val stateSpace = TouringRomania.cities.map(e => new CityState(e, TouringRomania.distFromBucharest(e))) 
	// Then, since original object is built in terms of Symbols make a map from Symbol to state
	val symbolToState = stateSpace.map(e => e.symbol -> e).toMap
	val startLocation: State = symbolToState(depart)
	
	def start() = startLocation
	def isGoal(s: State) = s.name == 'Bucharest.name // Seems a bit dangerous ... no Namespaceing??
	
	// Build the successor function
	// For each State (=City) look through list of roads
	// If city is start or end of a road then add the 
	// road and the other city to the list
	def successors(s: CityState) = {
	  val ss = s.symbol
	  //val z = TouringRomania.roads.filter( e => (ss == e._1 || ss == e._2) ) // w/o pattern matching
	  val z = TouringRomania.roads.filter { case (si,sf,d) => (ss == si || ss == sf) }  // Clearer ??
	  z.map {case (si,sf,d) => (new Action(si.name+"-to-"+sf.name), if (ss == si) symbolToState(sf) else symbolToState(si))}
	}

	val successorMap = stateSpace.map(s => s -> successors(s)).toMap
	
	def getSuccessorFunction = (s: State) => successorMap( symbolToState(Symbol(s.name)) )
	
	
	override def getCostFunction = (si: State, sf: State) => {
	  TouringRomania.roads find {
	    case (s,f,d) => ((s == Symbol(si.name) && f == Symbol(sf.name)) ||
	    			  	 (s == Symbol(sf.name) && f == Symbol(si.name)))
	  }  match {
	    case Some(x) => x._3 // Can't figure out how to pattern match this
	    case None => 0f		 // Should never happen ...
	  }
	}
	
	override def getHeuristicFunction = (s: State) => TouringRomania.distFromBucharest(Symbol(s.name))
}

object TouringRomaniaProblem {
  def main(args: Array[String]) {
    val p = new TouringRomaniaProblem('Arad)
    System.out.println(p.start.name)
    
    System.out.println('Lugoj.name )
    System.out.println(Symbol(p.symbolToState('Lugoj).name) )
    
    val next = p.getSuccessorFunction()
    System.out.println(next(p.start))
    
    val h = p.getHeuristicFunction()
    System.out.println(h(p.start()))
    
    val nextState = next(p.start)(0)._2
    System.out.println(nextState)
    
    val cost = p.getCostFunction()
    System.out.println(cost(p.start, nextState))
    
    
  }
}