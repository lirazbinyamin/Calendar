import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Calendar;

public class CalendarController {

    private CalendarDisplay display;
    private CalendarData data;
    private Calendar date =  Calendar.getInstance(); // Calendar Object to hold a changeable date to update display
    
    @FXML
    private GridPane calendar; // Calendar displayed in a GridPane
    
    // Month and year Text on calendar
    @FXML
    private Text monthText;
    @FXML
    private Text yearText;
    
    //Calendar buttons
    @FXML
    private Button nextMonthButton;
    @FXML
    private Button nextYearButton;
    @FXML
    private Button prevMonthButton;
    @FXML
    private Button prevYearButton;
    @FXML
    private Button showMeetingsButton; // Button I added to check the meetingsPerDay Map located in CalendarLogic Class
    @FXML
    private Button currentDateButton; // Button I added to help user to go back to current date
    
    // Action Events for buttons
    @FXML
    void nxtMonthPressed(ActionEvent event) {
    	date.add(Calendar.MONTH, 1);
    	updateCalendar(date);
    }
    @FXML
    void nxtYearPressed(ActionEvent event) {
    	date.add(Calendar.YEAR, 1);
    	updateCalendar(date);
    }
    @FXML
    void prvMonthPressed(ActionEvent event) {
    	date.add(Calendar.MONTH, -1);
    	updateCalendar(date);
    }
    @FXML
    void prvYearPressed(ActionEvent event) {
    	date.add(Calendar.YEAR, -1);
    	updateCalendar(date);
    }
    @FXML
    void showMeetingsPressed(ActionEvent event) {
    	display.displayMeetings();
    }
    @FXML
    void currentDatePressed(ActionEvent event) {
    	
    	date= Calendar.getInstance();
    	updateCalendar(date); // Go back to calendar with current date
    }
    
    //Initialize
    @FXML
    public void initialize() {
    	
    	data = new CalendarData(date);
        display = new CalendarDisplay(calendar, data, monthText, yearText);
        currentDateButton.setText(data.toString()); // Update Current Date Button
        updateCalendar(date); // Update Calendar to current date
        }
    
    // Method to get a Calendar and update the calendar display based on it 
    private void updateCalendar(Calendar updated) {
    	data = new CalendarData(updated); // Update Calendar Data to a given updated date
        display.drawCalendar(data);  // Draw the calendar based on Calendar Data
    }
    
    

}
