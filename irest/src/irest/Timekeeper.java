/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.time.Instant;

public class Timekeeper {
    private final int checkDelay = 5;//minutes
    private final int seconds = 1000;
    private final int minutes = 60;
    private final int pauseTime = checkDelay * seconds * minutes;
    //private final long pauseTime = checkDelay * seconds;//for testing
    private final DataManager data;
    
    public Timekeeper() {
        data = new DataManager();
    }
    
    public long GetPauseDelay() {return pauseTime;}
    public void RecordActivity() {//means that the User is currently using the computer
        data.RecordActiveTime(Instant.now().getEpochSecond());
    }
}
