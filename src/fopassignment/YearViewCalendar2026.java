//Tigi

package fopassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class YearViewCalendar2026 {

    private static List<Calender_Event> events = new ArrayList<>();

    private static final DateTimeFormatter DAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("EEE dd");

    private static final DateTimeFormatter MONTH_YEAR_FORMATTER =
            DateTimeFormatter.ofPattern("MMM yyyy");

    // ✅ Static initializer → loads events once
    static {
        loadEventsFromCSV();
    }

    // ================= CSV LOADING =================
  private static void loadEventsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("event.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                LocalDateTime start = LocalDateTime.parse(
                        parts[3],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                );

                events.add(new Calender_Event(
                        start.toLocalDate(),
                        start.toLocalTime(),
                        parts[1]
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading events from CSV.");
        }
    }
    // ================= HELPER =================
    private static List<Calender_Event> getEventsOnDate(LocalDate date) {
        return events.stream()
                .filter(e -> e.getDate().isEqual(date))
                .sorted((e1, e2) -> e1.getTime().compareTo(e2.getTime()))
                .collect(Collectors.toList());
    }

    // ================= WEEKLY VIEW =================
    public static void print_weekly_list_view(LocalDate startOfWeek) {
        LocalDate sunday = startOfWeek.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        System.out.println("\n// Weekly List View");
        System.out.println("~~~~~ Week of " + sunday + " ~~~~~");

        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = sunday.plusDays(i);
            List<Calender_Event> dailyEvents = getEventsOnDate(currentDate);

            System.out.print(currentDate.format(DAY_DATE_FORMATTER) + ": ");

            if (dailyEvents.isEmpty()) {
                System.out.println("No events");
            } else {
                System.out.println(
                        dailyEvents.stream()
                                .map(Calender_Event::toString)
                                .collect(Collectors.joining(", "))
                );
            }
        }
    }

    // ================= MONTHLY VIEW =================
    public static void printmonthly_calendar_view(YearMonth month) {
        System.out.println("\n// Calendar month view");
        System.out.println(month.format(MONTH_YEAR_FORMATTER));
        System.out.println(" Sun   Mon   Tue   Wed   Thu   Fri   Sat");

        LocalDate firstOfMonth = month.atDay(1);
        int daysInMonth = month.lengthOfMonth();
        int firstDayIndex = firstOfMonth.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayIndex; i++) {
            System.out.print("      ");
        }

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = month.atDay(day);
            boolean hasEvent = !getEventsOnDate(date).isEmpty();

            System.out.printf("%3d%s  ", day, hasEvent ? "*" : " ");

            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                System.out.println();
            }
        }
        System.out.println();
    }

    // ================= YEARLY VIEW =================
    public static void printYearlyCalendarView(int year) {
        System.out.println("\n=============================================");
        System.out.println("           FULL YEAR CALENDAR: " + year);
        System.out.println("=============================================");

        for (int m = 1; m <= 12; m++) {
            printmonthly_calendar_view(YearMonth.of(year, m));
            System.out.println("---------------------------------------------");
        }
    }

    // ================= MENU HANDLER =================
    public static void viewHandling(Scanner scanner) {

        System.out.println("\n--- CALENDAR VIEW ---");
        System.out.println("1. Weekly view");
        System.out.println("2. Monthly view");
        System.out.println("3. Yearly view (2026)");
        System.out.print("Choose view type: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input.");
            return;
        }

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> print_weekly_list_view(LocalDate.now());
            case 2 -> printmonthly_calendar_view(YearMonth.now());
            case 3 -> printYearlyCalendarView(2026);
            default -> System.out.println("Invalid option.");
        }

        System.out.println("\nPress Enter to return to Main Menu...");
        scanner.nextLine(); // consume leftover newline
        scanner.nextLine(); // wait
    }
}

