package fopassignment;

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
            System.out.print("Choose option (1–6): ");
    
            String input = scanner.nextLine();
            int choice;
    
            try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
    
            switch (choice) {
                case 1:
                    Eventupdatecreationdelete.eventManagement(scanner);
                    break;
    
                case 2:
                    EventSearch.searchEvents(scanner);
                    break;
    
                case 3:
                    ViewCalendar.viewHandling(scanner);
                    break;
    
                case 4:
                    ReminderManager.checkReminders(scanner);   // IMPORTANT
                    break;
    
                case 5:
                    BackupManager.backupRestore(scanner);
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
}    