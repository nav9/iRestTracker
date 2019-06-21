package irest;

public class ActiveState extends State {
    private DataManager dat = null;
    ActiveState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        dat.minutesOfRestObtained -= dat.bigSleepTime * dat.strainFactor;
        dat.currentState = dat.writeState;        
    }
}
