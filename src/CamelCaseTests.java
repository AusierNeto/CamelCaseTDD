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
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(5);
		assertEquals(answer, camel.getNumericIndex("asdfj7"));
		answer.add(9);
		assertEquals(answer, camel.getNumericIndex("asdfg7jio7"));
	}
	
	@Test
	void splitStringOnUpperCaseTest() {
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("first");
		answer.add("second");
		assertEquals(answer, camel.splitStringOnUpperCase("firstSecond"));
	}
	
//	@Test
//	void splitOnUpperCaseTest() {
//		assertEquals("last", camel.splitOnUpperCase("testLast"));
//	}

}
