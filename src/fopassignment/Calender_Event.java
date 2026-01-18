package fopassignment;

import java.time.LocalDate;
import java.time.LocalTime;

public class Calender_Event {

    private LocalDate date;
    private LocalTime time;
    private String title;

    public Calender_Event(LocalDate date, LocalTime time, String title) {
        this.date = date;
        this.time = time;
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time + " " + title;
    }
}
