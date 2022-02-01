package calendar;

/**
 * This Class creates a Week object that contains an array of 7 Day objects and a ShoppingList object 
 * @author Dallen Corry
 *
 */
public class Week {
	Day[] days = new Day[7];
	ShoppingList shoppingList;
	
	/**
	 * Default constructor, creating a new Day Array using the default Day 
	 * Constructor, and setting shoppingList to null.
	 */
	public Week() {
		for (int i =0; i<days.length; i++)
			days[i]= new Day();
		shoppingList = null;
	}
	
	public Week(Day d1, Day d2, Day d3, Day d4, Day d5, Day d6, Day d7) {
		days[0]=d1;
		days[1]=d2;
		days[2]=d3;
		days[3]=d4;
		days[4]=d5;
		days[5]=d6;
		days[6]=d7;
		shoppingList = new ShoppingList(this);
	}
	
	public Week(Day[] d) {
		for (int i=0; i<days.length;i++)
			days[i] = d[i];
		shoppingList = new ShoppingList(this);
	}

	/**
	 * Gets the name of a meal for a specific day of the week
	 * @param i (Day) of the week to get meal from, indexed 0-6 inclusive
	 * @param m (int) Unused
	 * @return (String) the name of a meal for day i.
	 */
	public String mealName(int i, int m) {// m is for meal number, not yet implemented (ie, one day will have multiple meals)
		if(days[i]!=null&&days[i].getMeal()!=null)
			return days[i].getMeal().getName();
		return "No Meal";
	}
	/**
	 * gets the IngredientsList object for a specified day 
	 * @param i (int) the index of the day of the week you want to get the meal of.
	 * @return the ingredients list object of the meal of the selected day of the week.
	 */
	public IngredientsList getIngredients(int i) {
		return days[i].getMeal().getIngredients();
	}
	
	public Day getDay(int i) {
		return days[i];
	}

	public void setDay(int i, Day day) {
		days[i] = day;
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i=0; i<days.length; i++)
			s+= days[i].toString();
		return s;
	}
	
	public String toStringWithoutIngredients() {
		String s = "";
		for(int i=0; i<days.length; i++)
			s+= days[i].toStringWithoutIngredients();
		return s;
	}
}
