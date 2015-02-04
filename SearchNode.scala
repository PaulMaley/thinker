abstract class SearchNode {
  def state: State
  def parent: Option[SearchNode]
  def action: Option[Action]
}

class RootNode(s: State) extends SearchNode {
  def state = s
  def parent = None
  def action = None
}

class Node(s: State, p: SearchNode, a: Action) extends SearchNode {
  def state = s
  def parent = Some(p)
  def action = Some(a)
}