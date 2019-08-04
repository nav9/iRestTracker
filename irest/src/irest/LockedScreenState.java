package irest;

public class LockedScreenState extends State {
    private DataManager dat = null;
    LockedScreenState(DataManager d) {
        dat = d;
    }
    
    @Override
    public void run() {
        long elapsedTime = dat.fileMgr.getElapsedTimeInSeconds();
        if (elapsedTime > dat.bigSleepTime + dat.bufferForProcessingTime) {//means computer was suspended during sleep and wakes to a locked screen state
            dat.userGotRestBy(elapsedTime);
            log.write("Locked scr state: elapsed time greater than big sleep time: "+elapsedTime);
        } else {//normal computer lock screen state
            dat.userGotRestBy(dat.bigSleepTime);
            log.write("Locked scr state: elapsed time lesser than or equal to big sleep time: "+dat.bigSleepTime);
        }
        log.write("Locked scr state: switching to write state ");
        dat.currentState = dat.writeState;
    }    
}
