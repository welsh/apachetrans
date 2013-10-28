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
There are two methods of installation, which is by a pre-built RPM or by using a Zip with the current release. Both are explain below.

**Note:** Ensure you have the environment variable of `$JAVA_HOME` set.

### RPM Method

A. Download the RPM:

    wget "https://github.com/welsh/apachetrans/releases/download/1.0.0/apachetrans-1.0-1.noarch.rpm"

B. Install it by going:

    sudo rpm -ivh apachetrans-1.0-1.noarch.rpm

### Non-RPM Method

A. Download & Extract the Zip

    wget "https://github.com/welsh/apachetrans/releases/download/1.0.0/apachetrans-1.0.zip"
    unzip apachetrans-1.0.zip
    cd apachetrans

B. Make the following directories:

    sudo mkdir -p /etc/apachetrans /usr/share/apachetrans /var/log/apachetrans

C. Copy install files into location:

    sudo cp application.conf /etc/apachetrans
    sudo cp apachetrans.jar /usr/share/apachetrans
    sudo cp apachetrans.sh /etc/init.d/

D. Add Execute privilege to the `/etc/init.d/` script:

    sudo chmod +x /etc/init.d/apachetrans.sh

Configuration
-------------

Once installed, you need to follow the following steps to configure and start up:

A. Modify `/etc/apachetrans/application.conf` to specify:

    graphiteHost
    graphitePort
    executionTime
    apaches:
        metricPath
        apacheUrl

B. Start `apachetrans` with:

    sudo /etc/init.d/apachetrans.sh start
    
C. If you see no errors, it has started up correct. You can follow the log:

    tail -f /var/log/apachetrans/output.log


Script Usage
-------------
Script usage is as follows:

    sudo /etc/init.d/apachetrans {start|stop|restart|status}
    
**Note:** You may get a stale PID file if you kill -9 the process or apachetrans fails to start. To clean up just run:

    sudo /etc/init.d/apachetrans stop

