/*
 * MIT
 * Each line should be prefixed with  * 
 */
package irest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SmallSleepState extends State {
    private DataManager dat = null;
    SmallSleepState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(dat.smallSleepTime);//go to sleep for 1 minute
        } catch (InterruptedException ex) {Logger.getLogger(SmallSleepState.class.getName()).log(Level.SEVERE, null, ex);}
        dat.minutesOfRestObtained -= dat.smallSleepTime * dat.strainFactor;//since user has been active
        dat.currentState = dat.writeState;
    }
}
