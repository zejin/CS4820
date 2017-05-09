#!/bin/bash
NUM_TESTS=4
R1="(536266705, 595851894)"
R2="(52251368, 58057076)"
R3="(21907422, 24341580)"
R4="(855139037, 950154485)"
R5="(83062448, 92291608)"
echo "Running tests of Framework Knapsack"
echo "Using beta value of 0.9"
echo "Each call should run in under 2 seconds"
javac FrameworkKnapsack.java
for i in `seq 0 $NUM_TESTS`; do
printf "Running test $i ...\n"
time java FrameworkKnapsack 0.9 "Instances/i"$i".txt" "Instances/i"$i"out_from_program.txt"
done
printf "Valid solution range for i0 : $R1\n"
printf "Valid solution range for i1 : $R2\n"
printf "Valid solution range for i2 : $R3\n"
printf "Valid solution range for i3 : $R4\n"
printf "Valid solution range for i4 : $R5\n"