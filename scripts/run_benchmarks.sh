#!/bin/bash

# Methods:
###################### First exp
# 1: Original GSA (Gravitatory Search Algorithm)
# 2: PTGSA (Parameter tuning for alpha in Gravitatory Search Algorithm)
# 3: TW = 0.1 (PTGSA with Temporary weight)
# 4: TW = 0.25
# 5: CI = 10 (PTGSA with Consecutive iterations)
# 6: CI = 20

###################### Second round
# 20: TW = 0.05
# 21: TW = 0.125
# 22: TW = 0.15
# 23: CI = 50
# 24: CI = 75
# 25: CI = 100

exec_file=exec_dic.jar

./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 1 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 2 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 3 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 4 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 30000 -x $exec_file -m 5 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 15000 -x $exec_file -m 6 -o logs/ &

./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 20 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 21 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 300000 -x $exec_file -m 22 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 6000 -x $exec_file -m 23 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 4000 -x $exec_file -m 24 -o logs/ &
./benchmark.sh -s 1 -e 51 -i 3000 -x $exec_file -m 25 -o logs/ &



