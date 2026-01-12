package fopassignment;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ConflictDetector {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static boolean hasConflict(String newStartStr, String newEndStr) {

        LocalDateTime newStart, newEnd;

        try {
            newStart = LocalDateTime.parse(newStartStr, FORMAT);
            newEnd   = LocalDateTime.parse(newEndStr, FORMAT);
        } catch (Exception e) {
            System.out.println("❌ Invalid date format. Use: yyyy-MM-ddTHH:mm:ss");
            return true;   // block creation if input is broken
        }

        // ---------- CHECK NORMAL EVENTS ----------
        try (BufferedReader br = new BufferedReader(new FileReader("event.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 5) continue;

                try {
                    LocalDateTime start = LocalDateTime.parse(p[3], FORMAT);
                    LocalDateTime end   = LocalDateTime.parse(p[4], FORMAT);

                    if (overlap(newStart, newEnd, start, end))
                        return true;
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("Cannot read event.csv");
        }

        // ---------- CHECK RECURRING EVENTS ----------
        try (BufferedReader br = new BufferedReader(new FileReader("recurrent.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] r = line.split(",");
                if (r.length < 4) continue;

                int eventId = Integer.parseInt(r[0]);
                String interval = r[1];
                int times = Integer.parseInt(r[2]);
                String endDateStr = r[3];

                // find original event
                LocalDateTime baseStart = null;
                LocalDateTime baseEnd = null;

                try (BufferedReader br2 = new BufferedReader(new FileReader("event.csv"))) {
                    String e;
                    while ((e = br2.readLine()) != null) {
                        String[] p = e.split(",");
                        if (Integer.parseInt(p[0]) == eventId) {
                            baseStart = LocalDateTime.parse(p[3], FORMAT);
                            baseEnd   = LocalDateTime.parse(p[4], FORMAT);
                            break;
                        }
                    }
                }

                if (baseStart == null) continue;

                LocalDateTime curStart = baseStart;
                LocalDateTime curEnd = baseEnd;
                int count = 0;

                while (true) {
                    if (times > 0 && count >= times) break;

                    if (!endDateStr.equals("0")) {
                        LocalDate endDate = LocalDate.parse(endDateStr);
                        if (curStart.toLocalDate().isAfter(endDate)) break;
                    }

                    if (overlap(newStart, newEnd, curStart, curEnd))
                        return true;

                    if (interval.equals("1w")) {
                        curStart = curStart.plusWeeks(1);
                        curEnd   = curEnd.plusWeeks(1);
                    } else if (interval.equals("1m")) {
                        curStart = curStart.plusMonths(1);
                        curEnd   = curEnd.plusMonths(1);
                    } else if (interval.endsWith("d")) {
                        int d = Integer.parseInt(interval.replace("d",""));
                        curStart = curStart.plusDays(d);
                        curEnd   = curEnd.plusDays(d);
                    } else break;

                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot read recurrent.csv");
        }

        return false;
    }

    private static boolean overlap(LocalDateTime aStart, LocalDateTime aEnd,
                                   LocalDateTime bStart, LocalDateTime bEnd) {
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }
}
