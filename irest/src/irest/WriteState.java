package irest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteState extends State {
    private DataManager dat = null;
    WriteState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        writeTimeToFileByOverwriting();
        if (!dat.isScreenLocked()) {
            dat.currentState = dat.reminderState;
        } else {
            dat.currentState = dat.bigSleepState;
        }
    }    
    
    private void writeTimeToFileByOverwriting() {
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(dat.timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
            //Writes "minutesOfRestObtained,timeTheUserWasLastKnownToBeActive"
            fos.println(Integer.toString(dat.minutesOfRestObtained) + "," + Long.toString(Instant.now().getEpochSecond()));
            fos.close();              
        } catch (IOException ex) {Logger.getLogger(WriteState.class.getName()).log(Level.SEVERE, null, ex);}              
    }
}
