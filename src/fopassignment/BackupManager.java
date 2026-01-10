package fopassignment; 

import java.io.*; 
import java.nio.file.*; 
import java.util.Scanner; 
public class BackupManager { 
    private static final String EVENT_FILE = "event.csv"; 
    private static final String RECURRENT_FILE = "recurrent.csv"; 
    private static final String BACKUP_FILE = "backup.txt"; // ===== BACKUP FUNCTION ===== 
    
public static void backup(String backupLocation) {
    try {
        File backupDir = new File(backupLocation);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File backupFile = new File(backupDir, BACKUP_FILE);
        BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile));

        writer.write("[EVENT]\n");
        if (Files.exists(Path.of(EVENT_FILE))) {
            writer.write(Files.readString(Path.of(EVENT_FILE)));
        } else {
            writer.write("No event data found.\n");
        }

        writer.write("\n[RECURRENT]\n");
        if (Files.exists(Path.of(RECURRENT_FILE))) {
            writer.write(Files.readString(Path.of(RECURRENT_FILE)));
        } else {
            writer.write("No recurrent data found.\n");
        }

        writer.close();
        System.out.println("Backup completed at: " + backupFile.getAbsolutePath());

    } catch (IOException e) {
        System.out.println("Error during backup: " + e.getMessage());
    }
}


    // ===== RESTORE FUNCTION =====     
    public static void restore(String backupFilePath, boolean overwriteExisting) { 
        try { BufferedReader reader = new BufferedReader(new FileReader(backupFilePath)); 
            String line; 
            StringBuilder eventData = new StringBuilder(); 
            StringBuilder recurrentData = new StringBuilder(); 
            String mode = ""; // keeps track whether reading EVENT or RECURRENT 

            while ((line = reader.readLine()) != null) { 
                if (line.equals("[EVENT]")) { 
                    mode = "EVENT"; continue; 
                } else if (line.equals("[RECURRENT]")) { 
                    mode = "RECURRENT"; continue; } 
                    if (mode.equals("EVENT")) { 
                        eventData.append(line).append("\n"); 
                    }else if (mode.equals("RECURRENT")) { 
                        recurrentData.append(line).append("\n"); } } 
            reader.close(); 
            
            // overwrite or append logic 
            if (overwriteExisting) { 
                writeToFile(EVENT_FILE, eventData.toString(), false); 
                writeToFile(RECURRENT_FILE, recurrentData.toString(), false); 
                System.out.println("Restore completed (overwrite mode)."); 
            } else { writeToFile(EVENT_FILE, eventData.toString(), true); 
                writeToFile(RECURRENT_FILE, recurrentData.toString(), true); 
                System.out.println("Restore completed (append mode)."); }
             } catch (IOException e) { 
                System.out.println("Error during restore: " + e.getMessage()); 
            } 
        } 
        // helper method to write data             
        private static void writeToFile(String filename, String content, boolean append) throws IOException { 
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append)); 
            writer.write(content); 
            writer.close(); } 

        public static void backupRestore(Scanner scanner) { 
            System.out.println("\n--- BACKUP & RESTORE ---"); 
            System.out.println("1. Create backup"); 
            System.out.println("2. Restore from backup (overwrite)"); 
            System.out.println("3. Restore from backup (append)"); 
            System.out.print("Choose option: "); 

            if (!scanner.hasNextInt()) { 
                System.out.println("Invalid input. Please enter a number."); 
                return; 
            } 
            int backupChoice = scanner.nextInt(); 
            scanner.nextLine(); 
            switch (backupChoice) { 
                case 1: System.out.print("Enter backup folder name: "); 
                String backupFolder = scanner.nextLine(); 
                BackupManager.backup(backupFolder); 
                break; 
                case 2: System.out.print("Enter backup file path: "); 
                String restoreFile = scanner.nextLine(); 
                BackupManager.restore(restoreFile, true); 
                break; 
                case 3: System.out.print("Enter backup file path: "); 
                String appendFile = scanner.nextLine(); 
                BackupManager.restore(appendFile, false); 
                break; 
                default: System.out.println("Invalid backup option."); 
            } 
         } 
    }