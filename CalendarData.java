import java.util.Calendar;

/** This class handles the Calendar Data**/
public class CalendarData {

	private Calendar currentDate;
	
	// Constructor
    public CalendarData(Calendar currentDate) {
    	
    	this.currentDate = currentDate; 
    }
    
    // Getters
    public Calendar getCalendar() {
		return currentDate;
	}
	public int getCurrentYear() {
		return currentDate.get(Calendar.YEAR);
	}
	public int getCurrentMonth() {
		return currentDate.get(Calendar.MONTH);
	}
	public int getCurrentDay() {
		return currentDate.get(Calendar.DAY_OF_MONTH);
	}
	// Setter
	public void setDay(int day, int month, int year) {
	    currentDate.set(year,month, day);
	}
	
	// Method to return current month name in String
	public String getCurrentMonthName() {
		
	    String[] months = {
	        "January", "February", "March", "April", "May", "June",
	        "July", "August", "September", "October", "November", "December"};
	    
	    return months[getCurrentMonth()]; 
	}

	// toString method
	@Override
	public String toString(){
		return getCurrentDay() + " " + this.getCurrentMonthName() + " " + this.getCurrentYear();
	}

	// Method to get the first week day of a specific month in a specific year
	public int getFirstDayOfMonth() {
		
		int month = getCurrentMonth();
        int year = getCurrentYear();
        Calendar c = currentDate; 
		c.set(year, month, 1); // Set current date to the 1st day of the given month and year
        return currentDate.get(Calendar.DAY_OF_WEEK); // Get the day of the week of the 1st day of the month
	}
	
	// Method to get the number of days of a specific month in a specific year
	public int getNumberOfDays() {
		
		int month = getCurrentMonth();
        int year = getCurrentYear();
        
        Calendar c = currentDate;
        c.set(year, month, 1); // Set the date to the 1st day of the month
        return c.getActualMaximum(Calendar.DAY_OF_MONTH); // Get the correct number of days for the month
		
	}
}

