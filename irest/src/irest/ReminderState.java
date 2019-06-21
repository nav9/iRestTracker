package irest;

//import java.awt.HeadlessException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;
import sounds.PlaySound;

public class ReminderState extends State {
    private DataManager dat = null;
    private final PlaySound sounds = new PlaySound();
    ReminderState(DataManager d) {
        dat = d;
    }
    @Override
    public void run() {
        this.sounds.PlayBeep();
        //Note: Disabling showing the message until a non-blocking alert can be implemented. As of now, the alert pauses program execution until the user closes the dialog box, and this gives SmallSleepState an inaccurate estimate of how long the user has been active since the user was reminded
//        try {
//            JOptionPane.showMessageDialog(null, "Time to rest");   
//        } catch(HeadlessException ex) {Logger.getLogger(Timekeeper.class.getName()).log(Level.WARNING, null, ex);}
        dat.currentState = dat.smallSleepState;
    }    
}
