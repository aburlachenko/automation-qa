import java.math.BigInteger;

public class Factorial {
	
	public static BigInteger getFactorial(BigInteger number) {
		return (number.compareTo(BigInteger.ZERO) == 1) ? number.multiply(getFactorial(number.subtract(BigInteger.ONE))) : BigInteger.ONE;
	}
	
	public static int getSumOfDigits(String input) {
		int result = 0;
		for (int i=0; i<input.length();i++) {
			result += Integer.parseInt(input.substring(i, i+1));
		}
		return result;
	}
	
	public static void main(String argv[]) {
		String factorial = getFactorial(new BigInteger("100")).toString();
		System.out.println(getSumOfDigits(factorial));
	}

}
