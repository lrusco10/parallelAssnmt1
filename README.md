

# Pseudo code for the important methods in my code

## Fitness
I will implement a fitness aslgorithm that sums the fitness of all factory objects in a floor. The floor is the final object that is weighted against other possible floors as drafted in the beginning of the program.

## selection:
sort all of the floor plans
choose a max and min configuration to bound a rand
select only floors greater than rand to move onto crossover

## crossover:
if # of floors is odd, kill lowest floor
pair up all floors
higher affinity parent gets 80% chance to give station to child floor.
lower affinity parent gets 20% chance to give station to child
child floors added to pool.

## Mutation:
every station of every floor gets to move 1 space in any direction.


## Objects
different stations will be decided at random, but the number of stations should remain constant. 
green -- most fit while touching red.
red -- most fit while touching green.
blue -- most fit while there is a space that distances it from green.
yellow -- most fit while it is 3 spaces from red.

