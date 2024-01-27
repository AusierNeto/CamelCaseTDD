import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamelCaseTests {
	
	private CamelCaseProcessing camel;

	@BeforeEach
	public void initializePilha() {
		camel = new CamelCaseProcessing();
	}
	
	@Test
	void upperCaseCharTest() {
		assertFalse(camel.isUpperCaseChar('e'));
		assertTrue(camel.isUpperCaseChar('E'));
	}
	
	@Test
	void upperCaseStringTest() {
		assertFalse(camel.isUpperCaseString("element"));
		assertTrue(camel.isUpperCaseString("Element"));
	}
	
	@Test
	void upperCaseIndexTest() {
		assertEquals(9, camel.getUpperCaseIndex("uppercaseIndextest"));
		assertEquals(0, camel.getUpperCaseIndex("Uppercaseindextest"));
		assertEquals(17, camel.getUpperCaseIndex("uppercaseindextesT"));
		assertEquals(-1, camel.getUpperCaseIndex("asdfpoij"));
	}
	
	@Test
	void multipleUpperCaseIndexTest() {
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);
		answer.add(17);
		assertEquals(answer, camel.getUpperCaseIndexes("UppercaseindextesT"));
		answer.clear();
		assertEquals(answer, camel.getUpperCaseIndexes("aspdofija"));
		answer.add(0);
		answer.add(1);
		answer.add(2);
		assertEquals(answer, camel.getUpperCaseIndexes("ABCaspodfij"));
	}
	
	@Test
	void numericIndexTest() {
		ArrayList<NumericString> answer = new ArrayList<NumericString>();
		answer.add(new NumericString(5,1));
		assertEquals(answer.get(0).toString(), camel.getNumericIndexes("asdfj7").get(0).toString());
		answer.add(new NumericString(9,2));
		assertEquals(answer.toString(), camel.getNumericIndexes("asdfg7jio70").toString());
	}
	
	@Test
	void splitStringOnUpperCaseTest() {
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("first");
		answer.add("second");
		assertEquals(answer, camel.splitStringOnUpperCase("firstSecond"));
	}
	
	@Test
	void splitStringOnNumericTest() {
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("first");
		answer.add("9");
		assertEquals(answer, camel.splitStringOnNumeric("First9"));
	}
	
	@Test 
	void splitStringOnMultipleUpperCaseTest() {
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("first");
		answer.add("second");
		answer.add("third");
		assertEquals(answer, camel.splitStringOnUpperCase("firstSecondThird"));
		assertEquals(answer, camel.splitStringOnUpperCase("FirstSecondThird"));
	}
	
	@Test
	void getFirstWordUpperCaseAndNumber() {
		assertEquals("first", camel.getFirstWord("FirstTest"));
		assertEquals("first", camel.getFirstWord("First10Test"));
		assertEquals("first", camel.getFirstWord("firstTest"));
	}
	
	@Test
	void processDigitFirstPosition() {
		assertThrows(StartsWithNumberException.class, () -> camel.processString("10First"));
	}
	
	@Test
	void getSecondWordNumber() {
		assertEquals("10", camel.getSecondWordNumber("First10"));
		assertEquals("10", camel.getSecondWordNumber("Second10"));
	}
	
//	@Test
//	void splitStringMultipleDigitNumericTest() {
//		ArrayList<String> answer = new ArrayList<String>();
//		answer.add("first");
//		answer.add("10");
//		answer.add("last");
//		assertEquals(answer, camel.splitStringOnNumericUpperCase("First10Last"));
//	}
	
}
