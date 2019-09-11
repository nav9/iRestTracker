#!/bin/bash
#Checking for locked screen in Linux
while true
do
	gdbus call -e -d com.canonical.Unity -o /com/canonical/Unity/Session -m com.canonical.Unity.Session.IsLocked
done
