apachetrans
===========

Contains a Linux Daemon that will push the Apache server-status to a graphite instance 

Install
-------------
To Install, read the install/install.txt file.

To Do
-------------
1. Create Proper Install Method (RPM?)
2. Allow passing environment variables like `-Dapachetrans.conf.dir=/etc/apachetrans/` and `-Dapachetrans.log.dir-/var/log/apachetrans`
3. Unit tests with Faux Apache & Graphite using Somelike like Grizley from Jersey to receive and send to
4. Modify Config to do Apache objects with metric path + apache url and then allow disabling of specific metrics
5. Fix logging to not output all the default logstash items at startup or switch to log4j2
