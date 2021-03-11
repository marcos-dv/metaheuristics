
a.out:  main.o Problem.o Solution.o
	g++ main.o Problem.o Solution.o
main.o: main.cpp Problem.h Solution.h
	g++ -c main.cpp
Problem.o: Problem.cpp Problem.h Solution.h
	g++ -c Problem.cpp
Solution.o: Solution.cpp Solution.h
	g++ -c Solution.cpp
        
        
