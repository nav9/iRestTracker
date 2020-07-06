package irest;

public class StartState extends State {
    private DataManager dat = null;
    StartState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        log.write("----------- program begins -----------");
        log.write("StartState: going to big sleep state");
        dat.currentState = dat.bigSleepState;
    }    
    
}