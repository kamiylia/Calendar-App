import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class EventSearch {

    static class Event {
        int eventId;
        String name;
        String dateStart;

        public Event(int id, String name, String dateStart) {
            this.eventId = id;
            this.name = name;
            this.dateStart = dateStart;
        } 
    }

    public static void main (String [] args) {
        Scanner sc = new Scanner (System.in);

        Event [] events = new Event [10000];
        int eventCount = 0;


        // Example of added events
        events[eventCount++] = new Event(1, "Meeting", "2025-10-05");
        events[eventCount++] = new Event(2, "Doctor Appointment", "2025-10-06");
        events[eventCount++] = new Event(3, "Presentation", "2025-10-09");
        events[eventCount++] = new Event(4, "Group Study", "2025-10-06");
        events[eventCount++] = new Event(5, "Exam", "2025-09-26");
        events[eventCount++] = new Event(6, "Submission FOP", "2026-01-13");
        events[eventCount++] = new Event(7, "Group Discussion", "2025-11-08");

        while (true){
            System.out.println ("\n ------- SEARCH MENU -------");
            System.out.println ("1. Search by date");
            System.out.println ("2. Search by event name");
            System.out.println ("3. Search by date range");
            System.out.println ("4. Exit");
            int choice = sc.nextInt();

            switch (choice){
                case 1 :
                    System.out.println ("Enter date (YYYY-MM-DD)");
                    sc.nextLine(); // consume newline
                    String date = sc.nextLine();
                    searchByDate (events, eventCount, date);
                    break;

                case 2 :
                    System.out.println ("Enter event name : ");
                    sc.nextLine(); // consume newline
                    String name = sc.nextLine ();
                    searchByEvent (events, eventCount, name);
                    break;

                case 3 :
                    System.out.println ("Enter start date (YYYY-MM-DD) : ");
                    sc.nextLine(); // consume newline
                    String startDate = sc.nextLine();
                    System.out.println ("Enter end date (YYYY-MM-DD)");
                    String endDate = sc.nextLine();
                    searchByRange (events, eventCount, startDate, endDate);
                    break;

                case 4 :
                    System.out.println ("Bye!!!");
                    return;

                default :
                    System.out.println ("Invalid input");
            }
        }
    }

    public static void searchByDate (Event [] events, int count, String date) {
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

    public static void searchByEvent (Event [] events, int count, String name) {
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

    public static void searchByRange (Event [] events, int count, String startDate, String endDate) {
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
}