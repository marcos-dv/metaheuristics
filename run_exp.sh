#!/bin/bash

if [ ! -d "logs" ]; then
	mkdir logs
fi

#./exec NUM_ITER ID_PROBLEM METHOD NUM_SIMUL
#	NUM_ITER: number of iterations. Default: 30
#	ID_PROBLEM: id of the problem to solve. Range of ids: from 1 to 15. Default: 1
#	METHOD: solving method number. Default: 1
#	NUM_SIMUL: number of simulations. Default: 30

EXEC_FILE=exec1.jar
NUM_SIMUL=30

# PTGSA
NUM_ITER=300000

#Seed = 11235813*(cur_method*15+(j-1))

for (( method=1; method<=4; method++ )); 
do
	for problem in 1 2 3 4 5 6 8
	do
		echo "# Method $method Problem $problem"
		seed=$((($method*16+$problem)*11235813))
		timestamp=$(date +"%Y-%m-%d_%H-%M-%S")
		name="$method-$problem"
		additions="$timestamp-$NUM_ITER-$NUM_SIMUL-$seed"
		filename="$name.txt"
		filename2="$name-$additions.txt"
		java -jar $EXEC_FILE $NUM_ITER $problem $method $NUM_SIMUL $seed | tee -a "logs/$filename"
		cp "logs/$filename" "logs/$filename2"
	done
done

NUM_ITER=30000

for (( method=5; method<=5; method++ )); 
do
	for problem in 1 2 3 4 5 6 8
	do
		echo "# Method $method Problem $problem"
		seed=$((($method*16+$problem)*11235813))
		timestamp=$(date +"%Y-%m-%d_%H-%M-%S")
		name="$method-$problem"
		additions="$timestamp-$NUM_ITER-$NUM_SIMUL-$seed"
		filename="$name.txt"
		filename2="$name-$additions.txt"
		java -jar $EXEC_FILE $NUM_ITER $problem $method $NUM_SIMUL $seed | tee -a "logs/$filename"
		cp "logs/$filename" "logs/$filename2"
	done
done

NUM_ITER=15000

for (( method=6; method<=6; method++ )); 
do
	for problem in 1 2 3 4 5 6 8
	do
		echo "# Method $method Problem $problem"
		seed=$((($method*16+$problem)*11235813))
		timestamp=$(date +"%Y-%m-%d_%H-%M-%S")
		name="$method-$problem"
		additions="$timestamp-$NUM_ITER-$NUM_SIMUL-$seed"
		filename="$name.txt"
		filename2="$name-$additions.txt"
		java -jar $EXEC_FILE $NUM_ITER $problem $method $NUM_SIMUL $seed | tee -a "logs/$filename"
		cp "logs/$filename" "logs/$filename2"
	done
done


