#include "Solution.h"

#include <stdlib.h>
#include <iostream>


Solution::Solution (int n) {
	srand(0);
	if (n < 1)
		cerr << "Solution.cpp: n < 1 population is empty\n";
		x = new real[n];
	this->n = n;
	for (int i = 0; i < n; ++i) {
		x[i] = 0.0;
	}
}

void Solution::mutation (int k) {
	if (k == 0) {
		int i = rand() % n;
		int j;
		do {
			j = rand() % n;
		} while (i == j);
		
		real aux = x[i];
		x[i] = x[j];
		x[j] = aux;
	}
	else {
		int i = random() % n;
		int integer = random() % 10;
		int dec = random() % 10;
		x[i] = (real)integer + ((real)dec/10);
	}
}

void Solution::print() {
	cout << "Solution, n = " << n << '\n';
	for (int i = 0; i < n; ++i) {
		cout << ' ' << x[i];
	}
	cout << '\n';
}





