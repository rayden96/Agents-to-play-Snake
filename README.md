# HonorsProject
4 AI Snake playing agents will be developed and compared against one another.

\1. Ensure GP has reproduceable results

2. Tune GPInitial parameters:
 - tune the tournament size, run 3% 5% 8% and 10% and compare results
 - tune the crossover chance: 1, 3, 5, 10, 15, 20
 - tune the mutation chance: 1, 3, 5, 10, 15, 20

Settle on some set of arameters

Initial gen chance, 10-1 8? maybe lower since structural change can occur

Add some form of structure change with mutation and crossover - done

3. Figure out GPSecond:
 - Function set - focus on prolonging the game and think about the future
	- ifNextMoveEndsGame - if the first operator ends the game, use the second option
	- ifAbove
	- ifRight
	ifbelow
	ifLeft
 - genetic operators? splits
 5% reproduction - random (no tournament)
 95 - equally crossover and mutation
 - a size difference? another depth?
 - parameter tuning

4. Finalize GPThird
 - start from a primed population
 - Add initial generation in some form
 - harsh genetic operators to focus on exploration
 - use more amount of games played to train 4x? 2x? 8x?

5. Finalise the script to go as follows
Test and record the following: get the score, numMoves and games won for each run
start with Randomizer:
 - 50 independent runs 
Then TowardsLessDense
Then WallHugger

Then start with training of PSO:
Record runtime of the training
then do the 50 independent runs etc

start with training of GP Initial
50 runs
record training runtime

then gp second

then gp third

then calculate the means and standard deviations of each and print out the comparisons




99%Test 1
Average score: 7.36 Average moves: 33.18 Games won: 46.0
Test 2
Average score: 7.48 Average moves: 23.0 Games won: 55.0
Test 3
Average score: 7.45 Average moves: 32.72 Games won: 58.0
Test 4
Average score: 7.45 Average moves: 21.62 Games won: 52.0
Test 5
Average score: 7.31 Average moves: 52.16 Games won: 56.0
Test 6
Average score: 7.48 Average moves: 32.37 Games won: 58.0
Test 7
Average score: 7.46 Average moves: 32.94 Games won: 54.0
Test 8
Average score: 7.52 Average moves: 43.16 Games won: 64.0
Test 9
Average score: 7.4 Average moves: 43.66 Games won: 57.0
Test 10
Average score: 7.57 Average moves: 32.83 Games won: 62.0
Average score: 7.448000000000002 Average moves: 34.763999999999996 Games won: 56.2
99%Test 1
Average score: 10.55 Average moves: 247.14 Games won: 40.0
Test 2
Average score: 11.06 Average moves: 172.68 Games won: 33.0
Test 3
Average score: 11.4 Average moves: 164.54 Games won: 36.0
Test 4
Average score: 10.43 Average moves: 230.84 Games won: 34.0
Test 5
Average score: 10.54 Average moves: 221.37 Games won: 33.0
Test 6
Average score: 11.0 Average moves: 183.64 Games won: 34.0
Test 7
Average score: 11.18 Average moves: 201.03 Games won: 39.0
Test 8
Average score: 11.74 Average moves: 174.28 Games won: 42.0
Test 9
Average score: 10.81 Average moves: 220.41 Games won: 35.0
Test 10
Average score: 11.32 Average moves: 184.41 Games won: 38.0
Average score: 11.003 Average moves: 200.03400000000002 Games won: 36.4
99%Test 1
Average score: 13.01 Average moves: 284.76 Games won: 3.0
Test 2
Average score: 13.07 Average moves: 256.17 Games won: 1.0
Test 3
Average score: 13.1 Average moves: 275.56 Games won: 2.0
Test 4
Average score: 12.07 Average moves: 338.52 Games won: 0.0
Test 5
Average score: 11.7 Average moves: 383.06 Games won: 1.0
Test 6
Average score: 11.94 Average moves: 375.43 Games won: 3.0
Test 7
Average score: 11.17 Average moves: 369.68 Games won: 1.0
Test 8
Average score: 13.2 Average moves: 267.94 Games won: 0.0
Test 9
Average score: 12.71 Average moves: 301.04 Games won: 1.0
Test 10
Average score: 12.2 Average moves: 344.3 Games won: 2.0
Average score: 12.417 Average moves: 319.646 Games won: 1.4
99%Test 1
Average score: 16.12 Average moves: 446.98 Games won: 2.0
Test 2
Average score: 16.91 Average moves: 395.82 Games won: 3.0
Test 3
Average score: 17.94 Average moves: 386.63 Games won: 3.0
Test 4
Average score: 18.77 Average moves: 338.76 Games won: 3.0
Test 5
Average score: 16.2 Average moves: 446.89 Games won: 4.0
Test 6
Average score: 15.54 Average moves: 434.76 Games won: 1.0
Test 7
Average score: 17.69 Average moves: 328.91 Games won: 5.0
Test 8
Average score: 16.18 Average moves: 432.14 Games won: 0.0
Test 9
Average score: 18.46 Average moves: 392.45 Games won: 2.0
Test 10
Average score: 17.24 Average moves: 412.35 Games won: 4.0
Average score: 17.105 Average moves: 401.56899999999996 Games won: 2.7
99%Test 1
Average score: 18.49 Average moves: 538.07 Games won: 0.0
Test 2
Average score: 19.68 Average moves: 501.97 Games won: 0.0
Test 3
Average score: 18.58 Average moves: 553.37 Games won: 0.0
Test 4
Average score: 21.9 Average moves: 456.1 Games won: 0.0
Test 5
Average score: 18.54 Average moves: 534.64 Games won: 0.0
Test 6
Average score: 19.08 Average moves: 557.61 Games won: 0.0
Test 7
Average score: 17.76 Average moves: 573.94 Games won: 0.0
Test 8
Average score: 18.0 Average moves: 539.52 Games won: 0.0
Test 9
Average score: 15.75 Average moves: 531.37 Games won: 0.0
Test 10
Average score: 17.83 Average moves: 529.39 Games won: 0.0
Average score: 18.561 Average moves: 531.598 Games won: 0.0
99%Test 1
Average score: 22.06 Average moves: 565.25 Games won: 0.0
Test 2
Average score: 24.89 Average moves: 581.47 Games won: 0.0
Test 3
Average score: 20.75 Average moves: 563.45 Games won: 0.0
Test 4
Average score: 22.74 Average moves: 577.3 Games won: 0.0
Test 5
Average score: 22.08 Average moves: 562.77 Games won: 0.0
Test 6
Average score: 19.16 Average moves: 647.2 Games won: 0.0
Test 7
Average score: 20.58 Average moves: 554.48 Games won: 0.0
Test 8
Average score: 22.86 Average moves: 588.07 Games won: 0.0
Test 9
Average score: 21.72 Average moves: 625.55 Games won: 0.0
Test 10
Average score: 21.55 Average moves: 616.5 Games won: 0.0
Average score: 21.839000000000002 Average moves: 588.2040000000001 Games won: 0.0

Tournament size : best in the range of 5 - 8 with no significant difference. Seems to preform better with lower (exploration)
Crossover - no signficant difference safe bet is 10
Mutation - absolutely no difference since it is 1 point mutation, potentially try a random chance between 1 and 20 every time


