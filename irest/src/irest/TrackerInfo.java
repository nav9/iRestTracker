package irest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrackerInfo {
    private String todaysDate = this.getTodaysDate();
    private double totalTimeActiveToday = 0.0;
    
    TrackerInfo() {        
    }
    
    public String getAsStringForWriting() {
        //System.out.println("getting string for writing: "+this.todaysDate + "," + this.totalTimeActiveToday);
        String output = this.todaysDate + "," + this.totalTimeActiveToday;
        return output;
    } 
    
    public void setFromString(String info) {
        //System.out.println("Loaded string: ["+info+"]");
        String[] data = info.split(",");
        int i = 0;
        this.todaysDate = data[i++];
        this.totalTimeActiveToday = Double.parseDouble(data[i++]);
        //System.out.println("date loaded: "+this.todaysDate);
        //System.out.println("time active today total: "+this.totalTimeActiveToday);
    }    

    public void setDate(final String dat) {
        this.todaysDate = dat;
    }

    public void setDateAsTodaysDate() {
        this.todaysDate = this.getDate();
    }
    
    public String getDate() {
        return this.todaysDate;
    }
    
    public double getTime() {
        return this.totalTimeActiveToday;
    }
    
    public void addTheAccumulatedTime(final double tim) {
        //System.out.println("adding accumulated time "+tim+"+"+this.totalTimeActiveToday);
        this.totalTimeActiveToday = tim + this.totalTimeActiveToday;
    }
    
    public void setTimeActive(final double tim) {
        this.totalTimeActiveToday = tim;
    }
    
    public boolean isSameDay(final String previousDate) {
        //System.out.println("Is "+previousDate+" = "+this.todaysDate);
        return previousDate.equals(this.todaysDate);
    }
    
    public String getTodaysDate() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());            
    }    
    
}
