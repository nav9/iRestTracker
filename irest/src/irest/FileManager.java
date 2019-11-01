package irest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private final String programName = "irest";
    public final String timeFilename = this.programName + ".time";
    private StrainTracker strainRef = null;
    private LogManager log = null;
    
    FileManager(StrainTracker strainInfoRef, LogManager logManagerRef) {
        log = logManagerRef;
        strainRef = strainInfoRef;
        createNewTimeFileIfItDoesNotExist();  
    }
    
    private void createNewTimeFileIfItDoesNotExist() {
        File currentFile = new File(this.timeFilename);
        log.write("File Exists? "+currentFile.exists());
        if(currentFile.exists() == false) {
            try {
                currentFile.createNewFile();
                PrintWriter fos = new PrintWriter(new FileWriter(timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
                fos.println(strainRef.getAsStringForWriting());
                fos.close();
            } catch (IOException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
        }  
        loadDataFromTimeFile();
    }  
    
    public long getElapsedTimeInSeconds() {
        long elapsedTime = 0;
        try {
            BufferedReader input = new BufferedReader(new FileReader(timeFilename));            
            elapsedTime = strainRef.getElapsedTimeInSeconds(input.readLine().split(","));
            input.close();
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewTimeFileIfItDoesNotExist();}//in case somebody deletes the file during runtime            
        } catch(NumberFormatException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
        return elapsedTime;
    }    
    
    public void loadDataFromTimeFile() {
        try {
            BufferedReader input = new BufferedReader(new FileReader(timeFilename));            
            strainRef.loadDataAndAssign(input.readLine().split(","));
            input.close();
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewTimeFileIfItDoesNotExist();}//in case somebody deletes the file during runtime
        } catch (NumberFormatException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
    }         
    
    public void writeToTimeFileByOverwriting() {
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
            fos.println(strainRef.getAsStringForWriting());
            fos.close();              
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewTimeFileIfItDoesNotExist();}//in case somebody deletes the file during runtime 
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }    
    
    public TrackerInfo loadDataFromTrackerFile() {
        TrackerInfo trackerInfo = new TrackerInfo();
        try {
            BufferedReader input = new BufferedReader(new FileReader(strainRef.timeTrackerFilename));            
            trackerInfo.setFromString(input.readLine());
            input.close();
        } catch (IOException ex) {
            this.createNewTrackerFile();//in case somebody deletes the file during runtime
        } catch (NumberFormatException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
        return trackerInfo;
    }  
    
    public void writeToTimeTrackerFile(final TrackerInfo trackerInfo) {
        TrackerInfo storedTrackerData = loadDataFromTrackerFile();
        trackerInfo.addTheAccumulatedTime(storedTrackerData.getTime());
        if (trackerInfo.isSameDay(storedTrackerData.getDate())) {
            try {
                PrintWriter fos = new PrintWriter(new FileWriter(strainRef.timeTrackerFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
                fos.println(trackerInfo.getAsStringForWriting());
                fos.close();              
            } catch (IOException ex) {
                if (ex instanceof FileNotFoundException) {this.createNewTrackerFile();}//in case somebody deletes the file during runtime. CAUTION/BUG: possibility of recursion
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }                           
        } else {
            this.appendPreviousDaysTotalToTimeTrackerFile(storedTrackerData);
        }           
    }       
    
    public void appendPreviousDaysTotalToTimeTrackerFile(final TrackerInfo previousDaysTotalTimeActive) {
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(strainRef.trackerResultsFilename, true));//the 'true' means the file data will be appended to. It won't be overwritten
            fos.println(previousDaysTotalTimeActive.getAsStringForWriting());
            fos.close();              
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {this.createNewTrackerFile();}//in case somebody deletes the file during runtime 
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }       
    
    public void createNewTrackerFile() {
        TrackerInfo tr = new TrackerInfo();
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(strainRef.trackerResultsFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
            fos.println(tr);
            fos.close();              
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {}//in case somebody deletes the file during runtime 
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }      
}
