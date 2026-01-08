package fopassignment;

import java.io.*;
import java.util.Scanner;
import fopassignment.EventSearch;


public class Eventupdatecreationdelete {
    
    // File paths - using relative paths 
    private static final String EVENT_FILE = "event.csv";
    
    // Main method with menu system
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("Calendar and Scheduler App");
        
        while (running) {//choice
            System.out.println("\n---MAIN MENU---");
            System.out.println("1. Create Event");
            System.out.println("2. View All Events");
            System.out.println("3. Update Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Exit");
            System.out.println("Enter your choice (1-5): ");
            
            int choice = scanner.nextInt(); //enter input
            
            // Selection structure
            switch (choice) {
                case 1:
                    createEvent(scanner);
                    break;
                case 2:
                    viewAllEvents();  // FIXED: Changed from viewAllEvent()
                    break;
                case 3:
                    updateEvent(scanner);
                    break;
                case 4:
                    deleteEvent(scanner);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1-5.");
            }
        }
        scanner.close();
    }
    
    // =================== CREATE EVENT ===================
    public static void createEvent(Scanner scanner) {//create event method
        System.out.println("\n-- CREATE NEW EVENT ---");
        
        int newEventId = generateEventId();
        scanner.nextLine();
        
        System.out.println("Enter event title: ");
        String title = scanner.nextLine();
        
        System.out.println("Enter event description: ");
        String description = scanner.nextLine();
        
        System.out.println("Enter start date/time (yyyy-MM-ddTHH:mm:ss) Example: (2024-12-25T14:30:45): ");  
        String startDateTime = scanner.nextLine();
    
        System.out.println("Enter end date/time (yyyy-MM-ddTHH:mm:ss): "); 
         String endDateTime = scanner.nextLine();
         
        RecurringEvents.RecurringHandling(scanner, newEventId, startDateTime, endDateTime, title);

        // ===== ADD EVENT TO EVENTSEARCH (IN-MEMORY) =====
        EventSearch.events[EventSearch.eventCount++] =
        new EventSearch.Event(newEventId, title, startDateTime);

        String eventEntry = newEventId + "," + title + "," + description + "," + startDateTime + "," + endDateTime;
    
        try {
            FileWriter writer = new FileWriter(EVENT_FILE, true);
            writer.write(eventEntry + "\n");
            writer.close();
            System.out.println("Event successfully created!"); 
        } catch (IOException e) {
            System.out.println("Error saving event: " + e.getMessage());  
        }
    }
    //=========auto genereate event id============
    private static int generateEventId() {
    int id = 1;
    try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            id = Math.max(id, Integer.parseInt(p[0]) + 1);
        }
    } catch (IOException ignored) {}
    return id;
  }
    // =================== VIEW EVENT ===================
public static void viewAllEvents() {//view event method  
    System.out.println("\n-- ALL EVENTS ---");  

    try {
        File file = new File(EVENT_FILE);  

        if (!file.exists()) { //file not found
            System.out.println("No events found.");
            return; // end 
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(EVENT_FILE));  
    
        String line;
        int eventCount = 0;
    
        // Updated header to include Description
        System.out.println("ID | Title | Description | Start Time | End Time");
        System.out.println("--------------------------------------------------------------");
    
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            
            String[] parts = line.split(",");
            
            if (parts.length >= 5) {
                // Add all fields including description (parts[2])
                System.out.print(parts[0] + " | ");
                System.out.print(parts[1] + " | ");
                System.out.print(parts[2] + " | ");  // ADDED: Display description
                System.out.print(parts[3] + " | ");
                System.out.println(parts[4]);
                eventCount++;
            }
        }
        
        reader.close();
        
        if (eventCount == 0) {
            System.out.println("No events found.");
        } else {
            System.out.println("Total events: " + eventCount);
        }
        
    } catch (IOException e) {
        System.out.println("Error reading events: " + e.getMessage());
    }
}
    // =================== UPDATE EVENT ===================
    public static void updateEvent(Scanner scanner) {
        System.out.println("\n=== UPDATE EVENT ===");
        viewAllEvents();
        
        System.out.println("Enter Event ID to update: ");
        int eventId = scanner.nextInt();
        scanner.nextLine();
        
        // Check if ID exists before updating
        if (!eventIdExists(eventId)) {
            System.out.println("Error: Event ID " + eventId + " does not exist!");
            return;  // Exit the method
        }
        
        try {
            File inputFile = new File(EVENT_FILE);
            File tempFile = new File("temp.csv");
            
            BufferedReader reader = new BufferedReader(new FileReader(EVENT_FILE));
            FileWriter writer = new FileWriter(tempFile);
            
            String line;
            boolean found = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 5) {
                    int currentId = Integer.parseInt(parts[0]);
                    
                    if (currentId == eventId) {
                        found = true;
                        System.out.println("Current event: " + line);
                        
                        System.out.println("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        if (newTitle.isEmpty()) newTitle = parts[1];
                        
                        System.out.println("Enter new description: ");
                        String newDescription = scanner.nextLine();
                        if (newDescription.isEmpty()) newDescription = parts[2];
                        
                        System.out.println("Enter new start time (yyyy-MM-dd HH:mm): ");
                        String newStart = scanner.nextLine();
                        if (newStart.isEmpty()) newStart = parts[3];
                        
                        System.out.println("Enter new end time (yyyy-MM-dd HH:mm): ");
                        String newEnd = scanner.nextLine();
                        if (newEnd.isEmpty()) newEnd = parts[4];
                        
                        String updatedLine = currentId + "," + newTitle + "," + newDescription + "," + newStart + "," + newEnd;
                        writer.write(updatedLine + "\n");
                        System.out.println("Event updated successfully!");
                    } else {
                        writer.write(line + "\n");
                    }
                }
            }
            
            reader.close();
            writer.close();
            
            if (found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
            } else {
                tempFile.delete();
                System.out.println("Event with ID " + eventId + " not found.");
            }
            
        } catch (IOException e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }
    
    // =================== DELETE EVENT ===================
    public static void deleteEvent(Scanner scanner) {
        System.out.println("\n=== DELETE EVENT ===");
        viewAllEvents();
        
        System.out.print("Enter Event ID to delete: ");
        int eventId = scanner.nextInt();
        
        // Check if ID exists before deleting
        if (!eventIdExists(eventId)) {
            System.out.println("Error: Event ID " + eventId + " does not exist!");
            return;  // Exit the method
        }
        
        try {
            File inputFile = new File(EVENT_FILE);
            File tempFile = new File("temp.csv");
            
            BufferedReader reader = new BufferedReader(new FileReader(EVENT_FILE));
            FileWriter writer = new FileWriter(tempFile);
            
            String line;
            boolean found = false;
            int deleteCount = 0;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 5) {
                    int currentId = Integer.parseInt(parts[0]);
                    
                    if (currentId == eventId) {
                        found = true;
                        deleteCount++;
                        System.out.println("Deleted event: " + parts[1]);
                    } else {
                        writer.write(line + "\n");
                    }
                }
            }
            
            reader.close();
            writer.close();
            
            if (found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("Deleted " + deleteCount + " event(s) successfully!");
            } else {
                tempFile.delete();
                System.out.println("Event with ID " + eventId + " not found.");
            }
            
        } catch (IOException e) {
            System.out.println("Error deleting event: " + e.getMessage());
        }
    }
    
    // =================== HELPER METHOD: Check if Event ID exists ===================
    private static boolean eventIdExists(int eventId) {
        try {
            File file = new File(EVENT_FILE);
            
            if (!file.exists()) {
                return false;  // File doesn't exist, so ID doesn't exist
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(EVENT_FILE));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 1) {
                    try {
                        int currentId = Integer.parseInt(parts[0]);
                        if (currentId == eventId) {
                            reader.close();
                            return true;  // ID found
                        }
                    } catch (NumberFormatException e) {
                        // Skip lines with invalid IDs
                    }
                }
            }
            
            reader.close();
            
        } catch (IOException e) {
            System.out.println("Error checking ID: " + e.getMessage());
        }
        
        return false;  // ID not found
    }

    public static void eventManagement(Scanner scanner) {
        boolean eventRunning = true;
        
        while (eventRunning) {
            System.out.println("\n--- EVENT MANAGEMENT ---");
            System.out.println("1. Create Event");
            System.out.println("2. View All Events");
            System.out.println("3. Update Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option (1-5): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            
            int eventChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (eventChoice) {
                case 1:
                Eventupdatecreationdelete.createEvent(scanner);
                    break;
                case 2:
                Eventupdatecreationdelete.viewAllEvents();
                    break;
                case 3:
                Eventupdatecreationdelete.updateEvent(scanner);
                    break;
                case 4:
                Eventupdatecreationdelete.deleteEvent(scanner);
                    break;
                case 5:
                    eventRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }    
}

