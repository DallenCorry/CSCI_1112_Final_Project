package calendar;

import java.util.*;
import java.io.*;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class TrialDriver extends Application{
	static String month;//String value of the month.
	static int firstDay;//1-7 first day of the month
	static int daysInMonth;//number of days in the month
	static boolean hasMeals;//to see if we had created meals yet. UNUSED
	
	public void start(Stage s) {
		//Make stuff
		
		ArrayList<DayPane> list = new ArrayList<DayPane>();
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, 3);
		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		ArrayList<String> weeks = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		String[] recipesList = {"Rec1","Rec2","Rec3","Rec4","Rec5","Rec6","Rec7","Rec8","Rec9"};//!!change if adding ability to add recipes!!
		month = months[calendar.get(Calendar.MONTH)];
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,	1);
		firstDay = calendar.get(Calendar.DAY_OF_WEEK);

		
		BorderPane pane = new BorderPane();
		GridPane gp = new GridPane();
		VBox side = new VBox();
		HBox top = new HBox();
		VBox vbShoppingList = new VBox();
		HBox DWM = new HBox();
		
		Button btIngredients = new Button("View Ingredients Page");
		Button btRandom = new Button("Generate Random Calendar");
		Button btAddRecipe = new Button("Add New Recipes");
		ListView<String> cbRecipes = new ListView<String>();
		Text title = new Text("View your Shopping List for the day, week or month");
		RadioButton rbDay = new RadioButton("Day");
		RadioButton rbWeek = new RadioButton("Week");
		RadioButton rbMonth = new RadioButton("Month");
		ComboBox<String> dateRange = new ComboBox<String>();
		TextArea shoppingListText = new TextArea();
		
		shoppingListText.setPrefWidth(300);
		shoppingListText.setPrefHeight(500);
		shoppingListText.setPromptText("No Dates Selected");
		shoppingListText.setWrapText(true);
		DWM.getChildren().addAll(rbDay,rbWeek,rbMonth);
		vbShoppingList.getChildren().addAll(title,DWM,dateRange,shoppingListText);
		Scene shoppingListScene = new Scene(vbShoppingList);
		Stage shoppingListStage = new Stage();
		shoppingListStage.setX(0);
		shoppingListStage.setY(50);
		shoppingListStage.setScene(shoppingListScene);
		shoppingListStage.setTitle("Shopping List");
		//Advanced make stuff
		
		ToggleGroup tgDWM = new ToggleGroup();
		rbDay.setToggleGroup(tgDWM);
		rbWeek.setToggleGroup(tgDWM);
		rbMonth.setToggleGroup(tgDWM);
		
		
		
		//generate the meal calendar
		generateCal(list);
		
		
		
		//add all days to the Grid Pane
		for(int i=0; i<6; i++) {
			for (int j=0; j<7; j++) {
				if(null!=list.get((i*7)+j) && ((i*7)+j)<list.size()){
					gp.add(list.get((i*7)+j), j, i);
				}
			}
		}
		
		
		//Do Stuff
		
		btRandom.setOnAction(e->{
//			gp.getChildren().removeAll(list);
			randomizeCal(list);
			//add all days to the grid pane
			for(int i=0; i<6; i++) {
				for (int j=0; j<7; j++) {
					if(null!=list.get((i*7)+j) && ((i*7)+j)<list.size()){
						gp.add(list.get((i*7)+j), j, i);
					}
				}
			}
		});
		
		//show shopping list
		btIngredients.setOnAction(e -> {
			shoppingListStage.show();
		});
		
		btAddRecipe.setOnAction(e -> {
			System.out.println("no functionality yet");
		});
		
		rbDay.setOnAction(e ->{
			days.clear();
			for(int i=0; i<daysInMonth; i++) {
				days.add(i+1+"");
			}
			dateRange.getItems().clear();
			dateRange.getItems().addAll(days);
		});
		
		rbWeek.setOnAction(e->{
			dateRange.getItems().clear();
			weeks.clear();
			calendar.set(Calendar.DATE, 1);
			String str = "";
			int lastDay;
			
			lastDay = 8-calendar.get(Calendar.DAY_OF_WEEK);
			str = "1 - "+lastDay;
			weeks.add(str);
			
			for (int i=0; i<calendar.getActualMaximum(Calendar.WEEK_OF_MONTH); i++) {
				if(lastDay+7<daysInMonth) {
					str = (lastDay+1)+" - ";
					lastDay+=7;
					str+=(lastDay);
					weeks.add(str);
				}else
					break;
			}
			str = (lastDay+1) +" - "+daysInMonth;
			weeks.add(str);
			dateRange.getItems().addAll(weeks);
		});
		
		rbMonth.setOnAction(e -> {
			dateRange.getItems().clear();
			dateRange.getItems().add("Current Month");
		});
		
		dateRange.setOnAction(e ->{
			//add support for the month

			if(hasMeals) {
				if(null == dateRange.getValue()) {
					shoppingListText.setText("No Dates Selected");
				}
				else {
					String str="";
					//list for each day
					if(rbDay.isSelected()){	
						str="Day: "+dateRange.getValue()+"\n";
						int index = Integer.parseInt(dateRange.getValue())+firstDay-2;
						str+= "\n"+new ShoppingList(list.get(index).getDay()).toString();
					}
					//list for each week
					else if(rbWeek.isSelected()){
						str="Week: "+dateRange.getValue()+"\n";
						String[] range = dateRange.getValue().split(" ");
						int start = Integer.parseInt(range[0]);
						Day[] arr = new Day[7];
						//first week of the month
						if(start==1) {
							Day[] arr2 = new Day[7-firstDay+start];
							for(int i=0; i<arr2.length; i++) {
								arr2[i]=list.get(i+firstDay-start).getDay();
							}
							str += new ShoppingList(arr2).toString();
						}
						//main body of the month
						else if(start+6<daysInMonth){
							for (int i=0; i<arr.length; i++) {
								arr[i] = list.get(i+firstDay+start-2).getDay();
							}
							str += new Week(arr).getShoppingList();
						}
						//last Week of the month
						else {
							Day[] arr3 = new Day[daysInMonth-start+1];
							for(int i=0; i<daysInMonth-start+1; i++) {
								arr3[i] = list.get(i+firstDay+start-2).getDay();
							}
							str += new ShoppingList(arr3).toString();
							
						}
					}
					//list for the month
					else if(rbMonth.isSelected()) {
						str="Month: "+month+"\n\n";
						Day[] monthArr = new Day[daysInMonth];
						
						for(int i=0; i<monthArr.length; i++) {
							monthArr[i] = list.get(i+firstDay-1).getDay();
						}
						str+= new ShoppingList(monthArr).toString();
					}
					shoppingListText.setText(str);
				}
			}
			else {
				shoppingListText.setText("No Meals Generated\nClick \"Generate Random Calendar\"");
			}
		});
		
		//Pretty stuff
		
		top.getChildren().addAll(btIngredients,btRandom,btAddRecipe);
		top.setAlignment(Pos.CENTER);
//		cbRecipes.getItems().addAll(recipesList);
//		cbRecipes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		side.getChildren().addAll(new Text("Select Recipes"), cbRecipes);
		
		top.setPadding(new Insets(5,5,5,5)); 
		top.setSpacing(5);
		gp.setStyle("-fx-border-color: black");
		gp.setGridLinesVisible(false);
		gp.setHgap(3);
		gp.setVgap(3);
		gp.setPrefHeight(610);//610
		gp.setPrefWidth(720);
		gp.setPadding(new Insets(5,5,5,5));
		side.setStyle("-fx-border-color: blue");
		side.setPrefWidth(100);
		side.setPrefHeight(500);
		vbShoppingList.setAlignment(Pos.CENTER);
		DWM.setAlignment(Pos.CENTER);
		DWM.setSpacing(5);
		shoppingListText.setEditable(false);
		
		
		pane.setTop(top);
		pane.setCenter(gp);
		pane.setRight(side);
		pane.setPadding(new Insets(5,5,5,5));
		gp.setStyle("-fx-background-color: black;");
		
		dateRange.setPromptText("Select Date Range");
		
		Scene scene = new Scene(pane);
		s.setScene(scene);
		s.setTitle(month +" Meal Calendar");
		s.setX(300);
		s.setY(0);
		s.show();
	}
	
	
	/**
	 * Generate a months worth of DayPanes based on the current calendar month
	 * @param list
	 * @return
	 */
//	public static ArrayList<DayPane> generateCal(ArrayList<DayPane> list){
	public static void generateCal(ArrayList<DayPane> list){
		Calendar cal = new GregorianCalendar();
		hasMeals = false;
		
		//add blank days at first of month
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH,3);
		for(int i=0; i<cal.get(Calendar.DAY_OF_WEEK)-1; i++) {
			list.add(null);
		}
		
		//add rest of the days of the month without meals
		for (int i=0; i<cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			list.add(new DayPane(i+1));
		}
		
		//add blank days at the end of the month
		//necessary for the creation of the grid pane in the Start method
		while(list.size()<42) {
			list.add(null);
		}
		
		for(int i=0; i<list.size(); i++) {
			if(null!=list.get(i)) {
				list.get(i).setPrefHeight(99);
				list.get(i).setPrefWidth(99);
			}
		}			
	}
	
	/**
	 * Randomizes the meals in the 
	 * @param list
	 */
	public static void randomizeCal(ArrayList<DayPane> list) {
		
		try {
			hasMeals = true;
			File f = new File("Ingredients.txt");
			int r,dateCheck=0,count = 0;
			Random random = new Random();
			Scanner input = new Scanner(f);
			//get number of Lines in file
			while(input.hasNextLine()) {
				count++;
				input.nextLine();
			}
			input.close();
			
			for(int i=0; i<list.size(); i++) {
				if(list.get(i)==null) {
					dateCheck++;
				}
				if(list.get(i)!=null) {
					r = random.nextInt(count);
					//make r not just random for the length of the whole thing, but of the size of the SelectedMeals list.
					//then it chooses from there, and gets the Index of that in the 
					list.set(i, new DayPane(r+1,f,i-dateCheck+1));
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Main method, solely for executing the launch method
	 * @param args arguments for launch
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
