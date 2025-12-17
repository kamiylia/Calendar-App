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

    public static void checkReminders() {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Reminder Settings ===");
        System.out.println("1. 30 minutes before");
        System.out.println("2. 1 hour before");
        System.out.println("3. 1 day before");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        long reminderMinutes;

        switch (choice) {
            case 1:
                reminderMinutes = 30;
                break;
            case 2:
                reminderMinutes = 60;
                break;
            case 3:
                reminderMinutes = 1440; // 1 day
                break;
            default:
                System.out.println("Invalid choice. No reminders shown.");
                return;
        }

        LocalDateTime now = LocalDateTime.now();

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length < 5)
                    continue;

                String title = parts[1];
                LocalDateTime startTime =
                        LocalDateTime.parse(parts[3], FORMATTER);

                long minutesUntilEvent =
                        Duration.between(now, startTime).toMinutes();

                if (minutesUntilEvent > 0 &&
                        minutesUntilEvent <= reminderMinutes) {

                    System.out.println("🔔 Reminder!");
                    System.out.println("Event: " + title);
                    System.out.println("Starts at: " + startTime);
                    System.out.println("In " + minutesUntilEvent + " minutes\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading events for reminders.");
        }
    }
}
