package TextReader;

import Models.Grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileParse {
    Scanner FileReader;
    File file;


    public FileParse(File file){
        this.file = file;

    }


    private int[] ParseDimensions() throws Exception{ //Will take the file, read the first line and parse integer values
        String line;
        String[] Numbers;
        String Regex = "[(),\\[\\]]";
        ArrayList<Integer> LineDetails = new ArrayList<>();
        try{
            FileReader = new Scanner(file);
            line = FileReader.nextLine();
            line = line.replaceAll(Regex, "");
            Numbers = line.split("[' ']");
            int[] GridInfo = new int[Numbers.length];

            for(int i = 0; i< Numbers.length; i++)
            {

                GridInfo[i] = Integer.parseInt(Numbers[i]);
            }

            if((GridInfo[0] * GridInfo[1]) != (GridInfo.length - 2)){
                throw new Exception("The Dimensions Specified are not suitable");

            }
            else if(duplicates(GridInfo)){
                throw new Exception("There are duplicate tiles in the grid file");
            }
            return GridInfo;
        }
         catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch(NullPointerException e2) {
            e2.printStackTrace();
        }catch(IndexOutOfBoundsException e3){
            throw new Exception("The Dimension file is not the correct format");
        }catch(NumberFormatException e4){
            throw new Exception("The Dimension file has non-numerical characters");
        }

        return null;
    }



    public int[][] getGrid() throws Exception { //Used to turn integer Values into grid readable for A* algorithm
        int[] Dimensions = ParseDimensions();

        int[][] GridArray = new int[Dimensions[0]][Dimensions[1]]; //Rows and columns
        int count = 2;
        for(int i = 0; i<Dimensions[0] ;i++){
            for(int i2 = 0; i2<Dimensions[1]; i2++){
                GridArray[i][i2] = Dimensions[count];
                count++;
            }
        }

        return GridArray;
    }





    boolean duplicates(final int[] Dimensions) //Used to check if there are duplicate integers within dimensions
    {
        Set<Integer> Checker = new HashSet<Integer>();
        int[] zipcodelist = Arrays.copyOfRange(Dimensions,2,Dimensions.length);
        for (int i : zipcodelist)
        {
            if (Checker.contains(i)) {
                return true;
            }
            else {
                Checker.add(i);
            }
        }
        return false;
    }


}
