import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class sorting_pure2 {

    public static void main(String ... av) throws IOException {

        int i;
        int[] data = new int[1000000];
        long time0 = System.currentTimeMillis();
        data = parseFile("d:/Jakarta/jakarta-jmeter-2.5/Projects/Sorting/list.txt");
        long time1 = System.currentTimeMillis();

        Arrays.sort(data);
        long time2 = System.currentTimeMillis();

        writeFile("d:/Jakarta/jakarta-jmeter-2.5/Projects/Sorting/list_sorted.txt", data);

        long time3 = System.currentTimeMillis();
        
        System.out.println("reading = " + (time1 - time0));
        System.out.println("sort = " + (time2 - time1));            
        System.out.println("writing = " + (time3 - time2));
    }

    private static int[] parseFile(String filename){
        int[] result = null;
        int number = 0;
        int position = 0;
        boolean isnegative = false;
        byte digit;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filename);
            // Obtain a channel         
            FileChannel channel = stream.getChannel();
            result = new int[(int)channel.size()];

            // Create a direct ByteBuffer; see also Creating a ByteBuffer
            MappedByteBuffer bb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()).load();
            //bb.flip();
            while(bb.hasRemaining()){
                digit = bb.get();
                switch(digit){
                    case 0x2D: isnegative = true; 
                        break;
                    case 0x31: number++;   
                        break;
                    case 0x32: number = number + 2;             
                        break;
                    case 0x33: number = number + 3;             
                        break;
                    case 0x34: number = number + 4;             
                        break;
                    case 0x35: number = number + 5;            
                        break; 
                    case 0x36: number = number + 6;            
                        break;  
                    case 0x37: number = number + 7;            
                        break;
                    case 0x38: number = number + 8;
                        break;
                    case 0x39: number = number + 9;            
                        break;
                    case 0x0A:{
                        if(isnegative)
                            result[position++] = -number / 10;
                        else
                            result[position++] = number / 10;
                        number = 0;
                        isnegative = false;
                    }
                    break; 
                    //case 0x0D: 
                }
                number  = number * 10;
            }                               
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                stream.close();
            } catch (IOException e) {    
                e.printStackTrace();
            }
        }
        int[] finres = new int[position];
        System.arraycopy(result, 0, finres, 0, position);
        return finres;
    }



    private static void writeFile(String filename, int[] array){
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
            BufferedOutputStream writer = new BufferedOutputStream(stream);
         
            byte[] bb = new byte[15];
            int size = 0;
            int prev = array[0];
            for(int i = 1; i<array.length; i++){
                if(prev != array[i] ){
                    size = writeBytes(bb,prev);
                    writer.write(bb, 0, size);              
                    prev = array[i];
                } 
            }
            writer.flush();
         
        } catch(IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {    
                e.printStackTrace();
            }   
        }
    }
 
    private static int writeBytes(byte[] bb, int p) {
        int position = 0;
        if(p<0){
            bb[position++] = (byte)0x2D;   
        }
        int digit = Math.abs(p) % 10;
        int number = Math.abs(p) / 10;
        while((number > 0) || (digit > 0)){
            bb[position++] = (byte)(digit + 0x30);
            digit = number % 10;
            number /= 10;
        }
        bb[position++] = (byte)0x0A;
        return position;
    }

}
