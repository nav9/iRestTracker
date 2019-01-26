/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
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
import static jdk.nashorn.internal.runtime.JSType.isNumber;

public class DataManager {
    private final String programName = "irest";
    private final String currentFilename = "time." + this.programName;
    private final String lastRemindedTimeFilename = "rem" + this.programName;
    private final String resultsFilename = this.programName + "results.csv";
    
    public DataManager() {}//ctor
    
    public boolean IsFirstRunIfYesCreateFile() {
        boolean fileExists = this.DoesThisFileExist(this.currentFilename);
        File currentFile = new File(this.currentFilename);
        if(!currentFile.exists()){
            try {currentFile.createNewFile();} catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}
        }
        return !fileExists;
    }
    
    private boolean DoesThisFileExist(final String filename) {
        File currentFile = new File(filename);        
        return currentFile.exists();
    }
    
    public void RecordActiveTime(final Long time) {//appends to file  
        if (!this.DoesThisFileExist(this.currentFilename)) {return;}
        PrintWriter fos = null;
        try {fos = new PrintWriter(new FileWriter(this.currentFilename, true));} catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "The file "+currentFilename+" could not be created. No recording of time can happen.", ex);}                
        if (fos == null) {return;}        
        fos.println(Long.toString(time));
        fos.close();        
    }
    
    public void RecordTimeRemindedUser(final long time) {//replaces existing value in file
        File currentFile;
        PrintWriter fos = null;
        
        currentFile = new File(this.lastRemindedTimeFilename);
        if(!currentFile.exists()){
            try {
                currentFile.createNewFile();
            } catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(!currentFile.exists()){return;}
        
        try {fos = new PrintWriter(new FileWriter(this.lastRemindedTimeFilename, false));} catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "The file "+currentFilename+" could not be created. No recording of time can happen.", ex);}                
        if (fos == null) {return;}        
        fos.println(Long.toString(time));
        fos.close();          
    }
    
    public long GetLastRemindedTime() {
        return GetLastTime(this.lastRemindedTimeFilename);
    }
    
    public long GetLastRecordedTime() {
        return GetLastTime(this.currentFilename);
    }
    
    private long GetLastTime(final String filename) {
        BufferedReader input = null;
        String last = null, line;     
        long lastTime = 0;
        try {
            input = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}

        try {
            if (input != null) {
                while ((line = input.readLine()) != null) {last = line;}     
                if (isNumber(last)) {lastTime = Long.parseLong(last);}
            }
        } catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}
        return lastTime;
    }    
    
}
