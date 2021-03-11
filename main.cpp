#include "Problem.h"
#include "Solution.h"
#include <iostream>

using namespace std;

int main() {
	cout << "###\n";
	Solution * sol = new Solution(3);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->mutation(1);
	sol->print();
	cout << "###\n";
	return 0;
}



