import java.util.Random;

public class GPRunnerInitial {

    GPInitial[] population;
    GPInitial[] tournament;
    double[] fitness;
    int populationSize;
    int tournamentSize;

    int generations;

    Random rand;

    int boardSize;

    int crossoverChance = 5;
    int mutationChance = 5;

    public GPRunnerInitial(int populationSize, int generations, int tournamentSize, int MaxDepth, int boardSize, Random rand) {
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.generations = generations;
        this.boardSize = boardSize;
        this.rand = rand;
        population = new GPInitial[populationSize];
        fitness = new double[populationSize];
        for(int i = 0; i < populationSize; i++){
            population[i] = new GPInitial(MaxDepth, rand, boardSize);
        }
        tournament = new GPInitial[tournamentSize];
    }

    public void run(){
        for(int i = 0; i < generations; i++){
            for(int j = 0; j < populationSize; j++){
                fitness[j] = population[j].playAndGetFitness(50);
            }
            System.out.println("Generation " + i + " complete");
            GPInitial[] newPopulation = new GPInitial[populationSize];

            // //perform fast sort on fitness and population based on fitness
            // for(int j = 0; j < populationSize; j++){
            //     int maxIndex = j;
            //     for(int k = j + 1; k < populationSize; k++){
            //         if(fitness[k] > fitness[maxIndex]){
            //             maxIndex = k;
            //         }
            //     }
            //     double tempFitness = fitness[j];
            //     fitness[j] = fitness[maxIndex];
            //     fitness[maxIndex] = tempFitness;
            //     GPInitial temp = population[j];
            //     population[j] = population[maxIndex];
            //     population[maxIndex] = temp;
            // }
            // //copy over best x number of individuals where x is 1% of the population size
            // int numCopied = populationSize / 100;
            // for(int j = 0; j < numCopied; j++){
            //     newPopulation[j] = population[j].copyTree();
            // }

            //eliteism above - very slow - potentially optimize

            //copy over fittest individual
            int maxIndex = 0;
            for(int j = 1; j < populationSize; j++){
                if(fitness[j] > fitness[maxIndex]){
                    maxIndex = j;
                }
            }
            newPopulation[0] = population[maxIndex].copyTree();
            

            for(int j = 1; j < populationSize; j++){
                //select the tournament
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[rand.nextInt(populationSize)];
                }
                //find the best and second best
                GPInitial best = tournament[0];
                GPInitial secondBest = tournament[1];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness > best.fitness){
                        secondBest = best;
                        best = tournament[k];
                    }
                    else if(tournament[k].fitness > secondBest.fitness){
                        secondBest = tournament[k];
                    }
                }
                newPopulation[j++] = best.crossoverTree(secondBest, crossoverChance);
                if(j == populationSize) break;
                newPopulation[j] = best.copyTree();
                newPopulation[j].mutateTree(mutationChance);
            }
            population = newPopulation;

            //get the best from the population
            GPInitial best = population[0];
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness > best.fitness){
                    best = population[j];
                }
            }
            System.out.println("Best fitness: " + best.fitness);
            double[] stats = best.playAndGetStatistics(100);
            System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Number of wins: " + stats[2]);
        }
    }
    
}
