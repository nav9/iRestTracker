# -*- coding: utf-8 -*-
"""
Created on Tue Jan 22 20:31:35 2019

@author: navin
"""

#!/usr/bin/env python
import gobject
import dbus
import time
import datetime
from dbus.mainloop.glib import DBusGMainLoop

def filter_cb(bus, message):
    if message.get_member() != "EventEmitted":
        return
    args = message.get_args_list()
    if args[0] == "desktop-lock":
        print("Lock Screen")
        print(time.time())
        print(datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S'))
    elif args[0] == "desktop-unlock":
        print("Unlock Screen")
        print(time.time())

DBusGMainLoop(set_as_default=True)
bus = dbus.SessionBus()
bus.add_match_string("type='signal',interface='com.ubuntu.Upstart0_6'")
bus.add_message_filter(filter_cb)
mainloop = gobject.MainLoop()
mainloop.run()


