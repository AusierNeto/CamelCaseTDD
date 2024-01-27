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

	public ArrayList<NumericString> getNumericIndexes(String stringElement) {
		ArrayList<NumericString> indexesArray = new ArrayList<NumericString>();
		for (int i=0; i<stringElement.length(); i++) {
			if (Character.isDigit((stringElement.charAt(i)))) {
				indexesArray.add(new NumericString(i, getNumberLength(stringElement, i)));
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
		ArrayList<NumericString> numericCharsArray = getNumericIndexes(stringElement);
		ArrayList<String> splittedStringsArray = new ArrayList<String>();
		
		splittedStringsArray.add(stringElement.substring(0, numericCharsArray.get(0).index()).toLowerCase());
		splittedStringsArray.add(stringElement.substring(numericCharsArray.get(0).index(), stringElement.length()).toLowerCase());
		
		return splittedStringsArray;
	}
	
	private static List<Object> sortList(List<Object> lista) {
        Collections.sort(lista, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 instanceof NumericString && o2 instanceof NumericString) {
                    return Integer.compare(((NumericString) o1).index(), ((NumericString) o2).index());
                } else if (o1 instanceof Integer && o2 instanceof Integer) {
                    return Integer.compare((Integer) o1, (Integer) o2);
                } else if (o1 instanceof NumericString && o2 instanceof Integer){
                	return Integer.compare(((NumericString) o1).index(), (Integer) o2);
                } else {
                	return Integer.compare(((NumericString) o2).index(), (Integer) o1);
                }
            }
        });
        return lista;  // TODO review this function
    }
	
	private List<Object> joinNumericAndUpperCaseLettersArray(ArrayList<NumericString> numericArray, ArrayList<Integer> upperCaseArray) {
		ArrayList<Object> unionArray = new ArrayList<Object>(numericArray);
		unionArray.addAll(upperCaseArray);
		List<Object> orderedList = unionArray;
		return orderedList;
	}
	
	private int getNextSplitElementIndex(int index, List<Object> indexesList) {
		if (index+1 < indexesList.size()) {
			if (indexesList.get(index + 1).getClass().getName() == "NumericString") {
				return ((NumericString) indexesList.get(index + 1)).index();
			} else {
				return (int) indexesList.get(index+1);
			}
		} else if (indexesList.get(index + 1).getClass().getName() == "NumericString") {
			return ((NumericString) indexesList.get(indexesList.size()-1)).index();
		} else {
			return (int) indexesList.get(indexesList.size()-1);
		}  // TODO Review this function
	}

	private int getSplitElementIndex(Object splitElement) {
		// splitElement can be Integer or NumericString;
		if (splitElement.getClass().getName() == "NumericString") {
			return ((NumericString)splitElement).index();
		} else {
			return (int)splitElement;
		} // Either way, the index will be returned;
		
	}
	
	public Object getFirstWord(String stringElement) {
		String stringToProcess = removeUpperCaseOnFirstLetter(stringElement);
		ArrayList<Integer> upperCaseIndexes = getUpperCaseIndexes(stringToProcess);
		ArrayList<NumericString> numericIndexes = getNumericIndexes(stringToProcess);
		Object firstSplitElement = joinNumericAndUpperCaseLettersArray(numericIndexes, upperCaseIndexes).get(0);
		int index = getSplitElementIndex(firstSplitElement);
		
		return stringToProcess.substring(0, index);
	}

	public void processString(String string) {
		isStringValid(string);
	}
	
	private String getNumericSubString(NumericString numericElement, String stringElement) {
		return stringElement.substring(numericElement.index(), numericElement.index() + numericElement.size());
	}

	public String getSecondWordNumber(String string) {
		String stringToProcess = removeUpperCaseOnFirstLetter(string);
		ArrayList<NumericString> upperCaseIndexes = getNumericIndexes(stringToProcess);
		NumericString firstElement = upperCaseIndexes.get(0);
		return getNumericSubString(firstElement, stringToProcess);
	}
}


