/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.time.Instant;
import sounds.PlaySound;

public class Timekeeper {
    private final int checkDelay = 5;//minutes
    private final int seconds = 1000;
    private final int minutes = 60;
    private final int pauseTime = checkDelay * seconds * minutes;
    private long lastBeep;
    //private final long pauseTime = checkDelay * seconds;//for testing
    private final DataManager data;
    private final PlaySound sounds;
    
    public Timekeeper() {
        this.lastBeep = 0;
        this.data = new DataManager();
        this.sounds = new PlaySound();
    }
    
    public long GetPauseDelay() {return pauseTime;}
    public void RecordActivity() {//means that the User is currently using the computer
        long timeNow = Instant.now().getEpochSecond();
        this.data.RecordActiveTime(timeNow);
        if (timeNow - this.lastBeep > minutes*minutes) {//play a beep every hour
            sounds.PlayBeep();
            this.lastBeep = timeNow;
        }
    }
}
