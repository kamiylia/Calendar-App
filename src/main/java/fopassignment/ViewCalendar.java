package src.main.java.fopassignment;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LENOVO
 */
public class ViewCalendar {
    private List<Calender_Event> events; // creates a master list where it is an empty filing cabinet where all the appointments will be stored
    
    // Fixed capitalization to match your usage in methods
    // instead of typing the data style, everytime, create a stamp to keep things consistent
    private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE dd");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MMM yyyy");

    public ViewCalendar() {
        this.events = new ArrayList<>(); // Initializes storage list
        
        // Adding sample events to make sure the output is not empty while test run
        events.add(new Calender_Event(LocalDate.of(2025, 10, 5), LocalTime.of(11, 0), "Assignment Meeting"));
        events.add(new Calender_Event(LocalDate.of(2025, 10, 7), LocalTime.of(15, 0), "Project Discussion"));
        events.add(new Calender_Event(LocalDate.of(2025, 10, 20), LocalTime.of(9, 30), "Team Checkpoint"));
    }

    public void checkAndPrintReminders() {
        System.out.println("\n" + "---".repeat(15));
        System.out.println("REMINDER NOTIFICATIONS");
        System.out.println("---".repeat(15));
        
        Duration reminderThreshold = Duration.ofDays(1); // setting a notification rule. if the event is within 24 hours, remind me.
        LocalDateTime now = LocalDateTime.now(); // checking the computer's clock to see the exact time it is right now.
        
        List<Calender_Event> reminders = this.events.stream()
                .filter(event -> { // this works like a sieve where the program only keeps the events that are in the future and within the next 24 hours
                    LocalDateTime startTime = event.getStartTime();
                    if (startTime.isBefore(now)) return false; 
                    Duration timeUntilEvent = Duration.between(now, startTime);
                    return timeUntilEvent.compareTo(reminderThreshold) <= 0;
                })
                .sorted((e1, e2) -> e1.getStartTime().compareTo(e2.getStartTime()))
                .collect(Collectors.toList());

        if (reminders.isEmpty()) {
            System.out.println("No events require an immediate reminder (within 24 hours).");
        } else {
            for (Calender_Event event : reminders) {
                Duration timeUntilEvent = Duration.between(now, event.getStartTime());
                long hours = timeUntilEvent.toHours();
                long minutes = timeUntilEvent.toMinutes() % 60;
                
                System.out.println("Your next event is coming soon in <" + hours + " hours, " + minutes + " minutes>: " 
                        + event.getDescription() + " on " + event.getDate().toString() + " at " + event.getTime().toString());
            }
        }
        System.out.println("---".repeat(15));
    }

    private List<Calender_Event> getEventsOnDate(LocalDate date) {
        return events.stream()
                .filter(e -> e.getDate().isEqual(date))
                .sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime()))
                .collect(Collectors.toList());
    }

    public void print_weekly_list_view(LocalDate startOfWeek) {
        // Fixed: changed 'startofWeek' to 'startOfWeek' to match parameter
        LocalDate sunday = startOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        
        System.out.println("// Weekly List View");
        System.out.println("~~~~~ Week of " + sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " ~~~~~");
        
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = sunday.plusDays(i);
            List<Calender_Event> dailyEvents = getEventsOnDate(currentDate);
            
            String dayLine = currentDate.format(DAY_DATE_FORMATTER) + ": ";
            
            if (dailyEvents.isEmpty()) {
                dayLine += "No events";
            } else {
                dayLine += dailyEvents.stream()
                        .map(Calender_Event::toString)
                        .collect(Collectors.joining(", "));
            }
            System.out.println(dayLine);
        }
    }

    public void printmonthly_calendar_view(YearMonth month) {
        System.out.println("\n// Calendar month view");
        System.out.println(month.format(MONTH_YEAR_FORMATTER));
        System.out.println("Sun || Mon || Tue || Wed || Thu || Fri || Sat");

        LocalDate firstOfMonth = month.atDay(1);
        int daysInMonth = month.lengthOfMonth();
        int firstDayOfWeekValue = firstOfMonth.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayOfWeekValue; i++) {
            System.out.print("    "); 
        }

        for (int currentDay = 1; currentDay <= daysInMonth; currentDay++) {
            LocalDate currentDate = month.atDay(currentDay);
            boolean hasEvents = !getEventsOnDate(currentDate).isEmpty();
            
            // Step 1: Format the number to always take 2 spaces (e.g., " 5" or "10")
            String dayString = String.format("%2d", currentDay);
            
            // Step 2: Add the event marker OR a blank space so the total width is always 3
            if (hasEvents) {
                System.out.print(dayString + "* "); // Width: 3 characters + 1 space
        } else {
                System.out.print(dayString + "  "); // Width: 3 characters + 1 space
    }
            
            if ((currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) || currentDay == daysInMonth) {
                System.out.println();
            }
        }
    }

    public static void CalendarViewing(String[] args) {
        ViewCalendar app = new ViewCalendar();
        app.checkAndPrintReminders();
        
        System.out.println("\n" + "=".repeat(45) + "\n");
        
        LocalDate weekStart = LocalDate.of(2025, 10, 5);
        app.print_weekly_list_view(weekStart);
        
        System.out.println("\n" + "~~~~~".repeat(15) + "\n");
        
        YearMonth monthToView = YearMonth.of(2025, 10);
        app.printmonthly_calendar_view(monthToView);
    }
}

// REMOVED 'public' keyword so it can live in the same file
class Calender_Event {
    private LocalDate date;
    private LocalTime time;
    private String description;
    
    public Calender_Event(LocalDate date, LocalTime time, String description) {
        this.date = date;
        this.time = time;
        this.description = description;
    }
    
    public LocalDateTime getStartTime() {
        return LocalDateTime.of(this.date, this.time);
    }
    
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getDescription() { return description; }
    
    public String getFormattedTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return "(" + time.format(timeFormatter) + ")";
    }
    
    @Override
    public String toString() {
        return description + " " + getFormattedTime();
    }
}
