/* Thomas Michalski
 * 2210 asn3
 * 251027332
 * 2020-12-08
 */

import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;


public class Labyrinth {
	
	private Graph labyrinth;
	private Stack<Node> pathStack = new Stack<Node>();
	private Node entrance, exit;
	private int key;
	private int keyArray[];
	private String[][] map;
	
	
	//constructor. reads input file and builds graph representing labyrinth
	public Labyrinth(String inputFile) throws LabyrinthException {
		
		try {

			BufferedReader in = new BufferedReader(new FileReader(inputFile));
						
			String line = in.readLine();// scale for program, not used.
			
			//gets width of labyrinth
			line = in.readLine();
			int width = Integer.parseInt(line);
			
			//gets length of labyrinth
			line = in.readLine();
			int length = Integer.parseInt(line);
			
			labyrinth = new Graph(length*width);//initializes graph of size width x length
			line = in.readLine();// reads line with keys
						
			//gets each key and stores in array
			StringTokenizer st = new StringTokenizer(line);
			keyArray = new int[10];
			//converts to int and stores keys in array
			for(int i = 0; i < 9; i++) {
				int tok = Integer.parseInt(st.nextToken());
				keyArray[i] = tok;
			}
			
			map = new String[2*length-1][2*width-1];// initializes a map for the input	
			line = in.readLine();// reads line with keys
			int place = 0; //sets place to store nodes at 0
			
			//searches through labyrinth and assigns nodes and edges
			for(int y = 0; y < (length*2) - 1; y++) { //checks the columns, first column is 0
				for(int x = 0; x < (width*2) - 1; x++) { //checks the rows, first row is 0
					
					if (y % 2 == 0) { //if it is an even numbered row
						if (x % 2 == 0) {//if it is an even numbered column
							
							if(line.charAt(x) == 's'){ // s is the entrance
								entrance = labyrinth.getNode((x/2) + (y/2*width));
								map[y][x] = Integer.toString(place); //add a node

							}
							else if(line.charAt(x) == 'x') { // x is the exit
								exit = labyrinth.getNode((x/2) + (y/2*width));								
								map[y][x] = Integer.toString(place);	

							}
							else if(line.charAt(x) == 'i') { // i is a room
								map[y][x] = Integer.toString(place);		

							}
							place++;
						}
						else { //if it is an odd numbered column
							if(line.charAt(x) == 'c') { // c is a corridor
								labyrinth.insertEdge(labyrinth.getNode(((x-1)/2) + (y/2*width)), labyrinth.getNode(((x+1)/2) + (y/2*width)), -1,  "corridor");

							}
							else if (Character.isDigit(line.charAt(x))) { //represents door of type 0 to 9 
								//it is a key
								key = Character.getNumericValue(line.charAt(x));
								labyrinth.insertEdge(labyrinth.getNode(((x-1)/2) + (y/2*width)), labyrinth.getNode(((x+1)/2) + (y/2*width)), key, "door");
							}
						}
					}
					else { // if odd numbered row
						if (x % 2 == 0) { // if even numbered column
							
							if(line.charAt(x) == 'c') { // w is a wall
								labyrinth.insertEdge(labyrinth.getNode((x/2) + ((y-1)/2*width)), labyrinth.getNode((x/2) + ((y+1)/2*width)), -1,"corridor");			
							}
							else if (Character.isDigit(line.charAt(x))) { //represents door of type 0 to 9 
								//it is a key
								key = Character.getNumericValue(line.charAt(x));
								labyrinth.insertEdge(labyrinth.getNode((x/2) + ((y-1)/2*width)), labyrinth.getNode((x/2) + ((y+1)/2*width)), key, "door");


							}
						}
					}				
				}
				line = in.readLine();
			}		 
			in.close();

		}
		catch (Exception e) {	
			throw new LabyrinthException("Error reading file");
		}
	}

	
	//returns reference to Graph object representing labyrinth
	public Graph getGraph() throws LabyrinthException {
		if (labyrinth != null) {
			return labyrinth;
		}
		else {
			throw new LabyrinthException("Graph is empty");
		}
	}
	
	
	//returns java Iterator containing nodes of the path from entrance to exit of labyrinth
	public Iterator solve() {

			try {
				if(!path(entrance)) {//if the solution does not exist, returns null
					return null;
				}
			} catch (GraphException e) {
				e.printStackTrace();
			}
		return pathStack.iterator();
	}
		
		
	//helper function. Called from solve()
	//finds path from start to finish and stores in a stack using a depth first traversal algorithm
	private boolean path(Node s) throws GraphException{
		
		pathStack.push(s);//pushes node into stack
		s.setMark(true);//sets stack as true
		if (s == exit) return true;//returns true if the node is at the exit node
		boolean canMove = false;//checks if can move to the node
		int usedKey = 0;//amount of keys used
		Iterator<Edge> incidentEdges = labyrinth.incidentEdges(s);//iterator for edges
		
		//while there are edges
		while (incidentEdges.hasNext()) {
			Edge e = incidentEdges.next();//sets next edge
			canMove = false;
			//if second node connected to edge is not marked
			if (!e.secondEndpoint().getMark()) {
				
				if(e.getLabel().equals("door")) {//checks if edge is labeled as a door
					int type = e.getType();//gets type of door
					
					//checks through smallest keys available to unlock the door first. if there is a key available then goes through door and subtracts amount of keys 
					for (int i = type; i < keyArray.length; i++) {
						if (keyArray[i] > 0) {
							keyArray[i]--;
							usedKey = i;
							canMove = true;	
							break;
						}
					}
				}
				else {//if it is a corridor (no key needed)
					canMove = true;
				}
				if (canMove) {
					//recursive call
					if (path(e.secondEndpoint())) {
						return true;
					} 
					//adds key back into used key if it does not use that path
					else if(usedKey>0) {
						keyArray[usedKey]++;
						usedKey=0;
					}
				}
				else {
				}
			}
		}
		//removes node from stack if not path was found and sets the mark as false so we can revisit through another path
		pathStack.pop();
		s.setMark(false);
		return false;
	}
}
