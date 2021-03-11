#ifndef _SOLUTION_
#define _SOLUTION_

typedef double real;

using namespace std;

class Solution {

	public:
		real * x;
		int n;
		Solution (int n = 1);
		void mutation (int k = 0);
		void print ();
};
#endif
