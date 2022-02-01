package calendar;

/**
 * This class creates a meal with a name, recipe, and list of Ingredients
 * @author Dallen Corry
 * @version 1.0
 * @since 9/29/2021
 */
public class Meal {
	private String name;
	private String recipe;
	private IngredientsList ingredients;
	
	/**
	 * Default constructor creates a meal with name "No meal selected", recipe as
	 * "No recipe selected" and ingredients as a new Ingredients list with a default constructor.
	 */
	public Meal(){
		name="No meal selected";
		recipe = "No recipe selected";
		ingredients = new IngredientsList();
	}
	
	/**
	 * Creates 
	 * @param s The String that contains the entire recipe formated in the correct manner
	 * Meal Name&lt;(double)measurmentQuantity_(String)MeasrementType_(String)Measurment name, extra details_(int)Measurement2... etc.
	 * 
	 * Example:
	 * Peanut Butter Sandwich&lt;2_Tbsp_Peanut Butter, extra creamy_2_slices_bread, whole wheat_1.5_Tbsp_jelly
	 */
	public Meal(String s) {
		recipe="Recipe: null";
		String[] s1 = new String[2];
		
		s1=s.split("[<]");//split it into dish name and ingredients(2 element array)
		name = s1[0];//same way we would do the recipe
				
		String[] s3 = s1[1].split("[_]");//split into an array with quantity, type, and name
		
		//Split the array into 3 separate arrays
		int x=s3.length/3;
		String[] a = new String[x];
		String[] b = new String[x];
		String[] c = new String[x];
		//split each ingredient into quantity, type, name 
		for(int i=0; i<x; i++) {
			a[i] = s3[i*3].toLowerCase();
			b[i] = s3[(i*3)+1].toLowerCase();
			c[i] = s3[(i*3)+2].toLowerCase();
		}
		ingredients = new IngredientsList(a,b,c);
	}
	
	//getters and setters
	public String getRecipe() {
		return recipe;
	}
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	public IngredientsList getIngredients() {
		return ingredients;
	}
	public void setIngredients(IngredientsList ingredients) {
		this.ingredients = ingredients;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		if(ingredients!=null)
			return name+"\nIngredients: "+ingredients.toString();
		return name+"\n No Ingredients";
	}
	
	/**
	 * 
	 * @return the name of the meal
	 */
	public String toStringWithoutIngredients() {
		return name;
	}
}
