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
	String month;
	int firstDay;
	int daysInMonth;
	boolean hasMeals;//to see if we had created meals yet.
	
	public void start(Stage s) {
		//Make stuff
		
		ArrayList<DayPane> list = new ArrayList<DayPane>();
		Calendar calendar = new GregorianCalendar();
//		calendar.set(Calendar.MONTH, 2);
		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		ArrayList<String> weeks = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		String[] recipesList = {"Rec1","Rec2","Rec3","Rec4","Rec5","Rec6","Rec7","Rec8","Rec9"};//!!change if adding ability to add recipes!!
		month = months[calendar.get(Calendar.MONTH)];
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
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
		
		//add blank days at first of month
		cal.set(Calendar.DATE, 1);
//		cal.set(Calendar.MONTH,2);
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
			File f = new File("Ingredients.txt");
			int r,dateCheck=0,count = 0;
			Random random = new Random();
			Scanner input = new Scanner(f);
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
