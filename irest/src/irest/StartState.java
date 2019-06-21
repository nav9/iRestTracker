package irest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartState extends State {
    private DataManager dat = null;
    StartState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        LoadDataFromFile();
        dat.currentState = dat.bigSleepState;
    }    
    
    public void LoadDataFromFile() {
        try {
            BufferedReader input = new BufferedReader(new FileReader(dat.timeFilename));
            String[] line = input.readLine().split(",");
            dat.minutesOfRestObtained = Integer.parseInt(line[0]);
            long userWasLastActiveAt = Long.parseLong(line[1]);
            //TODO: use above variable and overwrite new minutesOfRestObtained
            input.close();
        } catch (IOException | NumberFormatException ex) {Logger.getLogger(StartState.class.getName()).log(Level.SEVERE, null, ex);}
    }        
}
