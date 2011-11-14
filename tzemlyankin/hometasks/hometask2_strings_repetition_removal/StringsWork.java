//import java.util.*;
import java.io.*;

public class StringsWork {

    public static void main(String ... av) throws IOException {

        int countOfLines = 0, intValue;
        FileReader reader = new FileReader("d:/Git_repository/automated-qa/tzemlyankin/hometasks/hometask2_strings_repetition_removal/Strings_source.txt");
        String stringDoubleLetters, stringWithSpaces, stringDoubleLettersResult = "stub", stringWithSpacesResult;
        BufferedReader scnr = new BufferedReader(reader);        

        stringDoubleLetters = scnr.readLine();
        stringWithSpaces = scnr.readLine();
        
        scnr.close();
        reader.close();

        // Operations
        //stringDoubleLettersResult = stringDoubleLetters.replaceAll(regex, "");
        stringWithSpacesResult = stringWithSpaces.replaceAll("[\\s]+", " ");


        OutputStream os;
        os = new FileOutputStream("d:/Git_repository/automated-qa/tzemlyankin/hometasks/hometask2_strings_repetition_removal/Strings_result.txt");
        os.write(stringDoubleLettersResult.getBytes("Cp1251") );
        os.write("\n".getBytes("Cp1251") );
        os.write(stringWithSpacesResult.getBytes("Cp1251") );
        os.close();            

    }
}