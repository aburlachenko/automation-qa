import java.math.BigInteger;


public class Factorial {
	
	static int n = 100;
	
	public static void main(String[] args) {
		System.out.println("n = " + n);
		System.out.println(n+"! = "+factorial(n));
		System.out.println("Split sum = " + split_sum(factorial(n)));
	}
	
	static BigInteger factorial (int n){
		BigInteger factorial = BigInteger.valueOf(1);
		for (int i=1;i<n+1;i++)
		{
			factorial = factorial.multiply(BigInteger.valueOf(i));
		}
	return factorial;
	}
	
	static int split_sum (BigInteger factorial){
		int split_sum = 0;
		String stringFactorial = factorial.toString(); 
		for (int i=0;i<stringFactorial.length();i++)
		{
			split_sum = split_sum + Integer.parseInt(stringFactorial.substring(i, i+1));
		}
	return split_sum;
	}
}
