package Listener;

import GUI.MainPanel;
import Models.Grid;
import TextReader.FileParse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

public class ButtonListener implements ActionListener {

    private MainPanel MP;
    private JFileChooser fc;


    public ButtonListener(MainPanel MP, JFileChooser fc)
    {
        this.MP = MP;
        this.fc = fc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MP.getState() == 0){
            JButton b = (JButton)e.getSource();
            if(b.getText().equals("Solve")){
                int values = fc.showOpenDialog(MP);
                File file = fc.getSelectedFile();
                if(values == JFileChooser.APPROVE_OPTION){
                    if(file.exists()){
                        FileParse FP = new FileParse(fc.getSelectedFile());
                        try{
                            int[][] grid = FP.getGrid();
                            MP.NextCard();
                            MP.setState(1);
                            MP.StartSolver(grid);

                        }catch(Exception e3){
                            JOptionPane.showMessageDialog(MP,e3.getMessage(),"Dimensions Error",JOptionPane.ERROR_MESSAGE);
                        }


                }
                }
                else{
                    JOptionPane.showMessageDialog(MP,"Please select a txt file in the correct format");
                }

            }


        }else if(MP.getState() == 2){
            JButton b = (JButton)e.getSource();
            if(b.getText().equals("Next Move")){
                try{
                    MP.NextDrawGrid();

                }catch(Exception e3){
                    JOptionPane.showMessageDialog(MP,e3.getMessage());
                }
            }
            else if(b.getText().equals("Last Move")){
                try{
                    MP.LastDrawGrid();

                }catch(Exception e3){
                    JOptionPane.showMessageDialog(MP,e3.getMessage());
                }
            }

        }
    }


    //Getters and setters


    public void setFc(JFileChooser fc) {
        this.fc = fc;
    }
}
