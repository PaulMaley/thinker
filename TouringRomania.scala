/* 
 * Object for developing Search code
 * Purposely don't extend any of the types defined for use in the search code
 * The idea is to see how to make the link between data/code which knows 
 * nothing about my A* code and the A* code itself .....
 */

object TouringRomania {
	// Internal representation
	val cities: List[Symbol] = List('Arad, 'Zerind, 'Oradea, 'Sibiu, 'Timisoara, 
									'Fagaras, 'Pitesti, 'Craiova, 'Lugoj, 'Mehadia, 
									'Dobreta, 'Bucharest, 'RimnicuVilcea)
									
	val roads: List[(Symbol, Symbol, Float)] = List(('Arad, 'Zerind, 75f),
													('Zerind, 'Oradea, 71f),
													('Oradea, 'Sibiu, 151f),
													('Sibiu, 'RimnicuVilcea, 80f),
													('Sibiu, 'Fagaras, 99f),
													('Arad, 'Sibiu, 140f),
													('Arad, 'Timisoara, 118f),
													('Timisoara, 'Lugoj, 111f),
													('Lugoj, 'Mehadia, 70f),
													('Mehadia, 'Dobreta, 75f),
													('Dobreta, 'Craiova, 120f),
													('Craiova, 'RimnicuVilcea, 146f),
													('Craiova, 'Pitesti, 138f),
													('RimnicuVilcea, 'Pitesti, 97f),
													('Fagaras, 'Bucharest, 211f),
													('Pitesti, 'Bucharest, 101f)
													)
	val distFromBucharest = Map('Arad -> 366, 
								'Zerind -> 374, 
								'Oradea -> 380, 
								'Sibiu -> 253, 
								'Timisoara -> 329, 
								'Fagaras -> 176, 
								'Pitesti -> 100, 
								'Craiova -> 160, 
								'Lugoj -> 244, 
								'Mehadia -> 241, 
								'Dobreta -> 242,
								'Bucharest -> 0, 
								'RimnicuVilcea -> 193)
  
}