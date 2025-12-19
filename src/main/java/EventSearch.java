//KamiliaAhlamTaqi

package FOPfinal.src.main.java;

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

//KamiliaAhlamTaqi
// 12/12/2025
