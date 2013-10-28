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

Note: It is recommended you have the environment variable of $JAVA_HOME set.

### RPM Method

A. Download:

    install/apachetrans-1.0-1.noarch.rpm

B. Install it by going:

    rpm -ivh apachetrans-1.0-1.noarch.rpm

C. Modify `/etc/apachetrans/application.conf` to specify:

    graphiteHost
    graphitePort
    executionTime
    apaches:
        metricPath
        apacheUrl

D. Start `apachetrans` with:

    /etc/init.d/apachetrans.sh start

E. If you see no errors, it has started up correct. You can follow the log:

    tail -f /var/log/apachetrans/output.log


### Non-RPM Method

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
1. Fix up /etc/init.d script. (PID file cleanup / checks need to be done.)
2. Look into setting up under different user
3. Tidy up how config is read
4. Update ConfigUtility & ConfigUtilityTest to:
    - Take in file name over-ride
    - Cover more test cases
    - Use relative testing paths so unit tests pass on unix

