#!/usr/bin/env bash

#if [ -d classes ] ; then
#	rm -rf classes;
#fi
#
#mkdir classes

javac -cp $JAVA_HOME/lib/tools.jar aboutjava/annotion/MySetter* -d
javac -processor aboutjava.annotion.MySetterProcessor aboutjava/annotion//TestMySetter.java
javap -p aboutjava/annotion/TestMySetter.class
#java -cp classes aboutjava.annotion.TestAno