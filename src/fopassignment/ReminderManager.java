package fopassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ReminderManager {

    private static final String EVENT_FILE = "event.csv";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static void checkReminders(Scanner sc) {

        System.out.println("\n=== Reminder Settings ===");
        System.out.println("1. 30 minutes before");
        System.out.println("2. 1 hour before");
        System.out.println("3. 1 day before");
        System.out.print("Choose option: ");

        String input = sc.nextLine();
        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        long reminderMinutes;

        switch (choice) {
            case 1:
                reminderMinutes = 30;
                break;
            case 2:
                reminderMinutes = 60;
                break;
            case 3:
                reminderMinutes = 1440;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String title = parts[1];
                LocalDateTime startTime =
                        LocalDateTime.parse(parts[3], FORMATTER);

                long minutesUntilEvent =
                        Duration.between(now, startTime).toMinutes();

                if (minutesUntilEvent > 0 &&
                        minutesUntilEvent <= reminderMinutes) {

                    found = true;
                    System.out.println("\n🔔 Reminder!");
                    System.out.println("Event: " + title);
                    System.out.println("Starts at: " + startTime);
                    System.out.println("In " + minutesUntilEvent + " minutes");
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading events for reminders.");
        }

        if (!found) {
            System.out.println("No upcoming events in selected reminder window.");
        }
    }
}
