package calendar;

import java.io.File;

import javafx.scene.layout.Pane;
//import javafx.scene.text.*;
import javafx.scene.control.*;

/**
 * This class creates a specific pane that contains a Day, TextArea and a specified Width and Height
 * @author Dallen Corry
 * @version 1.2
 * @since Jan/27/2022
 */
public class DayPane extends Pane{
	private Day day;
	private TextArea t;
	private static final int defaultWidth = 95;
	private static final int defaultHeight = 95;
	
	/**
	 * Constructors
	 */
	public DayPane() {
		day = null;
		t = new TextArea("");
		formatPane();
	}
	
	public DayPane(int date) {
		day = null;
		t = new TextArea(date+"\n  No Meal");
		formatPane();
	}
	public DayPane(int date, int width,int height) {
		day = null;
		t = new TextArea(date+"\n  No Meal");
		formatPane(width, height);
	}
	
	public DayPane(int line, File f, int date) {
		day = new Day(line, f, date);
		t = new TextArea(this.day.toStringWithoutIngredients());
		formatPane();
	}
	
	public DayPane(int line, File f, int date, int width, int height) {
		day = new Day(line, f, date);
		t = new TextArea(this.day.toStringWithoutIngredients());
		formatPane(width,height);
	}
	
	/**
	 * Formats the pane to a default width and height, and sets the text in the pane
	 */
	public void formatPane() {
		t.setLayoutY(1);
		t.setLayoutX(1);
		t.setPrefWidth(defaultWidth);
		t.setPrefHeight(defaultHeight);
		t.setWrapText(true);
		t.setEditable(false);
		getChildren().add(t);
	}
	/**
	 * Formats the pane to a Specified width and height, and sets the text in the pane
	 * @param width Width of the pane
	 * @param height Height of the pane
	 */
	public void formatPane(int width, int height) {
		t.setLayoutY(1);
		t.setLayoutX(1);
		t.setPrefWidth(width);
		t.setPrefHeight(height);
		t.setWrapText(true);
		t.setEditable(false);
		getChildren().add(t);
	}
	
	/**
	 * 
	 * @return the day specified in the pane
	 */
	public Day getDay() {
		return day;
	}
	
	/**
	 * 
	 * @return the text of the pane
	 */
	public String getText() {
		return t.getText();
	}
}
