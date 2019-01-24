/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

import java.util.logging.Level;
import java.util.logging.Logger;
import linux.LinuxScreenLockDetect;

public class Irest {
    public static void main(String[] args) {
        LockScreenDetector lockScreen = new LockScreenDetector();//Default functionality is to not detect lock screen. Can be overridden based on OS type, where the overriding class can detect screen locks
        String osName = System.getProperty("os.name");
        Timekeeper t = new Timekeeper();
        
        //---Assign lock screen detection class based on OS type
        if (osName.equals("Linux")) {lockScreen = new LinuxScreenLockDetect();}
        
        //---main loop that runs while the system runs
        while(true) {
            if (!lockScreen.isScreenLocked()) {t.RecordActivity();}
            try {Thread.sleep(t.GetPauseDelay());} catch (InterruptedException ex) {Logger.getLogger(Irest.class.getName()).log(Level.SEVERE, null, ex);}            
        }            
    }    
}
