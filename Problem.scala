/*
 *  A class to encapsulate what is necessary for the search
 *  At some point replace this with an interface or trait?
 *  So that we don't force users to extend a class 
 */
//abstract class Problem(world: Map[State,List[(Action, State)]], start: State, goals: List[State]) {
abstract class Problem {
  def start(): State
  def isGoal(s: State): Boolean

  // Returns the successor function
  // For a given state this returns a list of possible actions and the 
  // new states corresponding to each action 
  def getSuccessorFunction(): (State) => List[(Action, State)] 

  // Return the cost function
  // For a given initial and final state returns the cost of the 
  // transition from initial to final
  // The default is no cost
  def getCostFunction(): (State, State) => Float = (si: State, sf: State) => 0f
  
  // Return a heuristic function
  // The default is no heuristic
  def getHeuristicFunction(): (State) => Float = (s: State) => 0f
}