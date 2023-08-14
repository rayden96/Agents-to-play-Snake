import java.util.Random;
import java.util.stream.IntStream;

public class GPRunnerInitial {

    GPInitial[] population;
    GPInitial[] tournament;
    double[] fitness;
    int populationSize;
    int tournamentSize;

    int generations;

    Random rand;

    int boardSize;

    int crossoverChance = 10;
    int mutationChance = 10;

    GPInitial bestTree;
    double bestFitness = 1000000;

    Random[] randArray;

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

        //assign randArray with random seeds
        randArray = new Random[populationSize];
        for(int i = 0; i < 100; i++){
            randArray[i] = new Random(rand.nextInt());
        }
    }

    public GPInitial run(){
        IntStream.range(0, populationSize).parallel().forEach(j -> {
            fitness[j] = population[j].playAndGetFitness(100, randArray[j]);
        });
        for(int i = 0; i < generations; i++){
            
            System.out.println("Generation " + (i+1));
            GPInitial[] newPopulation = new GPInitial[populationSize];

            //for 5% of population, run tournament selection and just copy the winner
            for(int j = 0; j < populationSize / 20; j++){
                //select the tournament
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[rand.nextInt(populationSize)];
                }
                //find the best
                GPInitial best = tournament[0];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        best = tournament[k];
                    }
                }
                newPopulation[j] = best.copyTree();
            }

            for(int j = populationSize/20; j < populationSize; j++){
                //select the tournament
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[rand.nextInt(populationSize)];
                }
                //find the best and second best
                GPInitial best = tournament[0];
                GPInitial secondBest = tournament[1];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        secondBest = best;
                        best = tournament[k];
                    }
                    else if(tournament[k].fitness < secondBest.fitness){
                        secondBest = tournament[k];
                    }
                }
                newPopulation[j++] = best.crossoverTree(secondBest, crossoverChance);
                if(j == populationSize) break;
                
                //get a new tournament and copy over a mutated tree
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[rand.nextInt(populationSize)];
                }
                //find the best
                best = tournament[0];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        best = tournament[k];
                    }
                }
                newPopulation[j] = best.copyTree();
                newPopulation[j].mutateTree(mutationChance);

            }
            population = newPopulation;

            IntStream.range(0, populationSize).parallel().forEach(j -> {
                fitness[j] = population[j].playAndGetFitness(100, randArray[j]);
            });
            //get the best from the population
            GPInitial best = population[0];
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness < best.fitness){
                    best = population[j].copyTree();
                    best.fitness = population[j].fitness;
                }
            }

            System.out.println(best.fitness);
            System.out.println(bestFitness);
            
            if(best.fitness < bestFitness ){
                bestFitness = best.fitness;
                this.bestTree = best.copyTree();
            }

            double[] stats = best.playAndGetStatistics(100);
            System.out.println("Best Tree from this generation: ");
            System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);


            System.out.println("Best Tree so far: ");
            stats = bestTree.playAndGetStatistics(100);
            System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);
            // System.out.println(best.printTree());
            // //wait two seconds
            // try{
            //     Thread.sleep(2000);
            // }
            // catch(Exception e){
            //     System.out.println("Error");
            // }
        }
        return bestTree;
    }
    
}
