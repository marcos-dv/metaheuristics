#!/bin/bash

# Author: Marcos Dominguez Velad <marcos.dominguezv.dev@gmail.com>
#
# Example: "./benchmark.sh -s 0 -e 30 -i 300000 -x exec.jar -m 3 -o logs/"

# Default values
OUTPUT_DIR="logs"
EXEC_FILE=exec1.jar
METHOD_NUMBER=1
# Expected: 30 or 50...
NUM_SIMUL=75 # STARTING_SIMULATION and ENDING_SIMULATION variables decide the number of sims
# this is just an informative number for the java application
STARTING_SIMULATION=0
ENDING_SIMULATION=$NUM_SIMUL
NUM_ITER=300000

# Parse arguments
while getopts "hs:e:i:x:m:o:" opt; do
  case $opt in
    h)
      printf "\tUSAGE: $0 [-h] [-s STARTING_SIMULATION] [-e ENDING_SIMULATION] [-i NUMBER_ITERATIONS]\n"
      printf "\t\t\t\t[-x EXECUTABLE_FILE] [-m METHOD_NUMBER] [-o OUTPUT_DIR]\n"
      printf "\tDefault values:\n"
      printf "\t\t-s: $STARTING_SIMULATION\n"
      printf "\t\t-e: $ENDING_SIMULATION\n"
      printf "\t\t-i: $NUM_ITER\n"
      printf "\t\t-x: $EXEC_FILE\n"
      printf "\t\t-m: $METHOD_NUMBER\n"
      printf "\t\t-o: $OUTPUT_DIR\n"
      exit 0
      ;;
    s)
      STARTING_SIMULATION=$OPTARG
      ;;
    e)
      ENDING_SIMULATION=$OPTARG
      ;;
    i)
      NUM_ITER=$OPTARG
      ;;
    x)
      EXEC_FILE=$OPTARG
      ;;
    m)
      METHOD_NUMBER=$OPTARG
      ;;
    o)
      OUTPUT_DIR="$OPTARG"
      ;;
  esac
done

if [ ! -d $OUTPUT_DIR ]; then
	mkdir $OUTPUT_DIR
fi

MY_SEED=11235813
# Computing the seed for problem j with method M (there are 15 problems but indexed from 1 to 15)
# Seed = 11235813*(M*16+j)

#for problem in 1 2 3 4 5 6 8
#for problem in 7 9 10 11 12 13 14 15
TOTAL_BENCHMARK_FUNCTIONS=16
for problem in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
do
	seed=$((($METHOD_NUMBER*$TOTAL_BENCHMARK_FUNCTIONS+$problem)*$MY_SEED))
	timestamp=$(date +"%Y-%m-%d_%H-%M-%S")
	echo "# Method $METHOD_NUMBER Problem $problem | $timestamp"

	name="method-$METHOD_NUMBER-cecfunc-$problem"
	additions="date-$timestamp-iters-$NUM_ITER-startsim_$STARTING_SIMULATION-endsim-$ENDING_SIMULATION-totalsims-$NUM_SIMUL-seed-$seed"

	filename="$name-startsim-$STARTING_SIMULATION-endsim-$ENDING_SIMULATION.txt"
	filename2="$name-$additions.txt"

	java -jar $EXEC_FILE $NUM_ITER $problem $METHOD_NUMBER $NUM_SIMUL $STARTING_SIMULATION $ENDING_SIMULATION $seed | tee -a "$OUTPUT_DIR/$filename"
	cp "$OUTPUT_DIR/$filename" "$OUTPUT_DIR/$filename2"
	date
done

date
echo " ## DONE ## "


