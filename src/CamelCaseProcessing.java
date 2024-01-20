import java.util.ArrayList;

public class CamelCaseProcessing {

	public Boolean isUpperCaseChar(char charElement) {
		return Character.isUpperCase(charElement);
	}
	
	public Boolean isUpperCaseString(String stringElement) {
		char firstCharacter = stringElement.charAt(0);
		return isUpperCaseChar(firstCharacter);
	}

	public int getUpperCaseIndex(String stringElement) {
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isUpperCase(stringElement.charAt(i))) {
				return i;
			}
		}
		return -1; 
	}
	
	public ArrayList<Integer> getUpperCaseIndexes(String stringElement) {
		ArrayList<Integer> indexesArray = new ArrayList<Integer>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isUpperCase(stringElement.charAt(i))) {
				indexesArray.add(i);
			}
		}
		return indexesArray;
	}

	public ArrayList<Integer> getNumericIndexes(String stringElement) {
		ArrayList<Integer> indexesArray = new ArrayList<Integer>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isDigit((stringElement.charAt(i)))) {
				indexesArray.add(i);
			}
		}
		return indexesArray;
	}

	public ArrayList<String> splitStringOnUpperCase(String stringElement) {
		ArrayList<Integer> upperCaseLettersArray = getUpperCaseIndexes(stringElement);
		ArrayList<String> splittedStringsArray = new ArrayList<String>();
		
		splittedStringsArray.add(stringElement.substring(0, upperCaseLettersArray.get(0)).toLowerCase());
		splittedStringsArray.add(stringElement.substring(upperCaseLettersArray.get(0), stringElement.length()).toLowerCase());
		
		return splittedStringsArray;
	}

	public Object splitStringOnNumeric(String stringElement) {
		ArrayList<Integer> numericCharsArray = (ArrayList<Integer>) getNumericIndexes(stringElement);
		ArrayList<String> splittedStringsArray = new ArrayList<String>();
		
		splittedStringsArray.add(stringElement.substring(0, numericCharsArray.get(0)).toLowerCase());
		splittedStringsArray.add(stringElement.substring(numericCharsArray.get(0), stringElement.length()).toLowerCase());
		
		return splittedStringsArray;
	}

}
