/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

//TODO: many public class member variables need to be converted to private and have getter/setters
//https://stackoverflow.com/questions/10228145/how-to-detect-workstation-system-screen-lock-unlock-in-windows-os-using-java
public class Irest {
    public static void main(String[] args) {
        DataManager dat = new DataManager();
        dat.createStatesAndStart();
        //---main loop that runs while the system runs
        while(true) {
            dat.currentState.run();
        }            
    }    
}