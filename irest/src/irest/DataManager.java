/*
 * License: MIT
 * @author navin
 */
package irest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import linux.LinuxScreenLockDetect;

public class DataManager {
    public State currentState = new State();
    public BigSleepState bigSleepState = null;
    public SmallSleepState smallSleepState = null;
    public LockedScreenState lockedScreenState = null;
    public ActiveState activeState = null;
    public StartState startState = null;
    public WriteState writeState = null;
    public ReminderState reminderState = null;
    
    public int minutesOfRestObtained = 0;//Unit: seconds
    public final float strainFactor = 1;//Factor to multiply with big or small sleep time value, when incrementing or decrementing minutesOfRestObtained
    
    private LockScreenDetector lockScreen = new LockScreenDetector();//Default functionality is to not detect lock screen. Can be overridden based on OS type, where the overriding class can detect screen locks
    private final int toMilli = 1000;
    private final int toSecond = 60;
    public final int bigSleepTime = 1 * this.toSecond * this.toMilli;//Unit: minutes to milliseconds. Thread sleep needs it in this unit
    public final int smallSleepTime = 1 * this.toSecond * this.toMilli;//Unit: minutes to milliseconds. Thread sleep needs it in this unit
    private final String programName = "irest";
    public final String timeFilename = "time." + this.programName;
    
    DataManager() {
        String osName = System.getProperty("os.name");
        //---Assign lock screen detection class based on OS type
        if (osName.equals("Linux")) {lockScreen = new LinuxScreenLockDetect();}
        //TODO: add line for windows lock screen detection and for Mac 
        
        createNewFileToStoreInfoIfItDoesNotExist();
    }//ctor
    
    public void createStatesAndStart() {
        bigSleepState = new BigSleepState(this);
        smallSleepState = new SmallSleepState(this);
        lockedScreenState = new LockedScreenState(this);
        activeState = new ActiveState(this);
        startState = new StartState(this);
        writeState = new WriteState(this);
        reminderState = new ReminderState(this);  
        
        currentState = bigSleepState;//assign the state to start with
    }
    
    public boolean isScreenLocked() {return lockScreen.isScreenLocked();}
    
    private void createNewFileToStoreInfoIfItDoesNotExist() {
        File currentFile = new File(this.timeFilename);
        if(!currentFile.exists()) {
            try {
                currentFile.createNewFile();
                writeInitialRunDummyDataToFile();
            } catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}
        }            
    }
    
    private void writeInitialRunDummyDataToFile() {//Writes dummy data since it's the first time the file is being created
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
            //Writes "minutesOfRestObtained,timeTheUserWasLastKnownToBeActive"
            fos.println(Integer.toString(minutesOfRestObtained) + "," + Long.toString(Instant.now().getEpochSecond()));
            fos.close();              
        } catch (IOException ex) {Logger.getLogger(WriteState.class.getName()).log(Level.SEVERE, null, ex);}              
    }    
    
}
