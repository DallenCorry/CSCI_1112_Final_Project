package calendar;

import java.util.ArrayList;

/**
 * This class creates a Shopping list, which is a subclass of IngredientsList
 * @author Dallen Corry
 * @version 2.3
 * @since Jan/27/2022
 */
public class ShoppingList extends IngredientsList {
	ArrayList<Double> ingredientsQuantity = new ArrayList<Double>();
	ArrayList<String> ingredientsMeasurmentType = new ArrayList<String>();
	ArrayList<String> ingredientsNames = new ArrayList<String>();
	
	
	ShoppingList(Week w){
		for(int i=0; i<7;i++) {
			IngredientsList il = w.getIngredients(i);
			makeList(il);
		}		
	}
	
	ShoppingList(Day[] d){
		for(int i=0; i<d.length;i++) {
			IngredientsList il = d[i].getMeal().getIngredients();
			makeList(il);
		}
	}
	
	ShoppingList(Day d){
		if(null!=d.getMeal()) {	
			IngredientsList il = d.getMeal().getIngredients();
			makeList(il);
		}
	}
	
	/**
	 * Creates an IngredientsList object with the sum of all the ingredients for the specified time, 
	 * as given when creating the Shopping list.
	 * @param il The list of ingredients for the given day/week
	 */
	private void makeList(IngredientsList il) {
		for (int j=0; j<il.getIngredientsNames().length;j++){// there will need to be an extra for loop when you have multiple meals in one day.
			String[] S = il.getIngredientsNames(j).split(",");
			if(ingredientsNames.contains(S[0])) {
				int index = ingredientsNames.indexOf(S[0]);//index of the current ingredient in the shopping list
				double value = Double.parseDouble(il.getIngredientsQuantity(j));
				double value2 = ingredientsQuantity.get(index);
				ingredientsQuantity.set(index,value+value2);
			}
			if(!ingredientsNames.contains(S[0])) {
				ingredientsQuantity.add(Double.parseDouble(il.getIngredientsQuantity(j)));
				ingredientsMeasurmentType.add(il.getIngredientsMeasurmentType(j));
				ingredientsNames.add(S[0]);
			}
		}
	}
	
	@Override
	public String toString() {
		String s="";
		for(int i=0; i<ingredientsNames.size(); i++) {
			s+=String.format("%-7.2f%-10s| %s\n",ingredientsQuantity.get(i),ingredientsMeasurmentType.get(i),ingredientsNames.get(i));
		}
		return s;
	}
}
