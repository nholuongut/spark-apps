#  vim:ts=4:sts=4:sw=4:noet
#
#  Author: Nho Luong
#  Date: 2015-05-30 13:11:10 +0100 (Sat, 30 May 2015)
#
#  https://github.com/nholuongut/spark-apps
#
#  If you're using my code you're welcome to connect with me on LinkedIn and optionally send me feedback to help improve or steer this or other code I publish
#
#  https://www.linkedin.com/in/nholuong
#

REPO := nholuongut/Spark-Apps

ifneq ("$(wildcard bash-tools/Makefile.in)", "")
	include bash-tools/Makefile.in
endif

# EUID /  UID not exported in Make
# USER not populated in Docker
ifeq '$(shell id -u)' '0'
	SUDO =
else
	SUDO = sudo
endif

.PHONY: build
build:
	if [ -x /usr/bin/apt-get ]; then $(MAKE) apt-packages; fi
	if [ -x /usr/bin/yum ];     then $(MAKE) yum-packages; fi
	$(MAKE) lib
	sbt clean assembly
	cp -av target/scala-2.10/spark-apps-assembly-*.jar spark-apps.jar

.PHONY: lib
lib:
	git submodule update --init --recursive
	cd lib && mvn clean package
	sbt eclipse || echo "Ignore this last error, you simply don't have the SBT eclipse plugin, it's optional"

.PHONY: clean
clean:
	cd lib && mvn clean
	sbt clean
	rm -f spark-apps.jar

.PHONY: apt-packages
apt-packages:
	$(SUDO) apt-get update
	# needed to fetch library submodules
	$(SUDO) apt-get install -y git
	# needed to fetch Spark for tests
	$(SUDO) apt-get install -y wget
	$(SUDO) apt-get install -y tar

.PHONY: yum-packages
yum-packages:
	# needed to fetch the library submodules
	rpm -q git || $(SUDO) yum install -y git
	# needed to fetch Spark for tests
	rpm -q wget || $(SUDO) yum install -y wget
	rpm -q tar  || $(SUDO) yum install -y tar

.PHONY: update
update:
	git pull
	git submodule update --init --recursive
	$(MAKE)

.PHONY: update2
update2:
	$(MAKE) update-no-recompile

.PHONY: update-no-recompile
update-no-recompile:
	git pull
	git submodule update --init --recursive

.PHONY: update-submodules
update-submodules:
	git submodule update --init --remote
.PHONY: updatem
updatem:
	$(MAKE) update-submodules

# useful for quicker compile testing but not deploying to Spark
.PHONY: p
p:
	$(MAKE) package
.PHONY: package
package:
	git submodule update --init --recursive
	cd lib && mvn clean package
	sbt package 

.PHONY: test
test:
	sbt test
	tests/all.sh
