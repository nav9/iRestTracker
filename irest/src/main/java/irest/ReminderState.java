package irest;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import sounds.PlaySound;

public class ReminderState extends State {
    private DataManager dat = null;
    private final PlaySound sounds = new PlaySound();
    private final double incrementStrainIncreaseRate = 0.1;
    private boolean firstReminder = true;
    
    ReminderState(DataManager d) {
        dat = d;
    }
    
    @Override
    public void run() {
        if (firstReminder == false) {
            dat.strainInfo.rateOfStrainIncrease += incrementStrainIncreaseRate;//if user keeps ignoring reminder, the amount of rest to be taken increases faster
            log.write("ReminderState: Reminding user and rate is now:"+dat.strainInfo.rateOfStrainIncrease);
            firstReminder = false;
        }
//        dat.fileMgr.writeToFileByOverwriting();//necessary only if message dialog showing is enabled
        this.sounds.PlayBeep();
        //Note: Disabling showing the message until a non-blocking alert can be implemented. As of now, the alert pauses program execution until the user closes the dialog box, and this gives SmallSleepState an inaccurate estimate of how long the user has been active since the user was reminded
        try {
            JOptionPane.showMessageDialog(null, "Time to rest");   
        } catch(HeadlessException ex) {log.write(ex.toString());}
        log.write("Reminder state: switching to small sleep state");
        dat.currentState = dat.smallSleepState;
    }  
    
    public void resetFirstReminder() {
        firstReminder = true;
    }
}