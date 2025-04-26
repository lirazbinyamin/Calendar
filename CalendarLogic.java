import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/** This class handles the Calendar Logic (Meetings data)**/
public class CalendarLogic {
	
	private Map<Calendar,String> meetingsPerDay; //Map to store Calendar keys and String values
	
	//Constructor
	public CalendarLogic() {
		
		meetingsPerDay = new HashMap<>();
	}

	// Method to add user's meeting input to Map
	public void addMeeting(Calendar dayOfMeeting, String meeting) {
		
		// Create a new Calendar object to ensure no existing Calendar object is reused
	    Calendar normalizedDate = (Calendar) dayOfMeeting.clone();
	    // Update meeting info in Map with String entered by user 
		meetingsPerDay.put(normalizedDate, meeting + "\n");
		
	}
	
	//Method to return meetings of the date pressed by user
	public String getMeetings(CalendarData date) {
		
		Calendar selectedDate = date.getCalendar();
	    String meetings = meetingsPerDay.get(selectedDate);// Retrieve meetings for the selected date from the map
	    // Return Meetings
	    if (meetings != null && !meetings.isEmpty()) 
	        return meetings;
	    else
	        return null;//	No meetings scheduled
	}
	
	//Method to return HashMap of meetings and dates as a StringBuilder
	public StringBuilder getMapString() {
		
		// Create a StringBuilder to collect all map entries
	    StringBuilder mapContent = new StringBuilder();

	    for (Map.Entry<Calendar, String> entry : meetingsPerDay.entrySet()) {
	    	// Get current date in Map into String
	    	CalendarData currentDate = new CalendarData(entry.getKey()); 
	        String currentDateString = currentDate.toString(); 
	        // Add currentDate String and currentDate value into the mapContent StringBuilder
	        mapContent.append(currentDateString).append(": ").append(entry.getValue()).append("\n"); 
	    }
	    return mapContent;
	}
}
