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
	
	public Object getUpperCaseIndexes(String stringElement) {
		ArrayList<Integer> indexesArray = new ArrayList<Integer>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isUpperCase(stringElement.charAt(i))) {
				indexesArray.add(i);
			}
		}
		return indexesArray;
	}

	public Object getNumericIndex(String stringElement) {
		ArrayList<Integer> indexesArray = new ArrayList<Integer>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isDigit((stringElement.charAt(i)))) {
				indexesArray.add(i);
			}
		}
		return indexesArray;
	}
	
	private int getAnchorIndex(ArrayList<Integer> upperCaseLettersArray) {
		if (upperCaseLettersArray.get(0) == 0) {
			upperCaseLettersArray.remove(0);
		}
		return upperCaseLettersArray.get(0);
	}

	public Object splitStringOnUpperCase(String stringElement) {
		ArrayList<String> wordsArray = new ArrayList<String>();
		ArrayList<Integer> upperCaseLettersArray = (ArrayList<Integer>) getUpperCaseIndexes(stringElement);
		int anchorIndex = getAnchorIndex(upperCaseLettersArray);
		
		return null;
		// TODO Refactor the code to run through the UpperCase Letters array
		
		//		for (int i=0; i<upperCaseLettersArray.size(); i++) {
//			wordsArray.add(stringElement.substring(anchorIndex, i).toLowerCase());
//			anchorIndex = i;
//		}
//		wordsArray.add(stringElement.substring(anchorIndex).toLowerCase());
//		return wordsArray;
	}

}
