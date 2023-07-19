
import java.util.Random;
import java.util.stream.IntStream;

public class PSOTrainedNN {
    //using PSO, train the NN to find the optimal one.

    NN[] population;
    int populationSize;
    int boardSize;
    NN bestNN;
    double bestFitness = 0;
    Random rand;
    int fanin = 11;
    
    double c1 = 1.42;
    double c2 = 1.42;
    double w = 0.72;

    public PSOTrainedNN(int populationSize, int boardSize, Random rand){
        this.populationSize = populationSize;
        this.boardSize = boardSize;
        this.rand = rand;
        population = new NN[populationSize];
        
        //initialize the population, each NN has random weights within the range [-1/sqrt(fanin), 1/sqrt(fanin)]
        IntStream.range(0, populationSize).parallel().forEach(i -> {
            double[] weights = new double[10 * (10 + 1) + 4 * (10 + 1)];
            for (int j = 0; j < weights.length; j++){
                weights[j] = rand.nextDouble() * 2 / Math.sqrt(fanin) - 1 / Math.sqrt(fanin);
            }
            population[i] = new NN(weights, boardSize, rand);
        });
    }

    public void train(int iterations){
        //get the best NN from the population
        IntStream.range(0, populationSize).parallel().forEach(i -> {
            population[i].getFitness();
        });
        //initialize global best position
        for (int i = 0; i < populationSize; i++){
            if (population[i].averageScoreAndMoves[0] > bestFitness){
                bestFitness = population[i].averageScoreAndMoves[0];
                bestNN = population[i];
            }
        }

        //run the iterations:
        for(int i = 0; i< iterations; i++){
            //loop through each NN in parallel
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                //get the weights of the NN
                double[] weights = population[j].weights;

                //set the velocity of the NN for each weight in a float array called velocities
                double[] velocities = new double[weights.length];
                for (int k = 0; k < weights.length; k++){
                    velocities[k] = w * weights[k] + c1 * rand.nextDouble() * (bestNN.weights[k] - weights[k]) + c2 * rand.nextDouble() * (population[j].bestWeights[k] - weights[k]);
                }

                //update the weights of the NN
                for (int k = 0; k < weights.length; k++){
                    weights[k] += velocities[k];
                }
                population[j].weights = weights;
            });

            //get the best NN from the population
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                population[j].getFitness();
            });
            //initialize global best position
            for (int j = 0; j < populationSize; j++){
                if (population[j].averageScoreAndMoves[0] > bestFitness){
                    bestFitness = population[j].averageScoreAndMoves[0];
                    bestNN = population[j];
                }
            }
        }

        //finished training - we now have the best NN.
    }
    
}
