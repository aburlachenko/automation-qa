import java.util.*;
import java.io.*;

public class sumOfDigits {

    public static void main(String ... av) throws IOException {

        int[] number = new int[200];
        Arrays.fill(number, 0);
        number[0] = 1;

        int i, j, temp = 0, lastIndex = 0, finalNumber = 100;

        for(i=1; i <= finalNumber; i++){
            for(j=0; j <= lastIndex; j++){
                temp = number[j] * i + temp;
                number[j] = temp % 10;
                temp = temp / 10;
                
            }
            if (temp > 0){
                number[++lastIndex] = temp % 10;
                temp = temp /10;
                if (temp > 0) number[++lastIndex] = temp;
                temp = 0;
            }
        }
        for(j=lastIndex; j >= 0; j--){
           System.out.print(number[j]);
           temp = temp + number[j];
        }
        System.out.println();
        System.out.println("Sum of numbers = " + temp);
    }
}