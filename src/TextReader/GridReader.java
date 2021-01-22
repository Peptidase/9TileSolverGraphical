package TextReader;

import Constants.CONST;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class GridReader extends JPanel {


    JFileChooser fc;

    public GridReader() {

        //Create a file chooser
        fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Grid Files (.txt files)","txt","text");
        fc.setFileFilter(filter);
        fc.changeToParentDirectory();
        fc.setAcceptAllFileFilterUsed(false);
        this.setPreferredSize(new Dimension(CONST.APP_WIDTH,CONST.APP_HEIGHT-200));
    }






    //Getter and Setters


    public JFileChooser getFc() {
        return fc;
    }
}