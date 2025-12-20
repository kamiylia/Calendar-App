//KamiliaAhlamTaqi

package fopassignment;

import java.util.Scanner;

public class EventSearch {

    public static class Event {
        int eventId;
        String name;
        String dateStart;

        public Event(int id, String name, String dateStart) {
            this.eventId = id;
            this.name = name;
            this.dateStart = dateStart;
        } 
    }

    private static void searchByDate (Event [] events, int count, String date) {
        System.out.println ("Event on "+date+" : \n");
        boolean found = false;

        for (int i = 0; i<count; i++){
            if (events[i].dateStart.equals(date)) {
                System.out.println (events[i].name);
                found = true;
            }
        }
        if (!found)
            System.out.println ("No events found on this date : " + date);
    }

    private static void searchByEvent (Event [] events, int count, String name) {
        System.out.println ("Events with name '"+name+"' : ");
        boolean found = false;

        for (int i = 0; i<count; i++){
            if (events[i].name.equalsIgnoreCase(name)) {
                System.out.println (events[i].name + " on " + events[i].dateStart);
                found = true;
            }
        }
        if (!found)
            System.out.println ("No events found with name : " + name);
    }

    private static void searchByRange (Event [] events, int count, String startDate, String endDate) {
        System.out.println ("Events between "+startDate+" and "+endDate+" : ");
        boolean found = false;

        for (int i = 0; i<count; i++){
            if (events[i].dateStart.compareTo(startDate) >= 0 && events[i].dateStart.compareTo(endDate) <= 0) {
                System.out.println (events[i].name + " on " + events[i].dateStart);
                found = true;
            }
        }
        if (!found)
            System.out.println ("No events found in date range");
    }

    public static void searchEvents(Scanner scanner) {
        EventSearch.Event[] events = new EventSearch.Event[100];
        int eventCount = 0;
        
        // Sample events for demonstration
        events[eventCount++] = new EventSearch.Event(1, "Meeting", "2025-01-15");
        events[eventCount++] = new EventSearch.Event(2, "Doctor Appointment", "2025-01-16");
        events[eventCount++] = new EventSearch.Event(3, "Project Deadline", "2025-01-20");
        
        System.out.println("\n--- EVENT SEARCH ---");
        System.out.println("1. Search by date");
        System.out.println("2. Search by event name");
        System.out.println("3. Search by date range");
        System.out.println("4. Advanced Search (from CSV file)");
        System.out.print("Choose search type: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
        
        int searchChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (searchChoice) {
            case 1:
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                EventSearch.searchByDate(events, eventCount, date);
                break;
                
            case 2:
                System.out.print("Enter event name: ");
                String name = scanner.nextLine();
                EventSearch.searchByEvent(events, eventCount, name);
                break;
                
            case 3:
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                EventSearch.searchByRange(events, eventCount, startDate, endDate);
                break;
                
            case 4:
                // Advanced Search from CSV file
                EventAdvancedSearch.advancedSearchMenu();
                break;
                
            default:
                System.out.println("Invalid search option.");
        }
    }
}

//KamiliaAhlamTaqi
// 12/12/2025
