#!/bin/bash
PATH="test/formula"
FILE0=$PATH/testFormula.java
FILE1=$PATH/testBooleanCircuit.java
FILE2=test/smt/testSMTSolving.java
JAVAC="/usr/bin/javac"
echo "Compiling test cases for Formula..."
$JAVAC $FILE0
$JAVAC $FILE1
$JAVAC $FILE2
#if [ /usr/bin/javac $FILE0 ];
#then
#	echo "Compilation successful"
#else
#	echo "Compilation unsuccessful"
#fi
echo "Leaving compiling phase."
