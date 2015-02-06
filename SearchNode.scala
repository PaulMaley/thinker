abstract class SearchNode {
  def state: State
  def parent: Option[SearchNode]
  def action: Option[Action]
  def cost: Float
}

class RootNode(s: State) extends SearchNode {
  def state = s
  def parent = None
  def action = None
  def cost = 0f
}

class Node(s: State, p: SearchNode, a: Action, _cost: Float) extends SearchNode {
	val cost = _cost 
	def state = s
	def parent = Some(p)
	def action = Some(a)
}

abstract class Strategy {
  def nextNode(): (List[SearchNode]) => (SearchNode, List[SearchNode])
}