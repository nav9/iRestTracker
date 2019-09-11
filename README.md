# iRestTracker
A handy program that runs in the background, keeping track of how long you used the computer.
After an hour's running, a beep will remind you to stop using the computer. If you don't stop, the beep will recur every few minutes until you either lock the screen, suspend the computer, restart or shutdown. If you stop using the computer after the beep sounds, the program will calculate a certain amount of time you need to take rest, depending on how much you were strained. If you continue using the computer for many subsequent beeps, a factor is used to increase the amount of estimated rest you need, so even if you shutdown or restart the computer, once you start using it again, if the program thinks you haven't got enough rest, it'll keep beeping. The only way to override this is by killing the process using "kill -9 $(pgrep -f 'irest')" and deleting the "irest.time" file.
TODO: A running mode which extracts the log of usage data, stores the number of hours of daily use and/or displays it on a GUI.
  
# To use on Ubuntu
You have three choices.  
1. To have the script automatically start whenever you log in to your account, put the following line in Ubuntu's "startup applications" which you can access via the Ubuntu start menu.  
java -jar path/to/the/jar/file/irest.jar&  
The "irest.time" file and "irest.log" files will be created in the home directory.  
This method of starting iRest is the most convenient and is the recommended method.  
  
or  
2. Follow these instructions to run the file via init.d if you are only particular about the time being recorded and do not need the beep or dialog box reminders.  
https://askubuntu.com/a/99582/148011  
The instructions are:  
First create an "iRest" folder in your home directory.  
Place the irest.jar file and the irest_start.sh and irest_stop.sh files in that directory.  
Place the irest_initd file in the /etc/init.d folder using the command sudo cp irest_initd /etc/init.d  
Execute this command sudo update-rc.d irest_initd defaults  
Reboot the computer and irest.jar will be run automatically.  
You can check by running ps -aef | grep java  
The "irest.time" file will be created in the ~/irest/ directory.  
Update: Sept 2019: gdbus has to start for iRest to be able to detect lock screen state. This has not been tested when the program is run via init.d. When the program was run from ~/.profile, the lock screen state was not detected.

or  
3. Simply start up a terminal whenever you want to run the program and run it with  
java -jar path/to/the/jar/file/irest.jar


# To use on Windows / Mac
To create a jar: https://stackoverflow.com/questions/9681876/how-to-create-a-jar-file-in-netbeans  
The program should run fine on any other operating system that runs the JVM. The only operating-system-specific functionality that is not yet programmed for Windows and Mac is the detection of a locked screen. If you do not need that functionality, then go ahead and run the program with:  
java -jar path/to/the/jar/file/irest.jar  
  
# The State Transition Diagrams
The StateTransition.odg file can be opened with Libre Office Draw. The contents of the file are what are shown in StateTransitions.png. These states describe the transition possibilities that are programmed in the state classes. It follows a principle similar to the State Design Pattern.  

# Command to kill the process
kill -9 $(pgrep -f 'irest')

# Diagrams
StateTransitions.odg is a LibreOffice document that depicts the states that the program transitions between. The StateTransitions.png file is just the exported image of the odg file.
