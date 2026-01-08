// KamiliaAhlamTaqi

package fopassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class EventSearch {

    private static final String EVENT_FILE = "event.csv";

    private static void searchByDate(String date) {
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String eventDate = parts[3].substring(0, 10);

                if (eventDate.equals(date)) {
                    System.out.println(parts[1] + " on " + eventDate);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading event file.");
            return;
        }

        if (!found) {
            System.out.println("No events found on " + date);
        }
    }

    private static void searchByEvent(String name) {
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[1].equalsIgnoreCase(name)) {
                    String date = parts[3].substring(0, 10);
                    System.out.println(parts[1] + " on " + date);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading event file.");
            return;
        }

        if (!found) {
            System.out.println("No events found with name: " + name);
        }
    }

    private static void searchByRange(String startDate, String endDate) {
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String eventDate = parts[3].substring(0, 10);

                if (eventDate.compareTo(startDate) >= 0 &&
                    eventDate.compareTo(endDate) <= 0) {

                    System.out.println(parts[1] + " on " + eventDate);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading event file.");
            return;
        }

        if (!found) {
            System.out.println("No events found in this date range.");
        }
    }


    public static void searchEvents(Scanner scanner) {

        System.out.println("\n--- EVENT SEARCH ---");
        System.out.println("1. Search by date");
        System.out.println("2. Search by event name");
        System.out.println("3. Search by date range");
        System.out.println("4. Back");
        System.out.print("Choose search type: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter date (YYYY-MM-DD): ");
                searchByDate(scanner.nextLine());
                break;

            case 2:
                System.out.print("Enter event name: ");
                searchByEvent(scanner.nextLine());
                break;

            case 3:
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String start = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String end = scanner.nextLine();
                searchByRange(start, end);
                break;

            default:
                return;
        }
    }
}
// 8/1/26