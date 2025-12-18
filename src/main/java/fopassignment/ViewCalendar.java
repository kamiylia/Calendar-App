import java.time.LocalDateTime;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO
 */

public class Calender_Event {
    private LocalDate date; // stores the year, month and day
    private LocalTime time; // stores the hour and minutes
    private String description; // acts as a storage container for the "name" or "label" of the calendar entry
    
    public Calender_Event(LocalDate date, LocalTime time, String description) { // parameters needed to successfully create and event
        this.date = date;
        this.time = time;
        this.description = description; // takes the specific text the user types in (like "Assignment Meeting) and saves it into this variable at the top of the class.
    }
    
    // We have used "this" to tell java the difference between the private storage variable (this.date) and the incoming data (date) from the constructor.
    
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}


public class Assignment_ViewCalendar {
    private List<Event> events; // this is a master list where all the saved evenrs are stored
    private static final DateTimeFormatter Day_Date_Formatter = DateTimeFormatter.ofPattern("EEE dd");
    private static final DateTimeFormatter Month_Year_Formatter = DateTimeFormatter.ofPattern("MMM yyy");
    
    // Checks all events against a predefined reminder duration and prints a notification for events coming soon upon program launch.
    public void checkAndPrintReminders() {
        System.out.println("\n" + "---".repeat(15));
        System.out.println("REMINDER NOTIFICATONS");
        System.out.println("---".repeat(15));
        
        // Define the reminder settings (This is the user-set duration)
        // We set the reminder threshold to 1 day (24 hours) for this example.
        Duration reminderThreshold = Duration.ofDays(1); // if an event is inside this 24 hour window, it triggers a notification
        
        // For a 30 minute reminder : Duration reminderThreshold = Duration.ofMinutes(30);
        
        // Get the current time for comparison
        LocalDateTime now = LocalDateTime.now(); // grabs the exact time on the computer right now to compare it against the events.
        
        // Filter the events list to find events that meet the criteria
        List<Event> reminders = this.events.stream()
                .filter(event -> { // throws away events that already happened and only keeps those happening within the next 24 hours.
                    LocalDateTime startTime = event.getStartTime();
                    
                    // Check if the event is in the future
                    if (startTime.isBefore(now)) {
                        return false; // Skips events that have already passed
                    }
                    
                    // Calculate the time until the event
                    Duration timeUntilEvent = Duration.between(now, startTime);
                    
                    // Check if the event is closer than the reminder threshold
                    return timeUntilEvent.compareTo(reminderThreshold) <= 0;
                })
                .collect(Collectors.toList());
        
        if (reminders.isEmpty()) {
            System.out.println("No events require an immediate reminder (within " + reminderThreshold.toHours() + " hours).");
        } else {
            // Sort reminders by time (next one first)
            reminders.sort((e1, e2) -> e1.getStartTime().compareTo(e2.getStartTime()));
            
            for (Event event : reminders) {
                Duration timeUntilEvent = Duration.between(now, event.getStartTime());
                
                // Format the duration into a readable message (e.g. 20 hours and 45 minutes)
                long hours = timeUntilEvent.toHours();
                long minutes = timeUntilEvent.toMinutes();
                long days = timeUntilEvent.toDays();
                
                String durationMessage; // calculates the time gap. if it is more than 24 hours, it shows "Days". if less, it shows "Hours" or "Minutes"
                if (days > 0) {
                    durationMessage = " days, " + (hours % 24) + " hours";
                } else if (hours > 0) {
                    durationMessage = " hours, " + minutes + " minutes";
                } else {
                    durationMessage = minutes + " minutes";
                }
                
                // Printing the notification as suggested in the assignment
                System.out.println("Your next event is coming soon in <" + durationMessage + ">: " + event.getDescription() + " on " + event.getDate().toString() + " at " + event.getTime().toString());
            }
        }
        System.out.println("---".repeat(15));
    }
    
    public Assignment_ViewCalendar() {
        this.events = new ArrayList<>(); // this line initializes the master list. the program would crash if an event is added without this line of code
        
        // Example: Sun 05: Assignment Meeting (11:00) - Assuming Oct 5, 2025 is a Sunday
        events.add(new Event(LocalDate.of(2025, 10, 5), LocalTime.of(11, 0), "Assignment Meeting"));
        // Example: Tue 07: Project Discussion (15:00) - Assuming Oct 7, 2025 is a Tuesday
        events.add(new Event(LocalDate.of(2025, 10, 7), LocalTime.of(15, 0), "Project Discussion"));
        // Additional event for the calendar view to show another day has events
        events.add(new Event(LocalDate.of(2025, 10, 20), LocalTime.of(9, 30), "Team Checkpoint"));
    }
    
    // events.add takes an event object and stores it into the storage box
    // new Event creates a specific meeting
    // LocalDate.of(2025, 10, 5) sets the date to October 5th 2025
    // LocalTime.of(11, 0) swets the time to exactly 11.00 AM 
    // "Assignment Meeting" is the description that tells you what the meeting is for.
    // others are examples so it does not look empty when we test it out.
    
    
    private List<Event> getEventsOnDate(LocalDate date) {
        return events.stream()
                .filter(e -> e.getDate().isEqual(date))
                .sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime()))
                .collect(Collectors.toList());
    }
    
    
    public void print_weekly_list_view(LocalDate startOfWeek) {
        LocalDate sunday = startofWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        
        System.out.println("// Weekly List View");
        System.out.println("~~~~~ Week of " + sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " ~~~~~");
        
        for  (int i = 0; i < 7; i++) {
            LocalDate currentDate = sunday.plusDays(i);
            List<Event> dailyEvents = getEventsOnDate(currentDate);
            
            String dayLine = currentDate.format(DAY_DATE_FORMATTER) + ": ";
            
            if (dailyEvents.isEmpty()) {
                dayLine += "No events";
            } else {
                String eventDetails = dailyEvents.stream()
                        .map(Event::toString)
                        .collect(Collectors.joining(", "));
                dayLine += eventDetails;
        }
        System.out.println(dayLine);
            
    }
}
    
// ----------------------------------------------------
    // Monthly Calendar View Implementation
    // ----------------------------------------------------
    

private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MMM yyy"); {
    System.out.println("\n// Calendar month view");
    System.out.println(month.format(MONTH_YEAR_FORMATTER));
    
    // Print the header
    System.out.println("Sun || Mon || Tue || Wed || Thu || Fri || Sat");
    
    LocalDate firstOfMonth = month.atDay(1);
    int daysInMonth = month.lengthOfMonth();
    
    // Calculate the day of the week for the 1st of the month (1=Monday, ..., 7=Sunday)
    // We want 0=Sunday, 1=Monday, ..., 6=Saturday
    // DayOfWeek.getValue() returns 1 (Mon) to 7 (Sun). 
    // We use .getValue() % 7 to get 0 for Sunday.
    int firstDayOfWeekValue = firstOfMonth.getDayOfWeek().getValue() % 7;
    
    // Print initial leading spaces (blanks before the 1st)
    for (int i = 0; i < firstDayOfWeekValue; i++) {
        System.out.print(" "); // 3 spaces for alignment
    }
    
    // Keep track of the current day in the month
    int currentDay = 1;
    
    while(currentDay <= daysInMonth) {
        LocalDate currentDate = month.atDay(currentDay);
        
        // Check for events on this day
        boolean hasEvents = !getEventsOnDate(currentDate).isEmpty();
        
        // Print the day number (padded with a space for single-digit days)
        String dayString = String.format("%2d", currentDay);
        
        // Append the event marker '*' if there is an event
        if (hasEvents) {
            // Check if the number is single-digit (1-9). If so, replace the leading space with '*'
                // to match the example '5*' and ' * 5'.
                // If the day is a single digit (1-9), the format will be " 5".
                // If it's the start of the line (or a special day), use the '* 5' format from the example.
                
                // Let us simplify and put the '*' after the number for ALL event days, e.g., " 5*" or "10*"
                dayString += "*";
                // Since we are using %2d,the length is 2. If we add '*', the length is 3.
                dayString = String.format("%-3s", dayString); // Left pad to 3 
        } else {
            dayString += " "; // Pad to three characters total: " 5 "
        }
        
        System.out.print(dayString);
        
        // Move to the next line if it is Saturday (i.e., day of week is 6)
        if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY && currentDay != daysInMonth) {
            System.out.println();
        }
        
        currentDay++;
    }
    
    // Print a final newline character to finish the last row
    System.out.println();
    
    // Print event details below the calendar, as in the example: * 5: Assignment Meeting (11.00)
    LocalDate firstEventDay = LocalDate.of(month.getYear(), month.getMonth(), 5); // Day 5 is the example event
    getEventsOnDate(firstEventDay).forEach(event ->{
        
        System.out.println("* " + firstEventDay.getDayOfMonth() + ": " + event);
    });
    
}


// ----------------------------------------------------
// Main Method for Demonstration
// ----------------------------------------------------


public static void main(String[] args) {
    Assignment_ViewCalendar app = new Assignment_ViewCalendar();
    
    // Call the reminder check first
    app.checkAndPrintReminders();
    
    System.out.println("\n" + "=".repeat(45) + "\n");
    
    // Then, calling the view methods
    LocalDate weekStart = LocalDate.of(2025, 10, 5);
    app.print_weekly_list_view(weekStart);
    System.out.println("\n" + "~~~~~".repeat(15) + "\n");
    
    // ~~~~~ Demonstrate Monthly Calendar View ~~~~~
    YearMonth monthToView = YearMonth.of(2025, 10); // October 2025
    app.printmonthly_calendar_view(monthToView);
    
    
}




}
