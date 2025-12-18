import src.main.java.fopassignment.*;
import java.util.Scanner;
import java.time.YearMonth;
import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("=== CALENDAR AND SCHEDULER APP ===");
        
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Event Management (Create/View/Update/Delete)");
            System.out.println("2. Event Search");
            System.out.println("3. View Calendar");
            System.out.println("4. Check Reminders");
            System.out.println("5. Backup & Restore");
            System.out.println("6. Exit");
            System.out.print("Choose option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    // Event Management
                    eventManagement(scanner);
                    break;
                    
                case 2:
                    // Event Search functionality
                    searchEvents(scanner);
                    break;
                    
                case 3:
                    // View Calendar
                    viewCalendar();
                    break;
                    
                case 4:
                    // Check Reminders
                    ReminderManager.checkReminders();
                    break;
                    
                case 5:
                    // Backup & Restore
                    backupRestore(scanner);
                    break;
                    
                case 6:
                    running = false;
                    System.out.println("Thank you for using Calendar App. Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void eventManagement(Scanner scanner) {
        boolean eventRunning = true;
        
        while (eventRunning) {
            System.out.println("\n--- EVENT MANAGEMENT ---");
            System.out.println("1. Create Event");
            System.out.println("2. View All Events");
            System.out.println("3. Update Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option (1-5): ");
            
            int eventChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (eventChoice) {
                case 1:
                    EventManager.createEvent(scanner);
                    break;
                case 2:
                    EventManager.viewAllEvents();
                    break;
                case 3:
                    EventManager.updateEvent(scanner);
                    break;
                case 4:
                    EventManager.deleteEvent(scanner);
                    break;
                case 5:
                    eventRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void searchEvents(Scanner scanner) {
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
        System.out.print("Choose search type: ");
        
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
                
            default:
                System.out.println("Invalid search option.");
        }
    }
    
    private static void viewCalendar() {
        ViewCalendar calendar = new ViewCalendar();
        
        System.out.println("\n--- CALENDAR VIEW ---");
        System.out.println("1. Weekly view");
        System.out.println("2. Monthly view");
        System.out.println("3. Yearly view (2026)");
        System.out.println("4. Check reminders");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose view type: ");
        int viewChoice = scanner.nextInt();
        
        switch (viewChoice) {
            case 1:
                LocalDate weekStart = LocalDate.now();
                calendar.print_weekly_list_view(weekStart);
                break;
                
            case 2:
                YearMonth currentMonth = YearMonth.now();
                calendar.printmonthly_calendar_view(currentMonth);
                break;
                
            case 3:
                // 2026 Yearly Calendar View
                printYearlyCalendarView(2026);
                break;
                
            case 4:
                calendar.checkAndPrintReminders();
                break;
                
            default:
                System.out.println("Invalid view option.");
        }
        
        scanner.close();
    }
    
    private static void printYearlyCalendarView(int year) {
        ViewCalendar calendar = new ViewCalendar();
        
        System.out.println("\n" + "=".repeat(45));
        System.out.println("          FULL YEAR CALENDAR: " + year);
        System.out.println("=".repeat(45));

        for (int m = 1; m <= 12; m++) {
            calendar.printmonthly_calendar_view(YearMonth.of(year, m));
            System.out.println("-".repeat(45));
        }
    }
    
    private static void backupRestore(Scanner scanner) {
        System.out.println("\n--- BACKUP & RESTORE ---");
        System.out.println("1. Create backup");
        System.out.println("2. Restore from backup (overwrite)");
        System.out.println("3. Restore from backup (append)");
        System.out.print("Choose option: ");
        
        int backupChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (backupChoice) {
            case 1:
                System.out.print("Enter backup folder name: ");
                String backupFolder = scanner.nextLine();
                BackupManager.backup(backupFolder);
                break;
                
            case 2:
                System.out.print("Enter backup file path: ");
                String restoreFile = scanner.nextLine();
                BackupManager.restore(restoreFile, true);
                break;
                
            case 3:
                System.out.print("Enter backup file path: ");
                String appendFile = scanner.nextLine();
                BackupManager.restore(appendFile, false);
                break;
                
            default:
                System.out.println("Invalid backup option.");
        }
    }
}