package Solver;

import Models.Grid;
import Models.Node;

import java.util.ArrayList;

public class AstarSolver extends UniformSolver implements Runnable {

    private boolean Failure;

    public AstarSolver(int[][] initialBoard){
        super(initialBoard);
        this.Failure = false;
    }


    private void solve() throws Exception {
        int lastdepth = 0;
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
            if(lastdepth < next.getDepth()){
                if(lastdepth == 100){ //TODO is this number the same for 4x4?
                    Failure =true;
                }
                lastdepth = next.getDepth();
                this.SetMove(next.getDepth());

            }
            if(next.State.isGoal()){
                reportSolution(next);

                return;
            }
            expanded.add(next);
            unexpanded.remove(next);
            moves++;

            ArrayList<Grid> moveList = next.State.expand();

            for(Grid g:moveList){
                if(Node.findNodeWithState(unexpanded,g) == null && (Node.findNodeWithState(expanded,g)) ==null){
                    int newCost = g.heuristic()+ next.getCost() + 1;
                    Node NewNode = new Node(g,next,newCost,next.getDepth()+1);
                    unexpanded.add(NewNode);
                }
            }



        }
        System.out.println("No solution found");
    }


    @Override
    public void reportSolution(Node n) {
        printSolution(n);
    }



    @Override
    public void run(){
        try {
            solve();
        } catch (Exception ignore) {}
    }


    public boolean isFailure() {
        return Failure;
    }
}
