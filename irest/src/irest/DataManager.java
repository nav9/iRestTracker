/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    private final String currentFilename = "time.txt";
    private final String resultsFilename = "results.csv";
    private final File currentFile;
    private PrintWriter fos;
    
    public DataManager() {
        this.fos = null;
        currentFile = new File(currentFilename);
        if(!currentFile.exists()){
            try {currentFile.createNewFile();} catch (IOException ex) {Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);}
        }   
        try {fos = new PrintWriter(new FileWriter(currentFilename, true));} catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "The file "+currentFilename+" could not be created. No recording of time can happen.", ex);
            //TODO: make provision to play sound to notify about this error. Or show message in GUI.
        }
    }
    
    public void RecordActiveTime(final Long time) {
        if (fos == null) {return;}        
        fos.println(Long.toString(time));
        fos.flush();
    }
    
}
