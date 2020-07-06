/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package linux;

import irest.LockScreenDetector;
import irest.LogManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinuxScreenLockDetect extends LockScreenDetector {
    private final String command = "gdbus call -e -d com.canonical.Unity -o /com/canonical/Unity/Session -m com.canonical.Unity.Session.IsLocked";
    @Override
    public boolean isScreenLocked(LogManager log) {
        boolean locked = false;
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            log.write("Executing: "+command);
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            InputStream i = p.getInputStream();
            log.write("Input stream result: "+i);
            BufferedReader reader = new BufferedReader(new InputStreamReader(i));
            String result = reader.readLine();
            log.write("Result: "+result);
            if (result != null) {
                if (result.contains("true")) {locked = true;}
            }
        } catch (IOException | InterruptedException ex) {Logger.getLogger(LinuxScreenLockDetect.class.getName()).log(Level.SEVERE, null, ex);}
        return locked;
    }
}
