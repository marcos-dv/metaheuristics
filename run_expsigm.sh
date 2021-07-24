#!/bin/bash

if [ ! -d "logs" ]; then
	mkdir logs
fi

#./exec NUM_ITER ID_PROBLEM METHOD NUM_SIMUL
#	NUM_ITER: number of iterations. Default: 30
#	ID_PROBLEM: id of the problem to solve. Range of ids: from 1 to 15. Default: 1
#	METHOD: solving method number. Default: 1
#	NUM_SIMUL: number of simulations. Default: 30

EXEC_FILE=sigm.jar
NUM_SIMUL=30

# PTGSA
NUM_ITER=300000

#Seed = 11235813*(cur_method*15+(j-1))

for (( method=7; method<=7; method++ )); 
do
	for problem in 1 2 3 4
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

