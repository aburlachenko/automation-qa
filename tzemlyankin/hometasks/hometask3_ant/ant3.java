import java.util.*;
import java.io.*;

public class ant3 {

    public static void main(String ... av) throws IOException {

        class point {
            long pointX;
            long pointY;

        }

        point currentPoint = new point();

        long i, blackCellsNumber, steps = 1000000l, iterationFor104steps = 9615384615375000l, numberOfBlackCellsIn104 = 12l;
        int currentOrientation = 12; // 12 - up, 3 - right, 6 - down, 9 - left
        currentPoint.pointX = 0l;
        currentPoint.pointY = 0l; 

        HashMap gridOfBlackCells = new HashMap();

        for (i = 1; i <= steps; i++){
            // emulation of ant's steps
            String currentKey = Long.toString(currentPoint.pointX) + "_" + Long.toString(currentPoint.pointY);
            if (gridOfBlackCells.containsKey(currentKey)){
                gridOfBlackCells.remove(currentKey);
                currentOrientation = currentOrientation - 3;
                if (currentOrientation == 0) currentOrientation = 12;
            } else {
                gridOfBlackCells.put(currentKey, 1);
                currentOrientation = currentOrientation + 3;
                if (currentOrientation == 15) currentOrientation = 3;
            }
            switch (currentOrientation) {
                case 3:  
                    currentPoint.pointX = currentPoint.pointX + 1;
                    break;
                case 6:  
                    currentPoint.pointY = currentPoint.pointY + 1;
                    break;
                case 9:  
                    currentPoint.pointX = currentPoint.pointX - 1;
                    break;
                case 12: 
                    currentPoint.pointY = currentPoint.pointY - 1;
                    break;
                default: System.out.println("How did you get here?!"); break;
            }
            //System.out.println("on the " + i + " step: X = " + currentPoint.pointX + ", Y = " + currentPoint.pointY);
        }
        // black cells calculation
        blackCellsNumber = gridOfBlackCells.size();

        //System.out.println("Number of black cells after " + steps + " steps = " + blackCellsNumber + " cells");
        blackCellsNumber = blackCellsNumber + iterationFor104steps * numberOfBlackCellsIn104;
        System.out.println("Number of black cells after 10^18 steps = " + blackCellsNumber + " cells");
    }
}