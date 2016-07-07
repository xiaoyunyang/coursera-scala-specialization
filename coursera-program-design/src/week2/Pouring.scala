package week2

//Pouring(4,9) means given two glasses with capacity 4 and 9 
class Pouring(capacity: Vector[Int]) {

  val initialState = capacity map (x => 0)
  val initialPath = new Path(Nil)
  val initialPathOptimized = new PathOptimized(Nil, initialState)
  
  val glasses = 0 until capacity.length  
  
  val moves = 
    (for (g <- glasses) yield Empty(g)) ++
    (for (g <- glasses) yield Fill(g)) ++
    (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))
  
  
 def fromInefficient(paths: Set[Path]): Stream[Set[Path]] = {
   if(paths.isEmpty) Stream.empty
   else {
     //generate all the possible new paths
      val more = for {
        path <- paths
        next <- moves map path.extend //extend each path to give new path
      } yield next
      paths #:: fromInefficient(more)
    }
  }
  //this is a depth first search approach?
  def from(paths: Set[PathOptimized], explored: Set[State]): Stream[Set[PathOptimized]] = {
    if(paths.isEmpty) Stream.empty
    else {
      //generate all the possible new paths
      val more = for {
        path <- paths
        next <- moves map path.extend //extend each path to give new path
        if !(explored contains next.endState)
      } yield next
      paths #:: from(more, explored ++ (more map (_.endState)))
    }
  }   
    
  //this gives me first all the paths of length 1 from my initial path, then all the paths
  //of length 2 from my initial path, ad infinitum
  val pathSetsInefficient = fromInefficient(Set(initialPath))
  val pathSets = from(Set(initialPathOptimized), Set(initialState))

  def solution(target: Int): Stream[PathOptimized] = {
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
  }
    
  /** States **/
  
  //a vector of integers
  type State = Vector[Int] //One entry per glass

  
  /** Moves **/
  trait Move {
    def change(state: State): State //this has to be implemented by each case classes 
  }
  case class Empty(glass: Int) extends Move {
    def change(state: State): State = state updated (glass, 0) 
  }
  case class Fill(glass: Int) extends Move {
    def change(state: State): State = state updated (glass, capacity(glass)) 
  }
  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State): State = {
      //amount being poured is the minimum of the from-glass state and the final state of 
      //the to-glass after the pour 
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to) + amount)
    }
    
  }
   
  /** Paths **/
  class Path(history: List[Move]) {
    //another optimization we can do is the endState gets recomputed every time. 
    //We can store the endState into the constructor of Path
    def endState: State = (history foldRight initialState)(_ change _)
    def endState2: State = trackState(history)
    
    //trackState is exactly the foldRight
    private def trackState(xs: List[Move]): State = xs match {
      case Nil => initialState
      case move :: xs1 => move change trackState(xs1)
    }
    
    def extend(move: Move) = new Path(move :: history)
    override def toString = (history.reverse mkString " ") + "--> " + endState
  }
  
  class PathOptimized(history: List[Move], val endState: State) {
    def extend(move: Move) = new PathOptimized(move :: history, move change endState)
    override def toString = (history.reverse mkString " ") + "--> " + endState
  }
  
}