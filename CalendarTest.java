package calendar;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalendarTest {

	@Test
	void testGetDate() {
		Day d = new Day();
		Day d2 = new Day(1,new File("Ingredients.txt"),1);
		assertTrue(d.getDate()==0);
		assertTrue(d2.getDate()==1);
	}
	
	@Test
	void test(){
		
	}

}
