package irest;

import linux.LinuxScreenLockDetect;

public class DataManager {
    public State currentState = new State();
    public BigSleepState bigSleepState = null;
    public SmallSleepState smallSleepState = null;
    public LockedScreenState lockedScreenState = null;
    public ActiveState activeState = null;
    public StartState startState = null;
    public WriteAndDecideState writeState = null;
    public ReminderState reminderState = null;

    private LockScreenDetector lockScreen = new LockScreenDetector();//Default functionality is to not detect lock screen. Can be overridden based on OS type, where the overriding class can detect screen locks
    private final int toMilli = 1000;
    private final int toSecond = 60;
    private final int ONE_HOUR = 60 * toSecond;
    private final int TWENTY_MINUTES = 20 * toSecond;
    private final int TWO_MINUTES = 2 * toSecond;
    private final int ONE_MINUTE = 1 * toSecond;
    private final int maxTolerableStrainTime = TWENTY_MINUTES;//Unit: seconds
    private final int maxStrainAllowedPerDay = ONE_HOUR * 6;//Six hours. Unit: seconds. 
    public final long bufferForProcessingTime = 5;//Unit: seconds
    public final int bigSleepTime = TWO_MINUTES * toSecond;//Unit: seconds
    public final int smallSleepTime = ONE_MINUTE * toSecond;//Unit: seconds
    
    public StrainTracker strainInfo = null;
    public FileManager fileMgr = null;
    
    DataManager() {
        strainInfo = new StrainTracker(currentState.log);
        fileMgr = new FileManager(strainInfo, currentState.log);
    
        String osName = System.getProperty("os.name");
        //---Assign lock screen detection class based on OS type
        if (osName.equals("Linux")) {
            lockScreen = new LinuxScreenLockDetect();
            System.out.println("Linux Lock screen detection active");
        }
        //TODO: add line for windows lock screen detection and for Mac 
    }//ctor
    
    public final int getBigSleepTimeInMillis() { return bigSleepTime * toMilli; }
    public final int getSmallSleepTimeInMillis() { return smallSleepTime * toMilli; }
    
    public void createStatesAndStart() {
        bigSleepState = new BigSleepState(this);
        smallSleepState = new SmallSleepState(this);
        lockedScreenState = new LockedScreenState(this);
        activeState = new ActiveState(this);
        startState = new StartState(this);
        writeState = new WriteAndDecideState(this);
        reminderState = new ReminderState(this);  
        
        currentState = bigSleepState;//assign the state to start with
    }
    
    public boolean isScreenLocked(LogManager log) {
        return lockScreen.isScreenLocked(log);
    }
    
    public void userGotRestBy(final double time) {
        //System.out.println("User got rest by:"+ time+". Rate of strain decr: "+strainInfo.rateOfStrainDecrease);
        strainInfo.secondsUserIsStrained -= time * strainInfo.rateOfStrainDecrease;//user was getting rest
        if (strainInfo.secondsUserIsStrained < 0) {
            strainInfo.secondsUserIsStrained = 0;
            reminderState.resetFirstReminder();
        }
    }

    public void userWasStrainedBy(final double time) {
        //System.out.println("User was strained by:"+ time+". Rate of strain incr: "+strainInfo.rateOfStrainIncrease);
        strainInfo.secondsUserIsStrained += time * strainInfo.rateOfStrainIncrease;//user was being strained
    }
    
    public boolean userNeedsToBeRemindedToStop() {    
        //System.out.println("secondsStrained:"+strainInfo.secondsUserIsStrained+". maxTolerableStrainTime:"+maxTolerableStrainTime);
        return strainInfo.secondsUserIsStrained >= maxTolerableStrainTime;
    }
    
    public boolean userNeedsToBeToldToStopForTheDay() {//TODO: this needs to be used. Shift all data into a JSON and then do this
        //System.out.println("secondsStrained:"+strainInfo.secondsUserIsStrained+". maxTolerableStrainTime:"+maxTolerableStrainTime);
        return strainInfo.secondsUserIsStrained >= maxStrainAllowedPerDay;
    }    
    
    public void userWasActiveForThisMuchTimeToday(final double timeActive) {
        TrackerInfo info = new TrackerInfo();
        info.setDateAsTodaysDate();
        info.setTimeActive(timeActive);
        fileMgr.writeToTimeTrackerFile(info);
    }
    
}