import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CamelCaseProcessing {

	public Boolean isUpperCaseChar(char charElement) {
		return Character.isUpperCase(charElement);
	}
	
	public Boolean isUpperCaseString(String stringElement) {
		char firstCharacter = stringElement.charAt(0);
		return isUpperCaseChar(firstCharacter);
	}
	
	private void isStringValid(String string) {
		if (Character.isDigit(string.charAt(0))) {
			throw new StartsWithNumberException("String starts with a number");
		}
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
	
	public String removeUpperCaseOnFirstLetter(String stringElement) {
		if (isUpperCaseString(stringElement)) {
			String processedString = Character.toLowerCase(stringElement.charAt(0)) + stringElement.substring(1);
			return processedString;
		}
		return stringElement;
	}
	
	private boolean checkNumericStringEnded(int index, String stringElement) {
		if (index+1 < stringElement.length()) {
			if (!Character.isDigit(stringElement.charAt(index+1))) {
				return true;
			}
		}
		return false;
	}
	
	public int getNumberLength(String stringElement, int startIndex) {
		int numberLength = 0;
		for (int i=startIndex; i<stringElement.length(); i++) {
			if (Character.isDigit(stringElement.charAt(i))) {
				numberLength++;
			}
			if (checkNumericStringEnded(i, stringElement)) {
				break;
			}
		}
		return numberLength;
	}

	public ArrayList<SplitElement> getNumericIndexes(String stringElement) {
		ArrayList<SplitElement> indexesArray = new ArrayList<SplitElement>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isDigit((stringElement.charAt(i)))) {
				indexesArray.add(new SplitElement(i, getNumberLength(stringElement, i)));
				i += indexesArray.get(indexesArray.size()-1).size();
			}
		}
		return indexesArray;
	}

	public ArrayList<String> splitStringOnUpperCase(String stringElement) {
		String stringToProcess = removeUpperCaseOnFirstLetter(stringElement);
		ArrayList<Integer> upperCaseLettersArray = getUpperCaseIndexes(stringToProcess);
		ArrayList<String> splittedStringsArray = new ArrayList<String>();
		int wordBeginningIndex = 0;
		for (int i=0; i<upperCaseLettersArray.size(); i++) {
			splittedStringsArray.add(stringToProcess.substring(wordBeginningIndex, upperCaseLettersArray.get(i)).toLowerCase());
			wordBeginningIndex = upperCaseLettersArray.get(i);
		}
		splittedStringsArray.add(stringToProcess.substring(wordBeginningIndex, stringToProcess.length()).toLowerCase());
		return splittedStringsArray;
	}

	public ArrayList<String> splitStringOnNumeric(String stringElement) {
		ArrayList<SplitElement> numericCharsArray = getNumericIndexes(stringElement);
		ArrayList<String> splittedStringsArray = new ArrayList<String>();
		
		splittedStringsArray.add(stringElement.substring(0, numericCharsArray.get(0).index()).toLowerCase());
		splittedStringsArray.add(stringElement.substring(numericCharsArray.get(0).index(), stringElement.length()).toLowerCase());
		
		return splittedStringsArray;
	}
	
	private List<Object> joinNumericAndUpperCaseLettersArray(ArrayList<SplitElement> numericArray, ArrayList<Integer> upperCaseArray) {
		ArrayList<Object> unionArray = new ArrayList<Object>(numericArray);
		unionArray.addAll(upperCaseArray);
		List<Object> orderedList = unionArray;
		return orderedList;
	}
	
	private int getNextSplitElementIndex(int index, List<Object> indexesList, String stringElement) {
		try {
			if (indexesList.get(index + 1).getClass().getName() == "NumericString") {
				return ((SplitElement) indexesList.get(index + 1)).index();
			} else {
				return (int) indexesList.get(index+1);
			}
		} catch (Exception e) {
			return stringElement.length();
		}
	}

	private int getSplitElementIndex(Object splitElement) {
		// splitElement can be Integer or NumericString;
		if (splitElement.getClass().getName() == "SplitElement") {
			return ((SplitElement)splitElement).index();
		}
		return (int)splitElement;
		// Either way, the index will be returned;
	}
	
	public String getFirstWord(String stringElement) {
		String stringToProcess = removeUpperCaseOnFirstLetter(stringElement);
		ArrayList<Integer> upperCaseIndexes = getUpperCaseIndexes(stringToProcess);
		ArrayList<SplitElement> numericIndexes = getNumericIndexes(stringToProcess);
		Object firstSplitElement = joinNumericAndUpperCaseLettersArray(numericIndexes, upperCaseIndexes).get(0);
		int index = getSplitElementIndex(firstSplitElement);
		
		return stringToProcess.substring(0, index);
	}
	
	private String getNumericSubString(SplitElement numericElement, String stringElement) {
		return stringElement.substring(numericElement.index(), numericElement.index() + numericElement.size());
	}

	public String getSecondWordNumber(String string) {
		String stringToProcess = removeUpperCaseOnFirstLetter(string);
		ArrayList<SplitElement> upperCaseIndexes = getNumericIndexes(stringToProcess);
		SplitElement firstElement = upperCaseIndexes.get(0);
		return getNumericSubString(firstElement, stringToProcess);
	}
	
	private ArrayList<String> processString(String stringElement, ArrayList<Object> splitElementsArrayList) {
		ArrayList<String> processedStrings = new ArrayList<String>();
		processedStrings.add(getFirstWord(stringElement));
		for (int i=0; i<splitElementsArrayList.size(); i++) {
			if (splitElementsArrayList.get(i).getClass().getName() == "SplitElement") {
				processedStrings.add(getNumericSubString((SplitElement)splitElementsArrayList.get(i), stringElement));
			} else {
				int sliceEndIndex = getNextSplitElementIndex(i, splitElementsArrayList, stringElement);
				processedStrings.add(stringElement.substring((int)splitElementsArrayList.get(i), sliceEndIndex).toLowerCase());
			}
		}
		return processedStrings;
	}
	
	public ArrayList<String> processCamelCase(String stringElement) {
		isStringValid(stringElement);
		ArrayList<SplitElement> numericIndexesArrayList = getNumericIndexes(stringElement);
		ArrayList<Integer> upperCaseIndexesArrayList = getUpperCaseIndexes(removeUpperCaseOnFirstLetter(stringElement));
		ArrayList<Object> splitElementsArrayList = (ArrayList<Object>)joinNumericAndUpperCaseLettersArray(numericIndexesArrayList, upperCaseIndexesArrayList);
		return processString(stringElement, splitElementsArrayList);
	}
	
	
}


