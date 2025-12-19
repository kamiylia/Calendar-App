/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FOPfinal;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class RecurringEvents{
static class Event {
    int eventId;
    String title;
    String recurrentInterval;
    int recurrentTimes;
    String recurrentEndDate;

    public Event(int id, String title) {
        this.eventId = id;
        this.title = title;
        this.recurrentInterval = "0";
        this.recurrentTimes = 0;
        this.recurrentEndDate = "0";
    }
}

    public static void RecurringHandling(int eventID, String date, String endDate, String title) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dt = LocalDateTime.parse(date, formatter);

        int month = dt.getMonthValue() - 1;
        int day   = dt.getDayOfMonth() - 1;
        
        Event events[][]= new Event[12][31];
        Event ev = new Event(eventID,title);
        events[month][day] = ev;
        
        System.out.println("\nRecurring events");
        System.out.println("1- No repetition");
        System.out.println("2- Monthly (same date)");
        System.out.println("3- Weeekly (same day)");
        System.out.println("4- Every X days");
        System.out.print("Choose: ");
            
        int repeat;
        while(true){
        repeat = sc.nextInt();
        if(repeat>=1&&repeat<=5)
            break;
        System.out.println("Invalid choice. Try again :");
        }
        if(repeat>=1&&repeat<=5){
            System.out.println("Choose: ");
            System.out.println("1. Repeats X times");
            System.out.println("2. Ends at specific date");
            int a = sc.nextInt();
            sc.nextLine();
            if(a==1){
                System.out.println("Enter how many times event should be repeated: ");
                ev.recurrentTimes = sc.nextInt();
                ev.recurrentEndDate = "0";
            }
            else if(a==2){
                LocalDate end = LocalDateTime.parse(endDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") ).toLocalDate();
                ev.recurrentEndDate = end.toString(); // store as String
                ev.recurrentTimes = 0;
                sc.nextLine();
            }
        }    
    
        int x=0;
        String interval;
        switch(repeat){
            case 1 : break;
            case 2 :ev.recurrentInterval = "1m";
                    interval = "monthly";
                    recurring(events,ev,month,day,interval,x); break;
            case 3 :ev.recurrentInterval = "1w";
                    interval = "daily";
                    x = 7;
                    recurring(events,ev,month,day,interval,x); break;
            case 4 : System.out.println("Enter how many days interval before next event: ");
                    x = sc.nextInt();
                    interval = "daily";
                    ev.recurrentInterval = x+"d";
                    recurring(events,ev,month,day,interval,x); break;
            default : break;
        }
        sc.nextLine();
        
         try{
         FileWriter fw = new FileWriter("recurrent.csv", true);// opens files in append mode, adds new lines
         fw.write(ev.eventId + "," + ev.recurrentInterval + "," 
             + ev.recurrentTimes + "," + ev.recurrentEndDate + "\n");
         fw.close();
         }
         catch(IOException e){
             System.out.println("Error writing file");
         }
    }

    static void recurring(Event[][] events, Event ev, int month, int day,String interval, int x){
       
    
        LocalDate nextDate = LocalDate.of(2026, month+1, day+1);
        int counter = 0;
        
        while(true){
        
        if(interval.equals("monthly")){
            nextDate = nextDate.plusMonths(1);
        }
        else{
            nextDate = nextDate.plusDays(x);
            }
        
        if(!ev.recurrentEndDate.equals("0")){
            LocalDate end = LocalDate.parse(ev.recurrentEndDate);
            if(nextDate.isAfter(end))
                break;
        }
        else{
            if(counter>=ev.recurrentTimes)
                break;
        }
        counter++;  
        
        int m = nextDate.getMonthValue()-1;
        int d = nextDate.getDayOfMonth()-1;
        
       if (m >= 0 && m < 12 && d >= 0 && d < 31) {
        events[m][d] = new Event(ev.eventId, ev.title);
    } else {
        break;
       }        
     }  
   }
}

