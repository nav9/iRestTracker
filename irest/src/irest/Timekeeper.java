/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

public class Timekeeper {
    private final int checkDelay = 5;//minutes
    private final int seconds = 1000;
    private final int minutes = 60;
    //private final int pauseTime = checkDelay * seconds * minutes;
    private final long pauseTime = checkDelay * seconds;    
    
    public void Timekeeper() {
        
    }
    
    public long GetPauseDelay() {return pauseTime;}
    
}
