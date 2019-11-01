package irest;

public class TrackerInfo {
    private String todaysDate;
    private double totalTimeActiveToday;
    
    TrackerInfo() {        
    }
    
    public String getAsStringForWriting() {
        return this.todaysDate + "," + this.totalTimeActiveToday;
    } 
    
    public void setFromString(String info) {
        String[] data = info.split(",");
        int i = 0;
        this.todaysDate = data[i++];
        this.totalTimeActiveToday = Double.parseDouble(data[i++]);
    }    

    public void setDate(final String dat) {
        this.todaysDate = dat;
    }
    
    public String getDate() {
        return this.todaysDate;
    }
    
    public double getTime() {
        return this.totalTimeActiveToday;
    }
    
    public double addTheAccumulatedTime(final double tim) {
        return tim + this.totalTimeActiveToday;
    }
    
    public void setTimeActive(final double tim) {
        this.totalTimeActiveToday = tim;
    }
    
    public boolean isSameDay(final String previousDate) {
        return previousDate.equals(this.todaysDate);
    }
    
}
