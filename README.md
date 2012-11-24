Texas Hold’em AI
=====================

Technology used
---------------
We used Java to develop our Texas Hold’em Simulator. More precisely, we chose Guice for dependency injection and H2, a free, lightweight embedded SQL database, to store and retrieve the data from the pre­flop simulation and the opponents model. Maven is also used to manage our dependencies for the project.

How to use
---------------
1. You need to fetch the dependencies from the maven repositories:

	    mvn install	
2. There are multiple classes with “main” function: 
 * Play [demo|phase1|phase2|phase3]. The parameter is optional. The default is demo. See *GameProperties.java file to get more details
 * RunPreFlopSimulator
 * RunModeler
 * PrintModeler & PrintPreFlop

Three different AI level
-----------------
- Phase I: Take decision based only on the power of his hand
- Phase II: Use hand-strength calculation and pre-flop simulation roll-out
- Phase III: Use phase II techniques plus opponent modelling

Each different AI is declined in two different strategies. A bluffer and a normal/rational player.