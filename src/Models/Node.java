package Models;

import java.util.ArrayList;

public class Node {
    public Grid State; //Nodes state
    public Node parent; // Nodes parent
    private int cost; //Cost of reaching from root node
    private int depth;


    public Node(Grid State, Node Parent, int Cost,int depth){
        this.State = State;
        this.parent = Parent;
        this.cost = Cost;
        this.depth = depth;
    }

    public Node(Grid State){ // Initial node constructor
        this(State, null,0,0);
    }

    public int getCost() {
        return cost;
    }

    public int getDepth() {
        return depth;
    }

    public String toString(){
        return "\n"+ this.State;
    }

    public Node getParent(){
        return parent;
    }


    public static Node findNodeWithState(ArrayList<Node> nodelist, Grid state){
        //finds the node if it is contained in a list.
        for(Node n: nodelist){
            if(state.sameBoard(n.State)) return n;
        }
        return null;
    }

    public Grid getState() {
        return State;
    }
}
