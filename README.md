# iRestTracker
A handy program that runs in the background, keeping track of how long you used the computer.
After an hour's running, a beep will remind you to stop using the computer. If you don't stop, the beep will recur every five minutes until you either lock the screen, suspend the computer, restart or shutdown.
If you rest for half an hour, the beep reminder will get reset and you won't hear any beeps for an hour from the time you start using the computer again.
TODO: A running mode which extracts the log of usage data, stores the number of hours of daily use and/or displays it on a GUI.
  
# To use on Ubuntu
You have three choices.  
1. To start the script whenever you log in to your account, put the following line in ~/.bash_profile  
java -jar path/to/the/jar/file/irest.jar

or  
2. Follow these instructions to run the file via init.d  
https://askubuntu.com/a/99582/148011

or  
3. Simply start up a terminal whenever you want to run the program and run it with  
java -jar path/to/the/jar/file/irest.jar


# To use on Windows / Mac
The program should run fine on any other operating system that runs the JVM. The only operating-system-specific functionality that is not yet programmed for Windows and Mac is the detection of a locked screen. If you do not need that functionality, then go ahead and run the program with:  
java -jar path/to/the/jar/file/irest.jar
