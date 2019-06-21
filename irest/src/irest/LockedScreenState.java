package irest;

public class LockedScreenState extends State {
    private DataManager dat = null;
    LockedScreenState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        dat.minutesOfRestObtained += dat.bigSleepTime * dat.strainFactor;
        dat.currentState = dat.writeState;
    }    
}
