package GUI;

import Constants.CONST;
import Listener.ButtonListener;
import Models.Grid;
import Models.Node;
import Solver.AstarSolver;
import TextReader.GridReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPanel extends JPanel {


    private Timer timer; //Used for timing in the Draw Sequence
    private ButtonListener buttonListener; //Used to hand button clicks
    private JFileChooser fc; //Used to fetch files from directories
    private CardLayout CL; //USed to display different Screens of the program
    private GridReader GR; //Used to read the grid from the file
    private AstarSolver Solver; // Used to solve the problem
    private JPanel SolverCard,LoadingCard,MenuCard; //Used for Solver screen, Loading screen and Main Menu
    private Thread SolvingThread; // USed to get JVM to utilize another thread for solving the A* problem
    private int State = 0; //0 - Menu, 1 - loading, 2 - Solved screen, 3 solving animation
    private ArrayList<Node> Moveset;
    private JTextArea MoveSet;
    private SliderPainter SP;
    //Will utilize a card layout to hide and display fullscreen jpanels Easily,
    //I know from experience this is the best way to easily show multiple screens.


    MainPanel(){
        InitMainPanel();
    }

    private void InitMainPanel(){ //Initalizes all the Jpanels
        CL = new CardLayout();
        this.setLayout(CL);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(CONST.APP_WIDTH,CONST.APP_HEIGHT));

        GR = new GridReader();
        fc = GR.getFc();
        buttonListener = new ButtonListener(this, fc);


        InitSolverCard();
        InitLoadingCard();
        InitMenuCard();

        this.add(MenuCard);
        this.add(LoadingCard);
        this.add(SolverCard);

        timer = new Timer(CONST.DELAY,new SolveCycle());
        timer.start();
    }




    private void InitSolverCard(){

        MoveSet = new JTextArea();
        SP = new SliderPainter();
        JButton ForwardMove = new JButton("Next Move");
        JButton BackwardMove = new JButton("Last Move");
        JPanel ButtonCont = new JPanel();
        JPanel TitleCont = new JPanel();
        JLabel SolverTitle = new JLabel("Solver \n V.5");
        JScrollPane MovesScrollPane = new JScrollPane(MoveSet);

        ButtonCont.add(BackwardMove);
        ButtonCont.add(ForwardMove);

        ForwardMove.addActionListener(buttonListener);
        BackwardMove.addActionListener(buttonListener);

        SolverTitle.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
        TitleCont.setAlignmentX(CENTER_ALIGNMENT);
        TitleCont.setLayout(new BoxLayout(TitleCont,BoxLayout.Y_AXIS));
        TitleCont.add(Box.createRigidArea(new Dimension(60,100)));
        TitleCont.add(SolverTitle);

        SP.setVisible(false);
        GridLayout GLayout = new GridLayout(2,2);

        MoveSet.setEditable(false);
        SolverCard = new JPanel();
        SolverCard.setBorder(new EmptyBorder(20,20,20,20));
        SolverCard.add(SP);
        SolverCard.setLayout(GLayout);
        SolverCard.add(TitleCont);
        SolverCard.add(ButtonCont);
        SolverCard.add(MovesScrollPane);
        SolverCard.setVisible(true);
    }

    private void InitLoadingCard(){
        JProgressBar Pb = new JProgressBar();
        Pb.setIndeterminate(true);

        JLabel LoadingText = new JLabel("Solving");
        LoadingText.setAlignmentX(CENTER_ALIGNMENT);



        LoadingCard = new JPanel();
        LoadingCard.setBorder(new EmptyBorder(200,50,50,50));
        LoadingCard.setLayout(new BoxLayout(LoadingCard,BoxLayout.Y_AXIS));
        LoadingCard.add(LoadingText);
        LoadingCard.add(Box.createRigidArea(new Dimension(30,100)));
        LoadingCard.add(Pb);

        LoadingCard.setVisible(true);

    }

    private void InitMenuCard(){
        MenuCard = new JPanel();
        JLabel MenuTitle = new JLabel("Sliding Puzzle Solver");
        MenuTitle.setFont(new Font(Font.MONOSPACED, Font.PLAIN,40));
        JButton StartButton = new JButton("Solve");






        MenuTitle.setAlignmentX(CENTER_ALIGNMENT);
        StartButton.setAlignmentX(CENTER_ALIGNMENT);
        StartButton.addActionListener(buttonListener);

        MenuCard.setLayout(new BoxLayout(MenuCard,BoxLayout.Y_AXIS));
        MenuCard.setBorder(new EmptyBorder(200,0,0,0));
        MenuCard.add(MenuTitle);
        MenuCard.add(GR);
        MenuCard.add(StartButton);
        MenuCard.add(Box.createRigidArea(new Dimension(30,200)));

        MenuCard.setVisible(true);

    }
    // Loop functions to handle updating of x/y coordinates of tile drawings

    private void update()
    {
        if(State == 1){ //loading

            if(Solver.isCompleted())
            {
                this.setState(2);
                this.NextCard();
                Moveset = Solver.getMoveStack();
                WriteMoveSet(Moveset);
                SP.SetMoveSet(Moveset);


            }
            else if(Solver.isFailure()){
                this.setVisible(false);
                JOptionPane.showMessageDialog(this,"FAILURE, PUZZLE IS UNSOLVABLE!","PUZZLE ERROR!",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }else{

            }
        }


        repaint();
    }

    //Functions to handle Drawings

    public void WriteMoveSet(ArrayList<Node> Moves) //Writes the Moveset to the JtextArea, will be useful for animation
    {
        WriteMove("Moves:");

        for(int nodeI = Moves.size()-1; nodeI >= 0;nodeI--)
        {

            String move = "";
            int[][] CurrentState = Moves.get(nodeI).getState().GetGrid();
            if(Moves.get(nodeI).getParent() != null){

                int[][] ParentState = Moves.get(nodeI).getParent().getState().GetGrid();
                int [] Current0Loc = new int[2];
                int [] Parent0Loc = new int[2];

                for(int i1 = 0; i1 < CurrentState.length;i1++){
                    for(int i2 = 0; i2 < CurrentState[0].length;i2++){
                        if(CurrentState[i1][i2] == 0)
                        {
                            Current0Loc[0] = i1;
                            Current0Loc[1] = i2;
                        }
                        if(ParentState[i1][i2] == 0){
                            Parent0Loc[0] = i1;
                            Parent0Loc[1] = i2;
                        }
                    }
                }

                if(Parent0Loc[0] > Current0Loc[0]){
                    move = "Up";
                }
                else if(Parent0Loc[0] < Current0Loc[0]){
                    move = "Down";
                }
                else if(Parent0Loc[1] < Current0Loc[1]){
                    move ="Right";
                }
                else if(Parent0Loc[1] > Current0Loc[1]){
                    move = "Left";
                }
            }






            int moveNum = Moves.get(nodeI).getDepth();
            if(moveNum != 0){
                WriteMove("Move "+(moveNum)+" "+move);
            }


        }
    }




    //InnerLoop for animating Graphics with actionListener


    private class SolveCycle implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    }


    ///Getters and setters

    public void NextDrawGrid() throws Exception {
        SP.NextCurrentGrid();

    }

    public void LastDrawGrid() throws Exception {
        SP.LastCurrentGrid();

    }


    public void StartSolver(int[][] Grid){
        this.Solver = new AstarSolver(Grid);
        SolvingThread = new Thread(Solver);
        SolvingThread.start();
    }

    public void WriteMove(String Move){
        String text = MoveSet.getText();
        MoveSet.setText(Move +"\n"+ text);
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public void NextCard(){
        CL.next(this);
    }
}




