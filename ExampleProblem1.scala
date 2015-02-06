class ActionWithCost(_name: String, _cost: Float) extends Action(_name) {
  val cost = _cost
}


class ExampleProblem1 extends Problem {
	  // Define some states
	  val s1 = new State("S1")
	  val s2 = new State("S2")
	  val s3 = new State("S3")
	  val s4 = new State("S4")
	  val s5 = new State("S5")
	  val s6 = new State("S6")
	  val s7 = new State("S7")

	  // And some actions
	  val a1 = new ActionWithCost("A1", 1f)
	  val a2 = new ActionWithCost("A2", 1f)
	  val a3 = new ActionWithCost("A3", 1f)
	  
	  // Make a state space
	  val world = Map(s1 -> List((a1,s2), (a2, s3)), 
			  		  s2 -> List((a1,s4)), 
			  		  s4 -> List((a2, s5), (a3, s6)),
			  		  s3 -> List((a1,s7)))
			  		  
	  // Goal state
	  val goals = List(s5)
	  
	  def start(): State = s1
	  def isGoal(s: State): Boolean =  goals.contains(s)
	  def getSuccessorFunction(): (State) => List[(Action, State)] = 
	    s => world getOrElse(s,List())
	  
	  override def getCostFunction(): (State, State) => Float = {
	    // Do some horrible inefficient matching ...
	    // Get the action that joins the two states and extract its cost 
	    (si: State, sf: State) => 0f
	  }

}

