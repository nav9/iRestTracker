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
    
    FileManager(StrainTracker strainInfoRef) {
        strainRef = strainInfoRef;
        createNewTimeFileIfItDoesNotExist();  
    }
    
    private void createNewTimeFileIfItDoesNotExist() {
        File currentFile = new File(this.timeFilename);
        System.out.println("File Exists? "+currentFile.exists());
        if(currentFile.exists() == false) {
            try {
                currentFile.createNewFile();
                PrintWriter fos = new PrintWriter(new FileWriter(timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
                fos.println(strainRef.getAsStringForWriting());
                fos.close();
            } catch (IOException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
        }  
        loadDataFromFile();
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
    
    public void loadDataFromFile() {
        try {
            BufferedReader input = new BufferedReader(new FileReader(timeFilename));            
            strainRef.loadDataAndAssign(input.readLine().split(","));
            input.close();
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewTimeFileIfItDoesNotExist();}//in case somebody deletes the file during runtime
        } catch (NumberFormatException ex) {Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);}
    }         
    
    public void writeToFileByOverwriting() {
        try {
            PrintWriter fos = new PrintWriter(new FileWriter(timeFilename, false));//the 'false' means the file data won't be appended to. It will be overwritten
            fos.println(strainRef.getAsStringForWriting());
            fos.close();              
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {createNewTimeFileIfItDoesNotExist();}//in case somebody deletes the file during runtime 
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }    

}
