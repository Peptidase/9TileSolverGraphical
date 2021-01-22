package Models;

import java.util.ArrayList;

public class Grid {

    //This class is used to define the AssignmentFiles.Grid as an object, It contains functions
    //To calculate the heuristics of the current Array, as well as move squares between each position

        //Custom Goal Grids:done
        //Custom GridArray:done
        //Location function needs to find in any size:done
        //Move function needs to move in any size:done
        //Grid Constructor needs to make from any size:done
        // isGoal Needs to function from any size :done
        // SameBoard needs to function from any size :done
        //Expand from any size :done

    int[][] GridArray;

    //accessable Goal and Initial Root nodes set as static.
    public int[][] goal;

    int rows;
    int columns;



    //Constructor that takes a 2d array and generates a grid from it.
    //It specifies the values iteratively. Useful for copying values.
    public  Grid(int[][] GA){

        this.rows = GA.length;
        this.columns = GA[0].length;
        GridArray = new int[rows][columns];
        SetGoal();
        for(int i = 0; i<rows ;i++){
            for(int i2 = 0; i2<columns; i2++){
                GridArray[i][i2] = GA[i][i2];
            }
        }
    }


    private void SetGoal(){
        this.goal = new int[rows][columns];
        int count = 0;
        for(int i = 0; i<rows ;i++){
            for(int i2 = 0; i2<columns; i2++){
                this.goal[i][i2] = count;
                count++;
            }
        }
    }





    public boolean isGoal(){
        for (int i = 0; i < rows; i++) {
            for(int i2 = 0; i2 < columns; i2++){
                if(this.GridArray[i][i2] != goal[i][i2]){
                    return false;
                }
            }
        }
        return true;
    }


    public boolean sameBoard(Grid g){
        for (int i = 0; i < rows; i++) {
            for(int i2 = 0; i2 < columns; i2++){
                if(this.GridArray[i][i2] != g.GridArray[i][i2]){
                    return false;
                }
            }
        }
        return true;
    }




    //Below is a private function that will return an array with the values of where the passed item is
    //Made to allow Move functions to quickly manage illegal movements and check for if moves are possible.
    //Also made incase in the future I want to know where another tile is for heuristic sake?
    private int[] location(int value){
        int[] loc = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int i2 = 0; i2<columns;i2++) {
                if(GridArray[i][i2] == (value)){
                    loc[0] = i;
                    loc[1] = i2;
                }


            }
        }
        return loc; //Return is y,x
    }

    public ArrayList<Grid> expand() throws Exception {
        ArrayList<Grid> ExpansionNodes = new ArrayList<>(); //Will store the plausible expanded nodes
        Grid EU = new Grid(this.GridArray);
        Grid ED = new Grid(this.GridArray);
        Grid EL = new Grid(this.GridArray);
        Grid ER = new Grid(this.GridArray);
        //Essentially will just try to move all of the nodes a specific way, if an exception occurs we ignore it and they
        //Arent added to the ExpansionNodes arraylist

        try{
            EU.Moveup();
            ExpansionNodes.add(EU);
        }catch (Exception ignore){}
        try {
            ED.Movedown();
            ExpansionNodes.add(ED);
        }catch (Exception ignore){}
        try{
            EL.Moveleft();
            ExpansionNodes.add(EL);
        }catch (Exception ignore){}
        try {
            ER.Moveright();
            ExpansionNodes.add(ER);
        }catch (Exception ignore){}


        return ExpansionNodes;
    }



    //Below are movement functions to change position of the AssignmentFiles.Grid, each with their Exceptions defined.
    //They move the tile respective to the space (0) e.g.
    // moveup will move the tile below the 0 upwards.
    // moveleft will move the tile to the right of the 0 left

    public void Moveup() throws Exception {
        int[] loc = location(0);
        if(loc[0] != (rows - 1))
        {
            GridArray[loc[0]][loc[1]] = GridArray[loc[0]+1][loc[1]];
            GridArray[loc[0]+1][loc[1]] = 0;

        }
        else{

            throw new Exception("Illegal Move!"); //Throws an exception so I know what is wrong with program
        }
    }

    public void Movedown() throws Exception {
        int[] loc = location(0);
        if(loc[0] != 0)
        {
            GridArray[loc[0]][loc[1]] = GridArray[loc[0]-1][loc[1]];
            GridArray[loc[0]-1][loc[1]] = 0;

        }
        else{
            throw new Exception("Illegal Move!");//Throws an exception so I know what is wrong with program
        }
    }
    public void Moveright() throws Exception {
        int[] loc = location(0);
        if(loc[1] != 0)
        {
            GridArray[loc[0]][loc[1]] = GridArray[loc[0]][loc[1]-1];
            GridArray[loc[0]][loc[1]-1] = 0;

        }
        else{
            throw new Exception("Illegal Move!");//Throws an exception so I know what is wrong with program
        }
    }
    public void Moveleft() throws Exception {
        int[] loc = location(0);
        if(loc[1] != (columns+1))
        {
            GridArray[loc[0]][loc[1]] = GridArray[loc[0]][loc[1]+1];
            GridArray[loc[0]][loc[1]+1] = 0;

        }
        else{
            throw new Exception("Illegal Move!");//Throws an exception so I know what is wrong with program
        }
    }

    public int heuristic(){
        int h = 0;
        int CurrentDigit = 0;
        int[] Currentloc;

        for (int i = 0; i < rows; i++) {
            for (int i2 = 0; i2<columns;i2++){
                Currentloc = location(CurrentDigit);
                h+=Math.abs(Currentloc[0] - i);
                h+=Math.abs(Currentloc[1] - i2);
                CurrentDigit++;
            }
        }
        return h;
    }





    //getters setters

    public int[][] GetGrid(){
        return GridArray;
    }


}
