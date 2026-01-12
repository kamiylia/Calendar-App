package fopassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventAdvancedSearch {

    private static final String EVENT_FILE = "event.csv";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // ========= MONTH FILTER MENU =========
    public static void advancedSearchMenu(Scanner sc) {

        System.out.println("\n=== Filter Events By Month ===");

        int year;
        int month;

        try {
            System.out.print("Enter year (e.g. 2025): ");
            year = Integer.parseInt(sc.nextLine());

            System.out.print("Enter month (1–12): ");
            month = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter numbers only.");
            return;
        }

        filterByMonth(year, month);
    }

    // ========= FILTER BY MONTH =========
    private static void filterByMonth(int year, int month) {

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {

            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");
                if (p.length < 5) continue;

                LocalDateTime start =
                        LocalDateTime.parse(p[3], FORMATTER);

                if (start.getYear() == year &&
                        start.getMonthValue() == month) {

                    printEvent(p);
                    found = true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading event file.");
        }

        if (!found) {
            System.out.println("No events found for this month.");
        }
    }

    // ========= PRINT EVENT =========
    private static void printEvent(String[] p) {

        System.out.println("--------------------");
        System.out.println("Event ID : " + p[0]);
        System.out.println("Title    : " + p[1]);
        System.out.println("Start    : " + p[3]);
        System.out.println("End      : " + p[4]);
    }
}
