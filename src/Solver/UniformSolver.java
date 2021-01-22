package Solver;

import Models.Grid;
import Models.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class UniformSolver implements Runnable{

    ArrayList<Node> MoveQueue = new ArrayList<>();
    ArrayList<Node> unexpanded = new ArrayList<>(); //The arraylist that stores all the nodes that are unexpanded
    ArrayList<Node> expanded = new ArrayList<>(); //The arraylist that stores all the nodes that are expanded
    Node rootNode; //The starting root node
    boolean Complete;
    int Move;
    //Sets up the initial Node with the intended final goal board specified.
    UniformSolver(int[][] initialBoard){

        Complete = false;
        Grid InitialState = new Grid(initialBoard);
        rootNode = new Node(InitialState);
    }



    private void solve() throws Exception { //Throws an exception as the expand method throws an exception
        //for trying to move all directions and returning the resulting list.
        unexpanded.add(rootNode);
        int moves = 0;
        //Initial moves and rootnode.
        while(unexpanded.size() > 0){
            int LowestCost = Integer.MAX_VALUE; //Max value to compare costs to
            int nextIndex = 0;// THe index of the next node to be expanded
            Node next;//the next node to be expanded
            for (Node n:unexpanded) //find the lowest costing node.
            {
                if (n.getCost() < LowestCost){
                    nextIndex = unexpanded.indexOf(n); // Get the index of the lowest node
                    LowestCost = n.getCost();
                }
            }
            next = unexpanded.get(nextIndex);
            if(next.State.isGoal()){
                System.out.println("Goal Found");
                reportSolution(next);

                return;
            }
            expanded.add(next);
            unexpanded.remove(next);
            moves++;
            ArrayList<Grid> moveList = next.State.expand();

            for(Grid g:moveList){
                if(Node.findNodeWithState(unexpanded,g) == null && (Node.findNodeWithState(expanded,g)) ==null){
                    int newCost = next.getCost() + 1;
                    Node NewNode = new Node(g,next,newCost,next.getDepth()+1);
                    unexpanded.add(NewNode);
                }
            }



        }
        System.out.println("No solution found");


    }

    public void printSolution(Node n) {
        MoveQueue.add(n);
        if (n.parent != null) {
            printSolution(n.parent);
        }else{
            System.out.println("Stack Complete");
            Complete = true;
        }
    }

    public void reportSolution(Node n) {
        printSolution(n);
    }


    @Override
    public void run(){
        try {
            solve();
        } catch (Exception ignore) {}
    }



    ///Getters and Setters

    public ArrayList<Node> getMoveStack() {
        return MoveQueue;
    }

    public boolean isCompleted(){
        return Complete;
    }

    public void SetMove(int depth){
        this.Move = depth;
    }

    public int getMove(){
        return this.Move;
    }
}
