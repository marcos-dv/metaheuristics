The attached project has a lot of classes and packages, in order to have a structured project and differentiate the distinct functionalities. There are a lot of problems and algorithms to solve them based on populations of solutions.

The most interesting packages are:
-Mason: The 2 classes inside this package are related to the Mason simulation. The ContinuousMetaSimulation.java class has a problem and an algorithm as attributes, so any problem (2-dimensional) and algorithm can be used. It is necessary to change the coordinates of the real space to the Mason screen simulation coords.

-Metaheuristics:
A lot of algorithms are implemented here. They implement the IMetaheuristic interface so all of them have some usual functions as "nextIteration()". The core of the algorithms is inside the nextIteration function. A brief summary of the classes within here:

# Related to PSO:
## PSO: Particle Swarm Optimization implementation. The nextIteration() function will call computeSpeed() where the main code of the PSO algorithm allocates. The implementation is based on this formulation:

1. Compute speed of all the particles as:
v(t) = a*v(t-1) + rand*b*(l-x) + rand*c*(g-x)

Where a, b, c are coefficients (hyperparameters) of PSO, rand are (0,1) uniform random values, v(t-1) is the speed at iteration t-1, l is the local best position of the particle, g the global best position of the population, and x the current position of the particle.

2. Update position with the current speed:
x(t) = lr*v(t) + x(t-1)

## PSOGroups: It is PSO but in computeSpeed(), instead of computing the global best position, it looks for the group best position. If popsize = 50 and groupSize = 5, the first 5 individuals are in the first group and are independant of the rest. 5th-10th individuals are the second group, and so on.

## PSOAdaptativeGroups: The same as PSOGroups but each a certain number of iterations, the group size increases (so there are less groups, less exploration, and eventually it will be equivalent to the usual PSO).

# Related to the autoadjustment algorithms:
## PTAlgorithm: Parameter-Tuning Algorithm. Abstract class to implement the method of autoadjustment for only one parameter. It relies on the EW (Matrix of Elementary Weights) class to handle the choice of the parameter value.

## PTAlgorithmMulti: Adaptation of the Parameter-Tuning Algorithm for more than one parameter. This leads to the two original variants presented in the paper: PTAlgorithmOne and PTAlgorithmAll.

These are abstracted classes, when applied to PSO, they are named as PSOOne and PSOAll classes.

# Related to GSA (Gravitatory Search Algorithm):
This is a swarm algorithm not explain in the paper but used for the experiments. I think it is a really interesting algorithm so I will explain it just for your information:

Just simulate the gravity laws over the particles. The better a particle is (in terms of fitness), the heavier it is. The heavier a particle is, the more attractive, so you can assign masses to the solutions and apply the Second Law of Newton, so the best particles will be surrounded by the worse particles, improving the populations. Indeed, the k-best particles are the only ones attracting other particles, and the number k decreases with the iterations, so at the beginning, all the particles are influencing other particles, at half of the number of iterations, only the best popsize/2 individuals are influencing. The last iterations, only the best particle is influencing other particles. You can see the nextIteration() function for implementation details.

The gravity influence is controlled by a parameter called alpha, which is autoadjusted in the PTGSA (Parameter Tuning GSA) class.

# Related to other packages (they are not relevant for our purpose):
## problems and commombenchmarks: these packages include some problems to test the algorithms. For example, the CircleProblem class implements a problem where the fitness function is minimizing the distance of the particle to a given circle (concretely, to the circle's border). See the fitness() function of the problems to check the definition/implementation of the problems.

## solutions: contains the implementation of Solutions (basically double[] arrays associated to a problem).

## demo: includes the executable classes for the demonstration. SSMMExp contains the class to reproduce the experiments which lead to the paper results. MasonSimulation class contains the code to simulate with Mason a pair (Problem, Algorithm).

## control, utils, misc: these packages are to make easier the implementation of the classes: Random number generators, geometry and polygons library...

## tests: class with a lot of tests to check that everything is ok. Just to ensure that this is not a hardcoded project, I have been carefully with the implementation and it has taken a lot of time.


