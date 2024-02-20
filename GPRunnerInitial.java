import java.util.Random;
import java.util.stream.IntStream;

public class GPRunnerInitial {

    GPInitial[] population;
    //GPInitial[] tournament;
    double[] fitness;
    int populationSize;
    int tournamentSize;

    int generations;

    Random rand;

    int boardSize;

    int crossoverChance;
    int mutationChance;
    int initialGenChance;

    GPInitial bestTree;
    double bestFitness = 1000000;

    public GPRunnerInitial(int populationSize, int generations, int tournamentSize, int MaxDepth, int boardSize, Random rand, int crossoverChance, int mutationChance, int initialGenChance) {
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.generations = generations;
        this.boardSize = boardSize;
        this.rand = rand;
        this.crossoverChance = crossoverChance;
        this.mutationChance = mutationChance;
        this.initialGenChance = initialGenChance;
        population = new GPInitial[populationSize];
        fitness = new double[populationSize];
        for(int i = 0; i < populationSize; i++){
            Random newRand = new Random(rand.nextInt());
            population[i] = new GPInitial(MaxDepth, newRand, boardSize, this.initialGenChance);
        }
    }

    public GPInitial run(){
        int sampleSeed = rand.nextInt(generations);
        IntStream.range(0, populationSize).parallel().forEach(j -> {
            fitness[j] = population[j].playAndGetFitness(100, sampleSeed);
        });
        // for(int j = 0; j < populationSize; j++){
        //     fitness[j] = population[j].playAndGetFitness(100);
        // }
        for(int i = 0; i < generations; i++){
            
            //System.out.println("Generation " + (i+1));
            GPInitial[] newPopulation = new GPInitial[populationSize];

            //get the best
            GPInitial best = population[0].copyTree();
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness < best.fitness){
                    best = population[j].copyTree();
                    best.fitness = population[j].fitness;
                }
            }
            newPopulation[0] = best.copyTree();

            // //for 5% of population, run tournament selection and just copy the winner
            // int size = populationSize / 20;
            // for(int j = 1; j < size; j++){
            //     //copy a random tree
            //     newPopulation[j] = population[tourneyRand.nextInt() % populationSize].copyTree();
            // }

            for(int j = 1; j < populationSize; j++){
                //select the tournament
                GPInitial[] tournament = new GPInitial[tournamentSize];
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[rand.nextInt(populationSize)].copyTree();
                }
                //find the best and second best
                best = tournament[0].copyTree();
                //get the best
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        best = tournament[k].copyTree();
                    }
                }
                best = best.copyTree();
                //select a new tournament and get the second best
                tournament = null;
                tournament = new GPInitial[tournamentSize];
                for(int k = 0; k < tournamentSize; k++){
                    
                    tournament[k] = population[rand.nextInt(populationSize)].copyTree();
                }
                GPInitial secondBest = tournament[0].copyTree();
                //get the best
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < secondBest.fitness){
                        secondBest = tournament[k].copyTree();
                    }
                }
                //crossover the two best and copy over the new tree
                secondBest = secondBest.copyTree();
                newPopulation[j++] = best.crossoverTree(secondBest, crossoverChance);
                if(j == populationSize) break;
                
                //get a new tournament and copy over a mutated tree
                tournament = null;
                tournament = new GPInitial[tournamentSize];
                for(int k = 0; k < tournamentSize; k++){
                    
                    tournament[k] = population[rand.nextInt(populationSize)].copyTree();
                }
                //find the best
                best = null;
                best = tournament[0].copyTree();
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        best = tournament[k].copyTree();
                    }
                }
                newPopulation[j] = best.copyTree();
                newPopulation[j].mutateTree(mutationChance);

                
                
            }
            population = newPopulation;

            int sampleSeed2 = rand.nextInt(generations);
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                fitness[j] = population[j].playAndGetFitness(100, sampleSeed2);
            });
            // for(int j = 0; j < populationSize; j++){
            //     fitness[j] = population[j].playAndGetFitness(100);
            // }
            //get the best from the population
            best = population[0];
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness < best.fitness){
                    best = population[j].copyTree();
                    best.fitness = population[j].fitness;
                }
            }

            // System.out.println(best.fitness);
            // System.out.println(bestFitness);
            
            if(best.fitness < bestFitness ){
                bestFitness = best.fitness;
                this.bestTree = best.copyTree();
            }

            // double[] stats = best.playAndGetStatistics(100);
            // System.out.println("Best Tree from this generation: ");
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);
            // for(int q = 0; q < populationSize; q++){
            //         System.out.println(population[q].printTree());
            //     }
            //     System.out.println();


            // System.out.println("Best Tree so far: ");
            // stats = bestTree.playAndGetStatistics(100);
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);
            // System.out.println(best.printTree());
            // //wait two seconds
            // try{
            //     Thread.sleep(2000);
            // }
            // catch(Exception e){
            //     System.out.println("Error");
            // }
            //print out percentage complete of loop in same line
            System.out.print("\r" + (int)(((double)i / generations) * 100) + "%");
        }
        // for(int i = 0; i < populationSize; i++){
        //     System.out.println(population[i].printTree());
        // }
        return bestTree;
    }
    
}
