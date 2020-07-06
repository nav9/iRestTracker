package irest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BigSleepState extends State {
    private DataManager dat = null;
    BigSleepState(DataManager d) {
        dat = d;
    }
    
    @Override
    public void run() {
        dat.fileMgr.writeToTimeFileByOverwriting();//for saving the lastActiveTime 
        log.write("Gonna big sleep");
        try {
            Thread.sleep(dat.getBigSleepTimeInMillis());//go to sleep for 5 minutes
        } catch (InterruptedException ex) {Logger.getLogger(BigSleepState.class.getName()).log(Level.SEVERE, null, ex);}
        if (dat.isScreenLocked(log)) {
            dat.currentState = dat.lockedScreenState;
            log.write("BigSleepstate: screen locked, so going to lock screen state");
        } else {
            dat.currentState = dat.activeState;
            log.write("BigSleepstate: screen not locked, so going to active state");
        }
    }
}