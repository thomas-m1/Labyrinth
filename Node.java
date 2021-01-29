/* Thomas Michalski
 * 2210 asn3
 * 251027332
 * 2020-12-08
 */

public class Node {
	
	int name;
	boolean mark;
	
	
	//constructor. integer value between 0 and n − 1. n is number of vertices in graph to which the node belongs
	public Node(int name) {
		this.name = name;
	}
	
	
	//marks node with specified value
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	// returns value which the node has been marked
	public boolean getMark() {
		return mark;
	}
	
	//returns name of node
	public int getName() {
		return name;
	}
	

}