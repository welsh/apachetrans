apachetrans
===========

Contains a Linux Daemon that will push the Apache server-status to a graphite instance 

The points that get pushed graphite by default are:

    Number of Busy Workers
    Number of Idle Workers
    Bytes Per Request
    Bytes Per Second
    Requests Per Second
    CPU Load
    Total Accesses
    Total KBytes
    Uptime


Install
-------------
The following are the current rough install steps:

A. Clone the repo:

    cd ~
    git clone https://github.com/welsh/apachetrans
    cd apachetrans/

B. Make the following directories:

    sudo mkdir -p /etc/apachetrans /usr/share/apachetrans /var/log/apachetrans

C. Copy install files into location:

    sudo cp install/application.conf /etc/apachetrans
    sudo cp install/apachetrans.jar /usr/share/apachetrans
    sudo cp install/apachetrans.sh /etc/init.d/

D. Modify `/etc/apachetrans/application.conf` to specify:

    graphiteHost
    graphitePort
    executionTime
    apaches:
        metricPath
        apacheUrl

E. Add Execute privilege to the `/etc/init.d/` script:

    chmod +x /etc/init.d/apachetrans.sh

F. Start `apachetrans` with:

    /etc/init.d/apachetrans.sh start
    
G. If you see no errors, it has started up correct. You can follow the log:

    tail -f /var/log/apachetrans/output.log


Script Usage
-------------
Once installed, script usage is as follows:

    sudo /etc/init.d/apachetrans {start|stop|restart|status}


To Do
-------------
1. Create Proper Install Method (RPM?)
2. Fix Logging
    - not output all the default logstash items at startup or switch to log4j2
    - Allow passing environment variable for `-Dapachetrans.log.dir-/var/log/apachetrans`
3. Unit tests with Faux Apache & Graphite using Somelike like Grizley from Jersey to receive and send to
4. Allow over-ride disable for all the `server-status` points.
