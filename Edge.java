/* Thomas Michalski
 * 2210 asn3
 * 251027332
 * 2020-12-08
 */
public class Edge {
	
	int type;
	Node start;
	Node end;
	String label;
	
	
	//constructor. parameters for end points of edge and type of edge
	public Edge(Node u, Node v, int type) {
		this.start = u;
		this.end = v;
		this.type = type;
		label = "";
	}
	
	
	//constructor. parameters for end points of edge and type and label of edge
	public Edge(Node u, Node v, int type, String label) {
		this.start = u;
		this.end = v;
		this.type = type;
		this.label = label;
	}
	
	
	//returns first end point of the edge
	public Node firstEndpoint() {
		return start;
		
	}
	
	// returns second end point of the edge
	public Node secondEndpoint() {
		return end;
		
	}
	
	//returns type of the edge
	public int getType() {
		return type;
		
	}
	
	// sets type of the edge to specific value
	public void setType(int newType) {
		this.type = type;
	}
	
	// returns the label of the edge
	public String getLabel() {
		return label;
	}
	
	//sets label of edge to the specific value
	public void setLabel(String newLabel) {
		this.label = label;
	}

}
