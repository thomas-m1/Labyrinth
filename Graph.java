/* Thomas Michalski
 * 2210 asn3
 * 251027332
 * 2020-12-08
 */

import java.util.Stack;
import java.util.*;

public class Graph implements GraphADT {
	
	public Edge adjacentMatrix[][]; //checks if nodes are adjacent
	public Node storeNode[]; // stores node
	int numberofNodes; // number of nodes

	
	
	//constructor. creates empty graph with n nodes and no edges
	public Graph(int n) {//// said n?????
		this.numberofNodes = n;
		this.adjacentMatrix = new Edge[n][n];
		this.storeNode = new Node[n];
		//from 0 -> n-1 for names of the node
		for(int i = 0; i < n; i++) {
			storeNode[i] = new Node(i);
		}
	}
	
	
	//adds an edge to graph connecting node u and v. includes type
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		//checks if nodes exist
		if((getNode(u.getName()) != null && (getNode(v.getName()) != null))) {
			//store type between nodes
			Edge edge = adjacentMatrix[u.getName()][v.getName()];
			//if edge does not exist, creates edge with type
			if(edge == null) {
				adjacentMatrix[u.getName()][v.getName()] = new Edge(u, v, edgeType);
				adjacentMatrix[v.getName()][u.getName()] = new Edge(v, u, edgeType);
			}
			else {
				throw new GraphException("There is already an edge connecting the given nodes");
			}
		}
		else {
			throw new GraphException("Node not in Graph");
		}
	}
	
	
	//adds an edge to graph connecting node u and v. includes type and label
	public void insertEdge(Node u, Node v, int edgeType, String label) throws GraphException {
		
		if((getNode(u.getName()) != null && (getNode(v.getName()) != null))) {
			//store type between nodes
			Edge edge = adjacentMatrix[u.getName()][v.getName()];
			////if edge does not exist, creates edge with type and label
			if(edge == null) {
				adjacentMatrix[u.getName()][v.getName()] = new Edge(u, v, edgeType, label);
				adjacentMatrix[v.getName()][u.getName()] = new Edge(v, u, edgeType, label);
			}
			else {
				throw new GraphException("There is already an edge connecting the given nodes");
			}
		}
		else {
			throw new GraphException("Node not in Graph");
		}
	}
		
	
	
	//returns node with the specific name
	public Node getNode(int name) throws GraphException {
		while(name >= numberofNodes || name < 0 ) {
			throw new GraphException("Node not in Graph");
		}
		return storeNode[name];
	}
	
	
	//returns Java Iterator storing all edges incident for node u
	public Iterator incidentEdges(Node u) throws GraphException {
		
		if (getNode(u.getName()) != null) {
			Stack stack = new Stack();// creates stack
			
			//checks row and if edge, put in stack
			for(int i = 0; i < numberofNodes; i++) {
				if(adjacentMatrix[u.getName()][i] != null)
					stack.push(adjacentMatrix[u.getName()][i]); // puts in stack
			}
			if (stack.isEmpty()) {
				return null;
			}
			return stack.iterator();
		}
		else
			throw new GraphException("Node not in Graph");
	}
	
	
	// returns a Java Iterator storing all the edges incident on node u
	public Edge getEdge(Node u, Node v) throws GraphException {
		
		if((getNode(u.getName()) != null && (getNode(v.getName()) != null))) {
			Edge edge = adjacentMatrix[u.getName()][v.getName()]; //stores between nodes
			//if u and v are not connecting
			if(edge == null) {
				throw new GraphException ("No edge between u and v");
			}
			else {
				return edge;
			}
		}
		else {
			throw new GraphException("Node not in Graph");
		}
	}

	
	//returns true if node u and v are adjacent
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		
		if((getNode(u.getName()) != null && (getNode(v.getName()) != null))) {
			Edge edge = adjacentMatrix[u.getName()][v.getName()];//stores between nodes
			
			//if u and v are not connecting return false, else true
			if(edge == null)
				return false;
			else {
				return true;
			}
		}
		else {
			throw new GraphException("u or v are not nodes in the graph");
		}
	}
}
