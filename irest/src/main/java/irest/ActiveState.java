package irest;

public class ActiveState extends State {
    private DataManager dat = null;    
    ActiveState(DataManager d) {
        dat = d;
    }
    
    @Override
    public void run() {
        long elapsedTime = dat.fileMgr.getElapsedTimeInSeconds();
        if (elapsedTime > dat.bigSleepTime + dat.bufferForProcessingTime) {//means computer was suspended during sleep
            dat.userGotRestBy(elapsedTime);
            log.write("ActiveState: elapsed time greater than bigSleepTime+bufferTime");
            log.write("Active state elapsed time: "+elapsedTime);
        } else {//normal computer active state
            dat.userWasStrainedBy(dat.bigSleepTime);
            dat.userWasActiveForThisMuchTimeToday(dat.bigSleepTime);
            log.write("ActiveState: elapsed time lesser than or equal bigSleepTime+bufferTime");
            log.write("Active state bigSleep time: "+dat.bigSleepTime);
        }
        log.write("Active state: going to writeState");
        dat.currentState = dat.writeState;        
    }
}