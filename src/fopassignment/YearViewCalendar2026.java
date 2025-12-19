//Tigi

package src.fopassignment;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class YearViewCalendar2026 {
    private List<Calender_Event> events; 
    private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE dd");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MMM yyyy");

    public YearViewCalendar2026() {
        this.events = new ArrayList<>(); // Initializes the master list
        
        // Example Events for 2025
        events.add(new Calender_Event(LocalDate.of(2025, 10, 5), LocalTime.of(11, 0), "Assignment Meeting"));
        events.add(new Calender_Event(LocalDate.of(2025, 10, 7), LocalTime.of(15, 0), "Project Discussion"));
        events.add(new Calender_Event(LocalDate.of(2025, 10, 20), LocalTime.of(9, 30), "Team Checkpoint"));
        
        // New Year 2026 Event to test the year view markers
        events.add(new Calender_Event(LocalDate.of(2026, 1, 1), LocalTime.of(0, 1), "New Year's Day"));
    }

    // --- REMINDER LOGIC ---
    public void checkAndPrintReminders() {
        System.out.println("\n" + "---".repeat(15));
        System.out.println("REMINDER NOTIFICATIONS");
        System.out.println("---".repeat(15));
        
        Duration reminderThreshold = Duration.ofDays(1); // 24-hour window
        LocalDateTime now = LocalDateTime.now(); 
        
        List<Calender_Event> reminders = this.events.stream()
                .filter(event -> {
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

    // --- CALENDAR VIEWS ---
    private List<Calender_Event> getEventsOnDate(LocalDate date) {
        return events.stream()
                .filter(e -> e.getDate().isEqual(date))
                .sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime()))
                .collect(Collectors.toList());
    }

    public void print_weekly_list_view(LocalDate startOfWeek) {
        LocalDate sunday = startOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        System.out.println("\n// Weekly List View");
        System.out.println("~~~~~ Week of " + sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " ~~~~~");
        
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = sunday.plusDays(i);
            List<Calender_Event> dailyEvents = getEventsOnDate(currentDate);
            String dayLine = currentDate.format(DAY_DATE_FORMATTER) + ": ";
            
            if (dailyEvents.isEmpty()) {
                dayLine += "No events";
            } else {
                dayLine += dailyEvents.stream().map(Calender_Event::toString).collect(Collectors.joining(", "));
            }
            System.out.println(dayLine);
        }
    }

    public void printmonthly_calendar_view(YearMonth month) {
        System.out.println("\n// Calendar month view");
        System.out.println(month.format(MONTH_YEAR_FORMATTER));
        System.out.println("  Sun  ||  Mon  ||  Tue  ||  Wed  ||  Thu ||  Fri  ||  Sat   ");

        LocalDate firstOfMonth = month.atDay(1);
        int daysInMonth = month.lengthOfMonth();
        int firstDayOfWeekValue = firstOfMonth.getDayOfWeek().getValue() % 7;

        // Leading spaces (4 spaces per day to match fixed-width days)
        for (int i = 0; i < firstDayOfWeekValue; i++) {
            System.out.print("    "); 
        }

        for (int currentDay = 1; currentDay <= daysInMonth; currentDay++) {
            LocalDate currentDate = month.atDay(currentDay);
            boolean hasEvents = !getEventsOnDate(currentDate).isEmpty();
            
            // Fixed-width alignment: 2 spaces for number + 1 space for marker + 1 space for gap
            String dayString = String.format("%6d", currentDay);
            System.out.print(dayString + (hasEvents ? "* " : "  "));

            // Start a new line after Saturday
            if ((currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) || currentDay == daysInMonth) {
                System.out.println();
            }
        }
    }

    public void printYearlyCalendarView(int year) {
        System.out.println("\n" + "=".repeat(45));
        System.out.println("          FULL YEAR CALENDAR: " + year);
        System.out.println("=".repeat(45));

        for (int m = 1; m <= 12; m++) {
            printmonthly_calendar_view(YearMonth.of(year, m));
            System.out.println("-".repeat(45));
        }
    }

    public static void main(String[] args) {
        YearViewCalendar2026 app = new YearViewCalendar2026();
        
        // 1. Reminders
        app.checkAndPrintReminders();
        
        // 2. Weekly View
        app.print_weekly_list_view(LocalDate.of(2025, 10, 5));
        
        // 3. Yearly View (2026)
        app.printYearlyCalendarView(2026);
    }
}


