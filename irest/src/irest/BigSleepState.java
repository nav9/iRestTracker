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
        try {
            Thread.sleep(dat.bigSleepTime);//go to sleep for 5 minutes
        } catch (InterruptedException ex) {Logger.getLogger(BigSleepState.class.getName()).log(Level.SEVERE, null, ex);}
        if (dat.isScreenLocked()) {
            dat.currentState = dat.lockedScreenState;
        } else {
            dat.currentState = dat.activeState;
        }
    }
}
