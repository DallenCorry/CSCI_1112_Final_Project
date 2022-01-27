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
import javafx.scene.control.Alert.AlertType;

public class TrialDriver extends Application{
	static String month;//String value of the month.
	static int firstDay;//1-7 first day of the month
	static int daysInMonth;//number of days in the month
	static boolean hasMeals;//to see if we had created meals yet. UNUSED
	
	public void start(Stage s) {
		//Make stuff
		
		ArrayList<DayPane> list = new ArrayList<DayPane>();
		Calendar calendar = new GregorianCalendar();
//		calendar.set(Calendar.MONTH, 3);
		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		ArrayList<String> weeks = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		ArrayList<Meal> meals = new ArrayList<Meal>();
		month = months[calendar.get(Calendar.MONTH)];
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,	1);
		firstDay = calendar.get(Calendar.DAY_OF_WEEK);

		
		BorderPane pane = new BorderPane();
		GridPane calendarPane = new GridPane();
		VBox mealSelect = new VBox();
		ScrollPane rightSide = new ScrollPane();
		HBox top = new HBox();
		VBox sListPane = new VBox();
		HBox DWM = new HBox();
		
		Button btShoppingList = new Button(" View Shopping List ");
		Button btRandom = new Button(" Generate Random Calendar ");
		Button btCheckAll = new Button(" Select All Meals ");
		Button btCheckNone = new Button(" DeSelect All Meals ");
		ArrayList<CheckBox> cbMeals = new ArrayList<CheckBox>();
		Alert alert = new Alert(AlertType.ERROR, "Select at least 1 meal");
		Text title = new Text("View your Shopping List for the day, week or month");
		RadioButton rbDay = new RadioButton("Day");
		RadioButton rbWeek = new RadioButton("Week");
		RadioButton rbMonth = new RadioButton("Month");
		ComboBox<String> dateRange = new ComboBox<String>();
		TextArea sListText = new TextArea();
		
		sListText.setPrefWidth(350);
		sListText.setPrefHeight(500);
		sListText.setPromptText("No Dates Selected");
		sListText.setWrapText(true);
		sListText.setFont(new Font("Courier",12));
		title.setFont(new Font(15));
		
		DWM.getChildren().addAll(rbDay,rbWeek,rbMonth);
		sListPane.getChildren().addAll(title,DWM,dateRange,sListText);
		
		Scene sListScene = new Scene(sListPane);
		Stage sListStage = new Stage();
		sListStage.setX(0);
		sListStage.setY(50);
		sListStage.setScene(sListScene);
		sListStage.setTitle("Shopping List");
		
		TextArea taNew = new TextArea("This is some text");
		Pane p = new Pane(taNew);
		Scene addNew = new Scene(p);
		Stage addNewStage = new Stage();
		addNewStage.setScene(addNew);
		
		//Advanced make stuff
		
		ToggleGroup tgDWM = new ToggleGroup();
		rbDay.setToggleGroup(tgDWM);
		rbWeek.setToggleGroup(tgDWM);
		rbMonth.setToggleGroup(tgDWM);
		
		
		
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
		
		
		//Do Stuff
		//show shopping list
		btShoppingList.setOnAction(e -> {
			sListStage.show();
		});
				
		btRandom.setOnAction(e->{
//			gp.getChildren().removeAll(list);
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
		});
		
		btCheckAll.setOnAction(e ->{
			for (CheckBox i:cbMeals) {
				i.setSelected(true);
			}
		});
		
		btCheckNone.setOnAction(e -> {
			for (CheckBox i:cbMeals) {
				i.setSelected(false);
			}
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
				sListText.setText("No Meals Generated\nClick \"Generate Random Calendar\"");
			}
		});
		
		//Pretty stuff
		
		top.getChildren().addAll(btShoppingList,btRandom);
		top.setAlignment(Pos.CENTER);
		//fix meal select - right side
//		mealSelect.getChildren().addAll(new Text("Select Meals"),btCheckAll,btCheckNone);
//		for(CheckBox i:cbMeals ) {
//			mealSelect.getChildren().add(i);
//		}
//		mealSelect.setContent(mealSelect);
		
		top.setPadding(new Insets(5,5,5,5)); 
		top.setSpacing(5);
		calendarPane.setStyle("-fx-border-color: black");
		calendarPane.setGridLinesVisible(false);
		calendarPane.setHgap(3);
		calendarPane.setVgap(3);
		calendarPane.setPrefHeight(610);//610
		calendarPane.setPrefWidth(720);
		calendarPane.setPadding(new Insets(5,5,5,5));
		mealSelect.setStyle("-fx-border-color: blue");
		mealSelect.setPadding(new Insets(5,5,5,5)); 
		mealSelect.setSpacing(5);
		mealSelect.setPrefWidth(200);
		mealSelect.setPrefHeight(500);
		sListPane.setAlignment(Pos.CENTER);
		sListPane.setPadding(new Insets(5,5,5,5));
		sListPane.setSpacing(5);
		DWM.setAlignment(Pos.CENTER);
		DWM.setSpacing(5);
		sListText.setEditable(false);
		
		
		pane.setTop(top);
		pane.setCenter(calendarPane);
		pane.setRight(mealSelect);
		pane.setPadding(new Insets(5,5,5,5));
		calendarPane.setStyle("-fx-background-color: black;");
		
		dateRange.setPromptText("Select Date Range");
		
		Scene scene = new Scene(pane);
		s.setScene(scene);
		s.setTitle(month +" Meal Calendar");
		s.setX(350);
		s.setY(0);
		s.show();
	}
	
	
	private void generateMeals(ArrayList<Meal> meals) {
		try {
			//instead of using meals, we could just directly get the string and set it to the CheckBox
			//since we don't ever use the names of the strings again, just their indices.
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
	 * @param list
	 * @return
	 */
	public static void generateCal(ArrayList<DayPane> list){
		Calendar cal = new GregorianCalendar();
		hasMeals = false;
		
		//add blank days at first of month
		cal.set(Calendar.DATE, 1);
//		cal.set(Calendar.MONTH,3);
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
		
		
		//un needed because it is taken care of in DayPane
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
	public static void randomizeCal(ArrayList<DayPane> list, ArrayList<CheckBox> cbMeals) {
		
		try {
			hasMeals = true;
			File f = new File("Ingredients.txt");
			int r,dateCheck=0;
			Random random = new Random();
			Scanner input = new Scanner(f);
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
