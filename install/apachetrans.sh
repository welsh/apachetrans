#!/bin/bash

CONF_DIR=/etc/apachetrans/
LOG_DIR=/var/log/apachetrans/
JAR_FILE=/usr/share/apachetrans/apachetrans.jar
PIDFILE=/var/run/apachetrans.pid

JPS=${JPS:-"${JAVA_HOME}/bin/jps -l"}
JAVA=${JAVA:-"${JAVA_HOME}/bin/java"}
PSJAVA=${PSJAVA:-"ps aux | grep [j]ava"}
PSCMD="$JPS | grep -i apachetrans | awk '{ print \$1 };'"

JAVA_OPTS=${JAVA_OPTS:-"-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true"}

start() {
    if [ ! -z "$PIDFILE" ]; then
        if [ -r "$PIDFILE" ]; then
                    PID=$(cat $PIDFILE)
        fi
    else
            PID=$(eval $PSCMD)
    fi

    if [ ! -z "$PID" ]; then
        echo "apachetrans appears to already be running @ pid: $PID"
        exit 1
    fi

    EXEC=${EXEC:-"-jar $JAR_FILE"}

    if [ ! -z "$CONF_DIR" ]; then
        EXEC=${EXEC:-"-jar -Dapachetrans.conf.dir=$CONF_DIR"}
    fi

    if [ ! -z "$LOG_DIR" ]; then
        EXEC=${EXEC:-"-jar -Dapachetrans.log.dir=$LOG_DIR"}
    fi

    $JAVA -server $JAVA_OPTS $EXEC 2>&1 &

    if [ ! -z "$PIDFILE" ]; then
        echo $! > "$PIDFILE"
    fi

}

stop() {
    if [ ! -z "$PIDFILE" ]; then
        if [ -r "$PIDFILE" ]; then
                PID=$(cat $PIDFILE)
        fi
    else
            PID=$(eval $PSCMD)
    fi
    if [ ! -z "$PID" ]; then
        kill -15 "$PID"
        echo -n "Stopping apachetrans"
        while (true); do
            ps -p $PID >/dev/null 2>&1
            if [ $? -eq 0 ] ; then
                echo -n "."
                sleep 1
            else
                echo ""
                echo "apachetrans stopped"
                if [ ! -z "$PIDFILE" ]; then
                    rm -f $PIDFILE
                fi

                break
            fi
        done
    else
            echo "apachetrans was not running"
    fi
}

restart() {
        stop
        start
}

status() {
    if [ ! -z "$PIDFILE" ]; then
        if [ -r "$PIDFILE" ]; then
                PID=$(cat $PIDFILE)
        fi
    else
        PID=$(eval $PSCMD)
    fi
    if [ ! -z "$PID" ]; then
        echo "apachetrans appears to be running at pid: $PID"
        exit 0
    else
        echo "apachetrans is not running."
        exit 3
    fi
}

case $1 in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        restart
    ;;
    status)
        status
    ;;
    *)
        echo $"Usage: $0 {start|stop|restart|status}"
    ;;
esac

exit 0
