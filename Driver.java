package calendar;

import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * This class executes the start method of the JavaFX program. This is the
 * final project for CSCI 1112 Object Oriented Programming
 * 
 * It creates a calendar based off of the current month, and generates a month of meals.
 * These meals come from a hard coded text file titled "Ingredients.txt" and the meals
 * can be selected from the list of combo boxes on the right hand side of the program
 * 
 * 
 * It is then possible to select from a period of time all of the ingredients needed
 * to make those meals and concatenates them into a single shopping list.
 * Able to make selections from a single day, a single week, or the entire month. 
 * @author Dallen Corry
 * @version 2.2
 * @since Jan/27/2022
 */
public class Driver extends Application{
	/**
	 * static data fields 
	 */
	static String month;//String value of the month.
	static int firstDay;//1-7 first day of the current month
	static int daysInMonth;//number of days in the current month
	static boolean hasMeals;//if the month has meals already generated
	
	
	@Override
	public void start(Stage main) {
		//FX Pane Objects
		BorderPane pane = new BorderPane();
		GridPane calendarPane = new GridPane();
		ScrollPane rightSide = new ScrollPane();
		VBox mealSelect = new VBox();
		HBox top = new HBox();
		VBox sListPane = new VBox();
		HBox DWM = new HBox();
		
		//FX Control Objects
		Button btShoppingList = new Button(" View Shopping List ");
		Button btRandom = new Button("Random Calendar");
		Button btCheckAll = new Button(" Select All Meals ");
		Button btCheckNone = new Button(" Clear Selection  ");
		ArrayList<CheckBox> cbMeals = new ArrayList<CheckBox>();
		Alert alert = new Alert(AlertType.ERROR, "Select at least 1 meal");
		Text title = new Text("View your Shopping List for the day, week, or month");
		RadioButton rbDay = new RadioButton("Day");
		RadioButton rbWeek = new RadioButton("Week");
		RadioButton rbMonth = new RadioButton("Month");
		ToggleGroup tgDWM = new ToggleGroup();
		ComboBox<String> dateRange = new ComboBox<String>();
		TextArea sListText = new TextArea();
		
		//FX Stage Objects
		Scene sListScene = new Scene(sListPane);
		Stage sListStage = new Stage();
		sListStage.setX(0);
		sListStage.setY(50);
		sListStage.setScene(sListScene);
		sListStage.setTitle("Shopping List");
		
		
		//Other Objects
		ArrayList<DayPane> list = new ArrayList<DayPane>();
		Calendar calendar = new GregorianCalendar();
		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		ArrayList<String> weeks = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		ArrayList<Meal> meals = new ArrayList<Meal>();
		
		
		//Instantiation
		month = months[calendar.get(Calendar.MONTH)];
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,	1);
		firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		//Instantiate days in the date selection
		days.clear();
		for(int i=0; i<daysInMonth; i++) {
			days.add(i+1+"");
		}
		dateRange.getItems().clear();
		dateRange.getItems().addAll(days);
		
		
		//properties of FX Control Objects
		btRandom.setStyle("-fx-background-color: #edd992;"+
				"-fx-border-color: #dfbb3a;");
		btShoppingList.setStyle("-fx-background-color: #edd992");
		btCheckAll.setStyle("-fx-background-color: #edd992");
		btCheckNone.setStyle("-fx-background-color: #edd992");
		title.setFont(new Font(15));
		title.setFill(Color.WHITE);
		rbDay.setToggleGroup(tgDWM);
		rbWeek.setToggleGroup(tgDWM);
		rbMonth.setToggleGroup(tgDWM);
		rbDay.setTextFill(Color.WHITE);
		rbWeek.setTextFill(Color.WHITE);
		rbMonth.setTextFill(Color.WHITE);
		rbDay.setSelected(true);
		dateRange.setPromptText("Select Date Range");
		sListText.setStyle("-fx-control-inner-background: #a3c6dc");
		sListText.setPrefWidth(350);
		sListText.setPrefHeight(500);
		sListText.setPromptText("No Dates Selected");
		sListText.setWrapText(true);
		sListText.setFont(new Font("Courier",12));
		sListText.setEditable(false);
		
		//Properties of FX Pane Objects
		calendarPane.setHgap(3);
		calendarPane.setVgap(3);
		calendarPane.setPadding(new Insets(5,5,5,5));
		mealSelect.setStyle("-fx-background-color: #a3c6dc;"+
				"-fx-border-color: #a3c6dc");
		mealSelect.setPadding(new Insets(5,5,5,5)); 
		mealSelect.setSpacing(5);
		mealSelect.setPrefWidth(200);
		mealSelect.setPrefHeight(600);
		top.setStyle("-fx-background-color: #2c5ea0;");
		top.setPadding(new Insets(5,5,5,5));
		top.setAlignment(Pos.CENTER);
		top.setSpacing(5);
		sListPane.setStyle("-fx-background-color: #2c5ea0");
		sListPane.setAlignment(Pos.CENTER);
		sListPane.setPadding(new Insets(5,0,0,0));
		sListPane.setSpacing(5);
		DWM.setAlignment(Pos.CENTER);
		DWM.setSpacing(5);
		
		//Functions
		
		  //generate the meal calendar
		generateCal(list);
		
		  //generate list of meals
		generateMeals(meals);
		
		  //generate Check Boxes for meals
		for(int i=0; i<meals.size(); i++) {
			cbMeals.add(new CheckBox(meals.get(i).getName()));
			cbMeals.get(i).setSelected(true);
		}
		
		  //add all days to the Grid Pane
		for(int i=0; i<6; i++) {
			for (int j=0; j<7; j++) {
				if(null!=list.get((i*7)+j) && ((i*7)+j)<list.size()){
					calendarPane.add(list.get((i*7)+j), j, i);
				}
			}
		}
		
		//Place objects in Panes
		mealSelect.getChildren().addAll(new Text("Select Meals"),btCheckAll,btCheckNone);
		for(CheckBox i:cbMeals ) {
			mealSelect.getChildren().add(i);
		}
		rightSide.setContent(mealSelect);
		top.getChildren().addAll(btShoppingList,btRandom);
		DWM.getChildren().addAll(rbDay,rbWeek,rbMonth);
		sListPane.getChildren().addAll(title,DWM,dateRange,sListText);
		pane.setCenter(calendarPane);
		pane.setRight(mealSelect);
		pane.setTop(top);
		
		
				
		//Button Actions	
		//show shopping list
		btShoppingList.setOnAction(e -> {
			sListStage.show();
		});
				
		//Generate random Meals
		btRandom.setOnAction(e->{
//					gp.getChildren().removeAll(list);
			randomizeCal(list, cbMeals);
			boolean isSelected=false;
			for(CheckBox i:cbMeals) {
				if(i.isSelected()) {
					isSelected = true;
				}
			}
			calendarPane.getChildren().clear();
			//add all days to the grid pane
			if(!isSelected) {
				alert.showAndWait();
			}
			for(int i=0; i<6; i++) {
				for (int j=0; j<7; j++) {
					if(null!=list.get((i*7)+j) && ((i*7)+j)<list.size()){
						calendarPane.add(list.get((i*7)+j), j, i);
					}
				}
			}
			dateRange.fireEvent(e);
		});
		
		//select all meals in the combo box
		btCheckAll.setOnAction(e ->{
			for (CheckBox i:cbMeals) {
				i.setSelected(true);
			}
		});
		
		//deselect all meals in the combo box
		btCheckNone.setOnAction(e -> {
			for (CheckBox i:cbMeals) {
				i.setSelected(false);
			}
		});
		
		//set shopping list selection to day
		rbDay.setOnAction(e ->{
			days.clear();
			for(int i=0; i<daysInMonth; i++) {
				days.add(i+1+"");
			}
			dateRange.getItems().clear();
			dateRange.getItems().addAll(days);
		});
		
		//set shopping list selection to Week
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
		
		//set shopping list selection to Month
		rbMonth.setOnAction(e -> {
			dateRange.getItems().clear();
			dateRange.getItems().add("Current Month");
		});
		
		//calculates the range of dates in the selected time period of Day, Week or Month.
		dateRange.setOnAction(e ->{
			//add support for the month

			if(hasMeals) {
				if(null == dateRange.getValue()) {
					sListText.setText("No Dates Selected");
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
						str="Week: "+dateRange.getValue()+"\n\n";
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
					sListText.setText(str);
				}
			}
			else {
				sListText.setText("No Meals Generated\nClick \"Random Calendar\"");
			}
		});	
				
		//Show Stages
		calendarPane.setStyle("-fx-background-color: #a3c6dc;");
		Scene scene = new Scene(pane);
		main.setScene(scene);
		main.setTitle(month+"Meal Calendar");
		main.setX(350);
		main.setY(0);
		main.show();
	}
	/**
	 * Creates a list of meals from a recipe file (hard coded in as "Ingredients.txt"
	 * @param meals The list of meals to create
	 */
	private void generateMeals(ArrayList<Meal> meals) {
		try {
			int count =0;
			File f = new File("Ingredients.txt");
			Scanner scanner = new Scanner(f);
			Scanner input = new Scanner(f);
			
			//get number of Lines in file
			while(input.hasNextLine()) {
				count++;
				input.nextLine();
			}
			input.close();
			
			for(int i=0; i<count; i++) {
				 meals.add(new Meal(scanner.nextLine()));
			}
			scanner.close();
		}catch(Exception e) {
			System.out.println("error in Meal Generation: "+e);
		}
	}
	
	/**
	 * Generate a months worth of DayPanes based on the current calendar month
	 * @param list the list of all the Days in a month
	 */
	public static void generateCal(ArrayList<DayPane> list){
		Calendar cal = new GregorianCalendar();
		hasMeals = false;		
		//add blank days at first of month
		cal.set(Calendar.DATE, 1);
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
	}
	
	/**
	 * Randomizes the meals in the current Calendar
	 * @param list an array List containing all of the day panes for the month
	 * @param cbMeals an Array List of checkBoxes of meals that have been selected
	 */
	public static void randomizeCal(ArrayList<DayPane> list, ArrayList<CheckBox> cbMeals) {
		
		try {
			hasMeals = true;
			File f = new File("Ingredients.txt");
			int r,dateCheck=0;
			Random random = new Random();
			ArrayList<Integer> indices = new ArrayList<Integer>();
			//get the indices of each selected meal from cbMeals
			for( int i=0; i<cbMeals.size(); i++) {
				if(cbMeals.get(i).isSelected()) {
					indices.add(i+1);
				}
			}
			//generate random meals from the selected meals
			if(indices.size()<1) {
				throw new IllegalArgumentException("Select at least 1 meal");
			}else {
				for(int i=0; i<list.size(); i++) {
					if(list.get(i)==null) {
						dateCheck++;
					}
					if(list.get(i)!=null) {
						r = random.nextInt(indices.size());
						list.set(i, new DayPane(indices.get(r),f,i-dateCheck+1));
					}
				}
			}
		}catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e);

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
