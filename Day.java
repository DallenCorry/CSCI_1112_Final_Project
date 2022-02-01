package calendar;

import java.util.*;
import java.io.*;

/**
 * Creates a Day that contains a Meal and integer representation of the date
 * @author Dallen Corry
 * @version 2.1
 * @since Jan/27/2022
 */
public class Day {
	/**
	 * Data fields for the meal object and int representing a date.
	 */
	private Meal meal;
	private int date;
	
	/**
	 * No arg Constructor makes meal null and date 0
	 */
	public Day() {
		meal=null;
		date=0;
	}
	
	//create a new day with a specific 
	/**
	 * Creates new Day with a specified line of a specified file, along with the date as an integer
	 * @param line (int) line of the file the meal is found on
	 * @param f the file with the recipes, formatted in the specific way found in Meal.java
	 * @param date The integer value of the date of this day
	 */
	public Day(int line, File f, int date){
		String s;
		try {	
			Scanner scanner = new Scanner(f);
			for(int i=0; i<line-1; i++)
				scanner.nextLine(); 
			s= scanner.nextLine();
			meal = new Meal(s);
			this.date = date;
			
			scanner.close();
		}catch(Exception e) {
			System.out.println("error in Day: "+e);
		}
	}
	
	/**
	 * Getters and Setters
	 * @return the meal of this day
	 */
	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		if(meal!=null)
			return date+"\n  "+meal.toString()+"\n";
		else
			return date+"\n  No Meal\n";
	}
	
	/**
	 * Creates a string of the day with its date and meal, without the attached ingredients
	 * @return date and meal.toStringWithoutIngredients()
	 */
	public String toStringWithoutIngredients() {
		if(meal!=null)
			return date+"\n  "+meal.toStringWithoutIngredients();
		else
			return date+"\n  No Meal";
	}
}
