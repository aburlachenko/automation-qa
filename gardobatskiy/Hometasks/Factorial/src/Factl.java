
import java.math.BigInteger;
import java.util.Scanner;

public class Factl {

public static void main( String args[] )
	   {

            BigInteger n = BigInteger.ONE;

            Scanner in = new Scanner(System.in);
            int z;

            System.out.print( "Enter an integer: ");
            z = in.nextInt();

            for (int i=1; i<=z; i++) {
            n = n.multiply(BigInteger.valueOf(i));

            }

            System.out.println(z + "! = " + n );
        }

}
