import java.util.*;
import java.io.*;

public class sorting_pure {

    public static void main(String ... av) throws IOException {

        int countOfLines = 0, intValue;
        long time0 = System.currentTimeMillis();
        TreeSet<Integer> data = new TreeSet<Integer>();
        FileReader reader = new FileReader("d:/Jakarta/jakarta-jmeter-2.5/Projects/Sorting/list.txt");
        String stringValue;
        BufferedReader scnr = new BufferedReader(reader);        

        while (scnr.ready()) {
            stringValue = scnr.readLine();
            intValue = Integer.parseInt(stringValue);
            data.add(intValue);
        }
        
        scnr.close();
        reader.close();
        long time1 = System.currentTimeMillis();
        //Arrays.sort(data.toArray());
        
        OutputStream os;
        os = new FileOutputStream("d:/Jakarta/jakarta-jmeter-2.5/Projects/Sorting/list_sorted.txt");
        int intToFile;
        while (!data.isEmpty()){
            intToFile = data.pollFirst();
            os.write(Integer.toString(intToFile).getBytes("Cp1251") );
            os.write("\n".getBytes("Cp1251") );
        }
        os.close();            

        long time2 = System.currentTimeMillis();
        System.out.println("reading"+(time1 - time0));
        System.out.println("writing/sort = "+(time2- time1));            
    }
}