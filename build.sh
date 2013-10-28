#!/bin/bash

NAME="apachetrans"
VER="1.0"
BASE_DIR=$(pwd)

echo "[Info] Starting Build Process"

echo "[Info]"
echo "[Info] Moving logback-test.xml out of the way temporarily."
mv src/main/resources/logback-test.xml ./

echo "[Info]"
echo "[Info] Building Jar"
mvn clean package -Dmaven.test.skip=true

echo "[Info]"
echo "[Info] Touching output file"
touch install/output.log

echo "[Info]"
echo "[Info] Cleaning rpm/SOURCES Directory" 
rm -rf rpm/SOURCES

echo "[Info]"
echo "[Info] Making rpm/SOURCES & its sub folders"
mkdir -p rpm/SOURCES
mkdir -p rpm/SOURCES/$NAME-$VER/usr/share/apachetrans/
mkdir -p rpm/SOURCES/$NAME-$VER/etc/apachetrans/
mkdir -p rpm/SOURCES/$NAME-$VER/etc/init.d/
mkdir -p rpm/SOURCES/$NAME-$VER/var/log/apachetrans/

echo "[Info]"
echo "[Info] Moving Files Into Place"
install -m 644 target/apachetrans.jar   rpm/SOURCES/$NAME-$VER/usr/share/apachetrans/
install -m 644 install/application.conf rpm/SOURCES/$NAME-$VER/etc/apachetrans/
install -m 755 install/apachetrans.sh   rpm/SOURCES/$NAME-$VER/etc/init.d/
install -m 644 install/output.log       rpm/SOURCES/$NAME-$VER/var/log/apachetrans

echo "[Info]"
echo "[Info] Switching int rpm/SOURCE & Making Tar"
cd rpm/SOURCES
tar -cvzf $NAME-$VER.tar.gz $NAME-$VER 

echo "[Info]"
echo "[Info] Cleaning up non-tar and going back into rpm"
rm -rf $NAME-$VER
cd ..

echo "[Info]"
echo "[Info] Cleaning Up Before Building RPM"
rm -rf BUILD 
rm -rf BUILDROOT 
rm -rf tmp 
rm -rf RPMS 
rm -rf SRPMS

echo "[Info]"
echo "[Info] Building RPM"
rpmbuild -ba SPECS/apachetrans.spec

echo "[Info]"
echo "[Info] Listing Contents"
rpm -qpl RPMS/noarch/$NAME-$VER-1.noarch.rpm

echo "[Info]"
echo "[Info] Cleaning up output file."
rm $BASE_DIR/install/output.log

echo "[Info]"
echo "[Info] Moving logback-text.xml back"
cd $BASE_DIR
mv logback-test.xml src/main/resources/

echo "[Info]"
echo "[Info] Moving built RPM into install folder."
cp rpm/RPMS/noarch/*.rpm install/

echo "[Info]"
echo "[Info] Moving built Jar into install folder."
cp target/apachetrans.jar install/

echo "[Info]"
echo "[Info] Done."

