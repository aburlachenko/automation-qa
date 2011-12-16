/*
TASK 6
====================
k defects are randomly distributed amongst n integrated-circuit chips produced by a factory 
(any number of defects may be found on a chip and each defect is independent of the other defects).

Let p(k,n) represent the probability that there is a chip with at least 3 defects.
For instance p(3,7)  0.0204081633.

----------------------------------------------------------------------------------------------------
Find p(20 000, 1 000 000) and give your answer rounded to 10 decimal places in the form 0.abcdefghij
*/

import java.math.BigDecimal;

public class Test {
	
	public static BigDecimal getProbability(int n, int k) {
		BigDecimal result = BigDecimal.ONE.divide(new BigDecimal(k).pow(n-1));
		return result;
	}
	
	public static void main(String argv[]) {
		BigDecimal result = getProbability(20000,1000000);
		System.out.println(result.toPlainString());
	}

}

// but it will run for ages...
// answer is 10e-11994 => 0.0000000000...
