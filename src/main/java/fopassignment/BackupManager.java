/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package backupmanager;

import java.io.*;
import java.nio.file.*;

public class BackupManager {

    private static final String EVENT_FILE = "event.csv";
    private static final String RECURRENT_FILE = "recurrent.csv";
    private static final String BACKUP_FILE = "backup.txt";

    // ===== BACKUP FUNCTION =====
    public static void backup(String backupLocation) {
        try {
            // Create the backup file path
            File backupFile = new File(backupLocation, BACKUP_FILE);

            BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile));

            // Write event.csv
            writer.write("[EVENT]\n");
            writer.write(Files.readString(Path.of(EVENT_FILE)));
            writer.write("\n");

            // Write recurrent.csv
            writer.write("[RECURRENT]\n");
            writer.write(Files.readString(Path.of(RECURRENT_FILE)));
            writer.write("\n");

            writer.close();

            System.out.println("Backup completed at: " + backupFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }

    // ===== RESTORE FUNCTION =====
    public static void restore(String backupFilePath, boolean overwriteExisting) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(backupFilePath));

            String line;
            StringBuilder eventData = new StringBuilder();
            StringBuilder recurrentData = new StringBuilder();

            String mode = "";  // keeps track whether reading EVENT or RECURRENT

            while ((line = reader.readLine()) != null) {
                if (line.equals("[EVENT]")) {
                    mode = "EVENT";
                    continue;
                } else if (line.equals("[RECURRENT]")) {
                    mode = "RECURRENT";
                    continue;
                }

                if (mode.equals("EVENT")) {
                    eventData.append(line).append("\n");
                } else if (mode.equals("RECURRENT")) {
                    recurrentData.append(line).append("\n");
                }
            }
            reader.close();

            // overwrite or append logic
            if (overwriteExisting) {
                writeToFile(EVENT_FILE, eventData.toString(), false);
                writeToFile(RECURRENT_FILE, recurrentData.toString(), false);
                System.out.println("Restore completed (overwrite mode).");
            } else {
                writeToFile(EVENT_FILE, eventData.toString(), true);
                writeToFile(RECURRENT_FILE, recurrentData.toString(), true);
                System.out.println("Restore completed (append mode).");
            }

        } catch (IOException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }

    // helper method to write data
    private static void writeToFile(String filename, String content, boolean append) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append));
        writer.write(content);
        writer.close();
    }
}

public class Main {
    public static void main(String[] args) {

        // Example: backup into folder "backup_folder"
        BackupManager.backup("backup_folder");

        // Example: restore from backup (overwrite)
        BackupManager.restore("backup_folder/backup.txt", true);

        // Example: restore from backup (append)
        // BackupManager.restore("backup_folder/backup.txt", false);
    }
}

