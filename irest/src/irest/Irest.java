/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package irest;

//TODO: many public class member variables need to be converted to private and have getter/setters

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
