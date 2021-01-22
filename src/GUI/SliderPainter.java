package GUI;

import Constants.CONST;
import Models.Grid;
import Models.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;

public class SliderPainter extends JComponent {

    private int rows,columns;
    private ArrayList<Grid> GridSet = new ArrayList<>();
    private int[][] CurrentGrid;
    int CurrentState;

    SliderPainter(){
    }

    public void SetMoveSet(ArrayList<Node> Moveset){ //Converts Queue to an ArrayList of grids
        for(Node n: Moveset){
            GridSet.add(n.getState());
        }
        InitPainter();
    }

    private void InitPainter(){ //Sets the column and
        Grid g = GridSet.get(0);
        int[][] DrawGrid = g.GetGrid();
        this.rows = DrawGrid.length;
        this.columns = DrawGrid[0].length;
        CurrentState = GridSet.size() -1;
        setCurrentGrid(CurrentState);
        this.setVisible(true);
    }






    @Override
    public void paintComponent(Graphics g){

        if(isVisible()){

        int boxWidth = CONST.GRID_WIDTH / columns;
        int boxHeight = CONST.GRID_HEIGHT / rows;
        int Startx = 20;
        int Starty = 20;
        g.setColor(Color.RED);
        for(int i1 = 0; i1 < rows; i1++){
            Startx = 0;
            for(int i2 = 0; i2 < columns; i2++){
                if(CurrentGrid[i1][i2] == 0){
                    g.setColor(Color.BLUE);
                }
                else{
                    g.setColor(Color.RED);
                }

                g.fillRect(Startx,Starty,boxWidth,boxHeight);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,20));
                g.drawString(String.valueOf(CurrentGrid[i1][i2]),Startx+(boxWidth/2),Starty+(boxHeight/2));
                Startx+=boxWidth;
            }
            Starty+=boxHeight;
        }


    }
    }


    //Getters setters

    public void setCurrentGrid(int g){
        this.CurrentGrid = GridSet.get(g).GetGrid();
    }

    public void NextCurrentGrid() throws Exception {
        if(CurrentState != 0){
            this.CurrentState = this.CurrentState - 1;
            setCurrentGrid(CurrentState);
        }else{
            throw new Exception("No more moves left");
        }


    }

    public void LastCurrentGrid() throws Exception {
        if(CurrentState != (GridSet.size()-1)){
            this.CurrentState = this.CurrentState + 1;
            setCurrentGrid(CurrentState);
        }else{
            throw new Exception("No more moves left");
        }


    }

}
