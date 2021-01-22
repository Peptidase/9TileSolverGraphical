package GUI;

import Constants.CONST;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    MainPanel MP;

    App(){ //Names the Title and the app screen with appropriate titles
        InitApp();
    }

    private void InitApp(){

        this.add(new MainPanel());
        this.setTitle("Tile Solver");
        this.setResizable(false);
        this.setSize(CONST.APP_WIDTH,CONST.APP_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
               var ex = new App();
                ex.setVisible(true);
            });
    }



}
