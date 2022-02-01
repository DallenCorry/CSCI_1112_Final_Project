package calendar;

/**
 * This class takes a file and generates a list of ingredients with their name, quantity and type (pound, cup, tsp etc.)
 * @author Dallen Corry
 * @version 1.0
 * @since 9/29/2021
 */
public class IngredientsList {
	protected String[] ingredientsQuantity;			//i.e 3, 2, .5 etc.
	protected String[] ingredientsMeasurmentType;	//i.e pound, cup, tsp, etc.
	private String[] ingredientsNames;				//i.e chicken, flour, salt, etc.
	//so 3 pounds of chicken is converted into 3 separate arrays.
	
	/**
	 * Default constructor sets ingrediensQuantity, ingredientsMeasurmentType, 
	 * and ingredientsNames to arrays of size 0.
	 */
	public IngredientsList(){
		ingredientsQuantity = new String[0];
		ingredientsMeasurmentType = new String[0];
		ingredientsNames = new String[0];
	}

	public IngredientsList(String[] ingredientsQuantity, String[] ingredientsMeasurmentType, String[] ingredientsNames) {
		this.ingredientsQuantity = ingredientsQuantity;
		this.ingredientsMeasurmentType = ingredientsMeasurmentType;
		this.ingredientsNames = ingredientsNames;
	}

	//getters and setters
	public String getIngredientsQuantity(int i) {
		return ingredientsQuantity[i];
	}

	public void setIngredientsQuantity(int i, String ingredientsQuantity) {
		this.ingredientsQuantity[i] = ingredientsQuantity;
	}

	public String getIngredientsMeasurmentType(int i) {
		return ingredientsMeasurmentType[i];
	}

	public void setIngredientsMeasurmentType(int i, String ingredientsMeasurmentType) {
		this.ingredientsMeasurmentType[i] = ingredientsMeasurmentType;
	}

	public String getIngredientsNames(int i) {
		return ingredientsNames[i];
	}
	
	public String[] getIngredientsNames() {
		return ingredientsNames;
	}
	
	public void setIngredientsNames(int i, String ingredientsNames) {
		this.ingredientsNames[i] = ingredientsNames;
	}
	
	@Override
	public String toString() {
		String string="";
		for(int i=0; i<ingredientsNames.length;i++){
			string += "\n\t"+ingredientsQuantity[i]+" "+ingredientsMeasurmentType[i]+" "+ingredientsNames[i];
		}
		return string;
	}
}
