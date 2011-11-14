import java.util.HashMap;
import java.util.Iterator;
import java.lang.Character;
	
public class StringTask {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testStr = "adbsaaaddssbcskj";
		

		System.out.println("Final string:" + reduceDuplicates(testStr));
		
		String stringWithSpaces = "a b    c";
		
		
		System.out.println("Final string spaces:" + reduceSpaces(stringWithSpaces));
	}

	private static String reduceDuplicates(String inputString)
	{
		HashMap myMap = new HashMap();
		
		StringBuilder finalString = new StringBuilder();
		for(char c : inputString.toCharArray()) {
		   if (!myMap.containsKey(c))
		   {
			   finalString.append(c);
			   myMap.put(c, new Integer(1));
		   }
		}
		return finalString.toString();
	}
	
	private static String reduceSpaces(String inputString) 
	{	
		StringBuilder finalString = new StringBuilder();
		boolean wasSpace = false;
		for(char c : inputString.toCharArray())
		{
			if (c == ' ' & !wasSpace)
			{
				finalString.append(".");
				wasSpace = true;
			}
			else if (c != ' ')
				{
					finalString.append(c);
					wasSpace = false;
				}
		}
		return finalString.toString();
	}
	
}
