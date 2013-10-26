apachetrans
===========

Contains a Linux Daemon that will push the Apache server-status to a graphite instance 

Install
-------------
To Install, read the install/install.txt file.

To Use
-------------
Once installed, usage is as follows:

    sudo /etc/init.d/apachetrans {start|stop|restart|status}


To Do
-------------
1. Create Proper Install Method (RPM?)
2. Allow passing environment variable for `-Dapachetrans.log.dir-/var/log/apachetrans`
3. Unit tests with Faux Apache & Graphite using Somelike like Grizley from Jersey to receive and send to
4. Fix logging to not output all the default logstash items at startup or switch to log4j2
