#!/bin/bash

NUM_TESTS=3

echo "Running tests on flow from source vertex"
echo "These tests will not check for errors in flow past the source edges"
javac FrameworkFlow.java
javac FrameworkFlowTest.java

for i in `seq 1 $NUM_TESTS`; do
    printf "Running test $i ...\n"
    java FrameworkFlow "FlowInstances/flow"$i".txt" "FlowInstances/flow"$i"out_from_program.txt"
    java FrameworkFlowTest "FlowInstances/flow"$i"out.txt" "FlowInstances/flow"$i"out_from_program.txt"
done