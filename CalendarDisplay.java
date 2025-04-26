import java.util.Calendar;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/** This class handles the Calendar Display**/
public class CalendarDisplay {

	// final integers to hold calendar rows and columns
    private final int CALENDAR_ROWS = 6;
    private final int CALENDAR_COLUMNS = 7;
    // doubles to hold weight and height
    private double w;
    private double h;
    // Calendar 
	private GridPane calendar;
    private Text monthText;
    private Text yearText;
    private CalendarData data;
    public CalendarLogic meetingsData;
    private Calendar currentDate = Calendar.getInstance();
	
    // Constructor
    public CalendarDisplay(GridPane calendar,CalendarData data, Text monthText, Text yearText) {
        
    	this.calendar = calendar;
        this.data = data;
        this.monthText = monthText;  
        this.yearText = yearText;   
        meetingsData = new CalendarLogic(); 
        w =calendar.getPrefWidth() / CALENDAR_COLUMNS; // Adjust for number of columns
        h = calendar.getPrefHeight() / CALENDAR_ROWS; // Adjust for number of rows
    }
    
    /** Method to draw a calendar based on date
     * 	When running the application for the first time calendar will be based on real time date.
     * 	if user will choose to move to other month/year (as described in task),
     *  the date will be updated to user's choice**/
    public void drawCalendar(CalendarData updatedData) {
    	
    	data = updatedData; // Update calendar data to updated CalendarData 
        calendar.getChildren().clear(); // Clear the GridPane before drawing the new calendar
    	setMonthText(data);//Month Text
    	setYearText(data);//Year Text
    	
        int firstDay = data.getFirstDayOfMonth();
        int daysInMonth = data.getNumberOfDays();
        
        // Create buttons for each day of the month
        int dayCounter = 1;
        for (int row = 0; row < CALENDAR_ROWS; row++) {
            for (int col = 0; col < CALENDAR_COLUMNS; col++) {
                if (row == 0 && col < firstDay - 1) {
                    continue;  // Skip the empty days before the first day of the month
                } 
                else if (dayCounter <= daysInMonth) {
                    Button dayBtn = new Button(String.valueOf(dayCounter)); //New button for day
                    dayBtn.setPrefSize(w,h);
                    //Mark current actual date so user
                    if (dayCounter==currentDate.get(Calendar.DAY_OF_MONTH) 
                    		&& data.getCurrentMonth() == currentDate.get(Calendar.MONTH) 
                    		&& data.getCurrentYear() == currentDate.get(Calendar.YEAR)) {
                    			setButtonColor(dayBtn,"lightblue","darkblue"); // Light blue background
                    }
                    else {
                    	setButtonColor(dayBtn,"white","darkblue"); // All other days with white background
                    	}
                    // Pressed button event
                    dayBtn.setOnAction(btnPressed -> handleButton(btnPressed));
                    calendar.add(dayBtn, col, row); // Add button to the GridPane
                    dayCounter++; // Move to next day
                }
            }
        }
    }
    
    // Method to handle pressed button of one of the days in the calendar
	private void handleButton(ActionEvent btnPressed) {
			
		 Button clickedBtn = (Button) btnPressed.getSource();
		 //When button pressed the color of it will be changed to user to notify user
		 setButtonColor(clickedBtn, "darkblue", "white");
		 // Reset the button color after a delay
		 PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		 pause.setOnFinished(event -> setButtonColor(clickedBtn, "white", "darkblue"));
		 pause.play();
				    
		 // Get the day number from the button
		 String day = clickedBtn.getText();
		 int dayInt = Integer.parseInt(day);
		 int monthInt = data.getCurrentMonth();
		 int yearInt = data.getCurrentYear();

		 // Update date to be the clicked one
		 data.setDay(dayInt,monthInt,yearInt);//yearInt, monthInt, dayInt);**/
		 //System.out.println("date is " + data.toString()); //check for button data
		 openMeetingDialog();
		    
	}
	
	/** Method to open a dialog to user when button of a day pressed.
	 * 	User will enter meetings information as a free text
	 * 	The method will update user's input in a HashMap "meetingsPerDay"
	 * **/
	public void openMeetingDialog() {
		
	    String currentMeetings  = meetingsData.getMeetings(data);
	    // Open dialog 
	    TextInputDialog dialog = new TextInputDialog(currentMeetings);
	    // Dialog settings:
	    dialog.getEditor().setPrefWidth(500);  // Set width
	    dialog.getEditor().setPrefHeight(80); // Set height
	    dialog.setGraphic(null);
	    dialog.getEditor().setStyle("-fx-font-size: 18px;");
	    dialog.setTitle(data.toString());
	    dialog.setHeaderText(null);
	    dialog.setContentText("Add/Edit meetings:");  
	    
	    // Adjust dialog position after it's shown
	    dialog.setOnShown(event -> {
	    	dialog.getDialogPane().getScene().getWindow().setX(400); // Adjust X-axis position
	        dialog.getDialogPane().getScene().getWindow().setY(430); // Adjust Y-axis position
	    });

	    Optional<String> result = dialog.showAndWait();
	    result.ifPresent(meetingsText -> {
	        // Add the meeting to "meetingsPerDay" HashMap in CalendarLogic
	        meetingsData.addMeeting(data.getCalendar(), meetingsText);
	    });    
	}
    
	// Method to set month Text in calendar
	private void setMonthText(CalendarData data) {
    	String monthString = data.getCurrentMonthName();
    	monthText.setText(monthString);
	}
	// Method to set year Text in calendar
	private void setYearText(CalendarData data) {
		String yearString = String.valueOf(data.getCurrentYear());
    	yearText.setText(yearString);
	}
	
	//Method to set button color
	private void setButtonColor(Button btn, String backgroundColor, String textColor) {
		btn.setStyle("-fx-background-color:" +backgroundColor +"; -fx-text-fill: " +textColor +";-fx-font-size: 18px;"); 
	}
	
	//Method to display to user meetings from HashMap which storing meetings in CalendarLogic
	public void displayMeetings() {
		
		StringBuilder mapContent = meetingsData.getMapString();
		// Create a TextArea for displaying the map content
	    TextArea textArea = new TextArea(mapContent.toString());
	    textArea.setEditable(false); // Make it read-only
	    textArea.setStyle("-fx-font-size: 18px;");
	    textArea.setPrefWidth(600); // Set preferred width
	    textArea.setPrefHeight(400); // Set preferred height

	    // Create a new Stage
	    Stage stage = new Stage();
	    stage.setTitle("Meetings");
	    // Create a Scene and add the TextArea to it
	    Scene scene = new Scene(textArea);
	    stage.setScene(scene);
	    stage.show();
	}
}



