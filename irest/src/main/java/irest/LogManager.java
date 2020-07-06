package irest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
    private final boolean loggerActive = true;
    public final String logFilename = "irest.log";//didn't use log4j or slf on purpose, to avoid an extra dependency for this tiny program. Adding it in future is an option
    
    LogManager() {
        if (loggerActive) {createNewLogFileIfItDoesNotExist();}
    }
    
    private void createNewLogFileIfItDoesNotExist() {
        File currentFile = new File(this.logFilename);
        if(currentFile.exists() == false) {
            try {
                currentFile.createNewFile();
            } catch (IOException ex) {Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);}
        }  
    }    
    
    public void write(final String message) {
        if (loggerActive == false) {return;}
        
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        String toWrite = "["+dateNow+" "+timeNow+"] "+message;
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(logFilename, true));//the 'true' means the file data will be appended to. It will not be overwritten
            fos.println(toWrite);
            fos.close();              
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewLogFileIfItDoesNotExist();}//in case somebody deletes the file during runtime 
            Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        }  
        System.out.println(toWrite);        
    }

}