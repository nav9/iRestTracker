/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sounds.PlaySound;

public class Timekeeper {
    private final int toMilli = 1000;
    private final int toSecond = 60;
    private final int toHours = 60 * this.toSecond;
    private final int pauseDelay = 1 * this.toSecond * this.toMilli;//Unit: minutes to milliseconds. Thread sleep needs it in this unit
    private final int writeDelay = 5 * this.toSecond;//Unit: minutes to seconds
    private final long beepDelay = 1 * this.toHours;//Unit: hours to seconds. 1 hour beeps
    private final long sufficientRestTime = 30 * this.toSecond;//Unit: seconds. 30 minute rest time is considered sufficient
    private final long reminderInterval = 1 * this.toSecond;//Unit: minutes to seconds. Reminds User to rest
    private boolean reminderActive;
    private long lastBeep;    
    private long lastWrite;    
    private final DataManager data;
    private final PlaySound sounds;
    //TODO: move intervals of data and sound to respective classes if necessary
    
    public Timekeeper() {
        this.data = new DataManager();
        this.sounds = new PlaySound();
        this.reminderActive = false;
        this.lastBeep = this.data.GetLastRemindedTime();
        this.lastWrite = this.data.GetLastRecordedTime();
        System.out.println("Last Beep: "+this.lastBeep);
        System.out.println("Last Write: "+this.lastWrite);
    }
    
    public long GetPauseDelay() {return pauseDelay;}
        
    public void RecordAndNotifyUserIfTimeElapsed() {//means that the User is currently using the computer
        long timeNow = Instant.now().getEpochSecond();
        
        if (timeNow - this.lastWrite >= this.writeDelay) {//write to file if interval elapsed
            this.data.RecordActiveTime(timeNow);
            this.lastWrite = timeNow;            
        }            
        
        if (timeNow - this.lastBeep >= this.beepDelay) {//notify User if an hour elapsed
            RemindUserToStop(timeNow, "Time to rest");
            this.reminderActive = true;            
        }
        
        if (timeNow - this.lastBeep >= this.reminderInterval && this.reminderActive) {//play sound if an hour elapsed and User is still using computer
            RemindUserToStop(timeNow, "Come on...rest");            
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
    
    private void RemindUserToStop(final long timeNow, final String message) {
        this.lastBeep = timeNow;
        this.sounds.PlayBeep();
        try {
            JOptionPane.showMessageDialog(null, message);   
        } catch(Exception ex) {{Logger.getLogger(Timekeeper.class.getName()).log(Level.WARNING, null, ex);}}
        this.data.RecordTimeRemindedUser(timeNow);
    }
    
}
