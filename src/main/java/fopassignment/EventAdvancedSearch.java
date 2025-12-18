package src.main.java.fopassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventAdvancedSearch {

    private static final String EVENT_FILE = "event.csv";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // ========= MENU =========
    public static void advancedSearchMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Advanced Event Search & Filter ===");
        System.out.println("1. Search event by title");
        System.out.println("2. Filter events by month");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (choice == 1) {
            System.out.print("Enter title keyword: ");
            searchByTitle(sc.nextLine());
        } 
        else if (choice == 2) {
            System.out.print("Enter year (e.g. 2025): ");
            int year = sc.nextInt();

            System.out.print("Enter month (1-12): ");
            int month = sc.nextInt();

            filterByMonth(year, month);
        } 
        else {
            System.out.println("Invalid choice.");
        }
        
        sc.close();
    }

    // ========= SEARCH BY TITLE =========
    private static void searchByTitle(String keyword) {

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {

            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");
                if (p.length < 5) continue;

                if (p[1].toLowerCase().contains(keyword.toLowerCase())) {
                    printEvent(p);
                    found = true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading event file.");
        }

        if (!found)
            System.out.println("No matching events found.");
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
            System.out.println("Error filtering events.");
        }

        if (!found)
            System.out.println("No events found for this month.");
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
