import java.util.Random;
import java.util.stream.IntStream;

public class GPRunnerSecond {

    GPSecond[] population;
    GPSecond[] tournament;
    double[] fitness;
    int populationSize;
    int tournamentSize;

    int generations;

    Random rand;

    int boardSize;

    int crossoverChance;
    int mutationChance;
    int initialGenChance;

    GPSecond bestTree;
    double bestFitness = 1000000;

    public GPRunnerSecond(int populationSize, int generations, int tournamentSize, int MaxDepth, int boardSize, Random rand, int crossoverChance, int mutationChance, int initialGenChance) {
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.generations = generations;
        this.boardSize = boardSize;
        this.rand = rand;
        this.crossoverChance = crossoverChance;
        this.mutationChance = mutationChance;
        this.initialGenChance = initialGenChance;
        population = new GPSecond[populationSize];
        fitness = new double[populationSize];
        for(int i = 0; i < populationSize; i++){
            Random newRand = new Random(i);
            population[i] = new GPSecond(MaxDepth, newRand, boardSize, this.initialGenChance);
        }
        tournament = new GPSecond[tournamentSize];

       
    }

    public GPSecond run(){
        int sampleSeed = rand.nextInt(generations);
        IntStream.range(0, populationSize).parallel().forEach(j -> {
            fitness[j] = population[j].playAndGetFitness(100, sampleSeed);
        });
        // for(int j = 0; j < populationSize; j++){
        //     fitness[j] = population[j].playAndGetFitness(100);
        // }
        for(int i = 0; i < generations; i++){
            
            //System.out.println("Generation " + (i+1));
            GPSecond[] newPopulation = new GPSecond[populationSize];

            //copy best tree
            //find the best
            GPSecond beste = population[0].copyTree();
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness < beste.fitness){
                    beste = population[j].copyTree();
                    beste.fitness = population[j].fitness;
                }
            }
            newPopulation[0] = beste.copyTree();

            //for 5% of population, add random trees using tournament selection
            for(int j = 1; j < populationSize / 20; j++){
                newPopulation[j] = population[Math.abs(rand.nextInt()) % populationSize].copyTree();
            }

            for(int j = populationSize/20; j < populationSize; j++){
                //select the tournament
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[Math.abs(rand.nextInt()) % populationSize];
                }
                //find the best and second best
                GPSecond best = tournament[0];
                GPSecond secondBest = tournament[1];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        secondBest = best;
                        best = tournament[k];
                    }
                    else if(tournament[k].fitness < secondBest.fitness){
                        secondBest = tournament[k];
                    }
                }
                best = best.copyTree();
                secondBest = secondBest.copyTree();
                newPopulation[j++] = best.crossoverTree(secondBest, crossoverChance);
                if(j == populationSize) break;
                
                //get a new tournament and copy over a mutated tree
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[Math.abs(rand.nextInt()) % populationSize];
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

            int sampleSeed2 = rand.nextInt(generations);
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                fitness[j] = population[j].playAndGetFitness(100, sampleSeed2);
            });
            // for(int j = 0; j < populationSize; j++){
            //     fitness[j] = population[j].playAndGetFitness(100);
            // }
            //get the best from the population
            GPSecond best = population[0];
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

            //double[] stats = best.playAndGetStatistics(100);
            // System.out.println("Best Tree from this generation: ");
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);


            // System.out.println("Best Tree so far: ");
            //stats = bestTree.playAndGetStatistics(100);
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);
            //System.out.println(best.printTree());
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
        return bestTree;
    }

    public GPSecond runForThirdInstance(){
        int sampleSeed = rand.nextInt(generations);
        IntStream.range(0, populationSize).parallel().forEach(j -> {
            fitness[j] = population[j].playAndGetFitness(400, sampleSeed);
        });
        // for(int j = 0; j < populationSize; j++){
        //     fitness[j] = population[j].playAndGetFitness(100);
        // }
        for(int i = 0; i < generations/10; i++){
            
            //System.out.println("Generation " + (i+1));
            GPSecond[] newPopulation = new GPSecond[populationSize];

            for(int j = 0; j < populationSize; j++){
                tournament = new GPSecond[tournamentSize];
                //select the tournament
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[Math.abs(rand.nextInt()) % populationSize];
                }
                //find the best and second best
                GPSecond best = tournament[0];
                GPSecond secondBest = tournament[1];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        secondBest = best;
                        best = tournament[k];
                    }
                    else if(tournament[k].fitness < secondBest.fitness){
                        secondBest = tournament[k];
                    }
                }
                best = best.copyTree();
                secondBest = secondBest.copyTree();
                int thresh = rand.nextInt(5);
                for(int x = 0; x<thresh; x++){
                    best = best.crossoverTree(secondBest, crossoverChance);
                }
                newPopulation[j++] = best;
                if(j == populationSize) break;
                
                //get a new tournament and copy over a mutated tree
                for(int k = 0; k < tournamentSize; k++){
                    tournament[k] = population[Math.abs(rand.nextInt()) % populationSize];
                }
                //find the best
                best = tournament[0];
                for(int k = 1; k < tournamentSize; k++){
                    if(tournament[k].fitness < best.fitness){
                        best = tournament[k];
                    }
                }
                newPopulation[j] = best.copyTree();
                thresh = rand.nextInt(5);
                for(int x = 0; x<thresh; x++){
                    newPopulation[j].mutateTree(mutationChance);
                }
                

            }
            population = newPopulation;

            int sampleSeed2 = rand.nextInt(generations);
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                fitness[j] = population[j].playAndGetFitness(400, sampleSeed2);
            });
            // for(int j = 0; j < populationSize; j++){
            //     fitness[j] = population[j].playAndGetFitness(100);
            // }
            //get the best from the population
            GPSecond best = population[0];
            for(int j = 1; j < populationSize; j++){
                if(population[j].fitness < best.fitness){
                    best = population[j].copyTree();
                    best.fitness = population[j].fitness;
                }
            }
            
            if(best.fitness < bestFitness ){
                bestFitness = best.fitness;
                this.bestTree = best.copyTree();
            }

            //double[] stats = best.playAndGetStatistics(500);
            // System.out.println("Best Tree from this generation: ");
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);


            // System.out.println("Best Tree so far: ");
            //stats = bestTree.playAndGetStatistics(500);
            // System.out.println("Average score: " + stats[0] + " Average moves: " + stats[1] + " Games won: " + stats[2]);
            //System.out.println(best.printTree());
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
        return bestTree;
    }
    
}
