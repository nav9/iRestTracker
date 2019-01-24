/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.time.Instant;
import sounds.PlaySound;

public class Timekeeper {
    private final int minutesToPause = 1;//program goes to sleep for these many minutes
    private final int writeToFileInterval = 5;//program writes to file at this interval (in minutes)
    private final int toMilli = 1000;
    private final int toSecond = 60;
    private final int toHours = 60 * this.toSecond;
    private final int pauseDelay = this.minutesToPause * this.toMilli * this.toSecond;//Unit: milliseconds
    private final int writeDelay = this.writeToFileInterval * this.toMilli * this.toSecond;//Unit: milliseconds
    //private final int pauseDelay = 10000;//for testing
    private final long beepDelay = 1 * this.toHours;//Unit: seconds. 1 hour beeps
    private final long sufficientRestTime = 30 * this.toSecond;//Unit: seconds. 30 minute rest time is considered sufficient
    private final long reminderInterval = 5 * this.toSecond;//reminds User to rest
    private boolean reminderActive;
    private long lastBeep;    
    private long lastWrite;    
    private final DataManager data;
    private final PlaySound sounds;
    //TODO: move intervals of data and sound to respective classes if necessary
    
    public Timekeeper() {
        this.reminderActive = false;
        this.lastBeep = 0;
        this.lastWrite = 0;
        this.data = new DataManager();
        this.sounds = new PlaySound();
    }
    
    public long GetPauseDelay() {return pauseDelay;}
    
    public void RecordAndNotifyUserIfTimeElapsed() {//means that the User is currently using the computer
        long timeNow = Instant.now().getEpochSecond();
        
        if (timeNow - this.lastWrite >= this.writeToFileInterval) {//write to file if interval elapsed
            this.data.RecordActiveTime(timeNow);
            this.lastWrite = timeNow;
        }            
        
        if (timeNow - this.lastBeep >= this.beepDelay) {//play sound if an hour elapsed
            this.sounds.PlayBeep();
            this.reminderActive = true;
            this.lastBeep = timeNow;
        }
        
        if (timeNow - this.lastBeep >= this.reminderInterval && this.reminderActive) {//play sound if an hour elapsed and User is still using computer
            this.sounds.PlayBeep();
            this.lastBeep = timeNow;
            //reminder is deactivated in ResetBeepWarningIfSufficientRestObtained()
        }        
    }
    
    public void ResetBeepWarningIfSufficientRestObtained() {
        long timeNow = Instant.now().getEpochSecond();
        if (timeNow - this.lastBeep >= this.sufficientRestTime) {
            this.lastBeep = timeNow;
            this.reminderActive = false;
        }        
    }
}
