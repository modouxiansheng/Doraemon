#!/usr/bin/env bash

if [ -d classes ] ; then
	rm -rf classes;
fi

mkdir classes

javac -cp $JAVA_HOME/lib/tools.jar aboutjava/annotion/MyGette* -d classes/
javac -cp classes -d classes -processor aboutjava.annotion.MyGetterProcessor aboutjava/annotion/testAno.java
javap -p classes/aboutjava/annotion/testAno.class
java -cp classes aboutjava.annotion.testAno