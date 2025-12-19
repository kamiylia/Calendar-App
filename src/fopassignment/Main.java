package src.fopassignment;

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
            System.out.println("4. Recurring Events");
            System.out.println("5. Check Reminders");
            System.out.println("6. Backup & Restore");
            System.out.println("7. Exit");
            System.out.print("Choose option (1-7): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    // Event Management
                    Eventupdatecreationdelete.eventManagement(scanner);;
                    break;
                    
                case 2:
                    // Event Search functionality
                    EventSearch.searchEvents(scanner);
                    break;
                    
                case 3:
                    // View Calendar
                    ViewCalendar.viewHandling(scanner);
                    break;
                    
                case 4:
                    // Recurring Events
                    RecurringEvents.recurringEventsMenu(scanner);
                    break;
                    
                case 5:
                    // Check Reminders
                    ReminderManager.checkReminders();
                    break;
                    
                case 6:
                    // Backup & Restore
                    BackupManager.backupRestore(scanner);
                    break;
                    
                case 7:
                    running = false;
                    System.out.println("Thank you for using Calendar App. Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}