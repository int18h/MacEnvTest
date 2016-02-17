#!/usr/bin/env sh

javac MacEnvTest.java

jar cfvm MacEnvTest.jar manifest.txt *class

rm *class

