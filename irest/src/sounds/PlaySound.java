/*
 * License: MIT
 * Date created: 23 Jan 2019
 * @author navin
 */
package sounds;

import java.applet.Applet;
import java.applet.AudioClip;

public class PlaySound {

    public void PlayBeep() {        
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sounds/beep3.wav"));
        clip.play();
    }
}
