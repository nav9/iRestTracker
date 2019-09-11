package irest;

public class WriteAndDecideState extends State {
    private DataManager dat = null;
    WriteAndDecideState(DataManager d) {
        dat = d;
    }
    
    @Override
    public void run() {
        dat.fileMgr.writeToFileByOverwriting();
        if (dat.isScreenLocked(log) == false && dat.userNeedsToBeRemindedToStop()) {
            log.write("WriteState: screen not locked. User needs to be reminded to stop. Going to reminder state");
            dat.currentState = dat.reminderState;
        } else {
            log.write("WriteState: screen locked or user does not need ot be reminded to stop. Going to bigSleepState");
            dat.currentState = dat.bigSleepState;
        }
    }    
    
}
