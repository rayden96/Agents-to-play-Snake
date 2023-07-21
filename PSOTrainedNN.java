
import java.util.Random;
import java.util.stream.IntStream;

public class PSOTrainedNN {
    //using PSO, train the NN to find the optimal one.

    NN[] population;
    int populationSize;
    int boardSize;
    double[] bestNNWeights;
    NN bestNN;
    double bestFitness = -1;
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
        bestNNWeights = new double[10 * (10 + 1) + 4 * (10 + 1)];
        
        
        //initialize the population, each NN has random weights within the range [-1/sqrt(fanin), 1/sqrt(fanin)]
        for (int i = 0; i < populationSize; i++){
            double[] weights = new double[10 * (10 + 1) + 4 * (10 + 1)];
            for (int j = 0; j < weights.length; j++){
                weights[j] = rand.nextDouble() * 2 / Math.sqrt(fanin) - 1 / Math.sqrt(fanin);
            }
            Random randForNN = new Random(rand.nextInt());
            population[i] = new NN(weights, boardSize, randForNN);
        }

        System.out.println("Initialized population");
    }

    public NN train(int iterations){

        System.out.println("Starting Training phase");
        //get the best NN from the population
        IntStream.range(0, populationSize).parallel().forEach(i -> {
            population[i].getFitness();
        });
        //initialize global best position
        for (int i = 0; i < populationSize; i++){
            if (population[i].averageScoreAndMoves[0] > bestFitness){
                bestFitness = population[i].averageScoreAndMoves[0];
                bestNNWeights = population[i].cloneWeights();
                bestNN = new NN(population[i]);
            }
        }

        //run the iterations:
        for(int i = 0; i< iterations; i++){
            System.out.println("Iteration: " + i + " Best NN average score: " + bestNN.averageScoreAndMoves[0] + " and average moves: " + bestNN.averageScoreAndMoves[1] + " and games won: " + bestNN.averageScoreAndMoves[2]);
            //loop through each NN in parallel and update the weights
            IntStream.range(0, populationSize).parallel().forEach(j -> {
                //get the weights of the NN
                double[] weights = population[j].weights;

                //set the velocity of the NN for each weight in a float array called velocities
                double[] velocities = new double[weights.length];
                for (int k = 0; k < weights.length; k++){
                    velocities[k] = w * weights[k] + c1 * population[j].rand.nextDouble() * (population[j].bestWeights[k]- weights[k]) + c2 * population[j].rand.nextDouble() * (bestNN.weights[k]  - weights[k]);
                    //System.out.println("velocities = " + k + "  " + velocities[k] + " weights" + k + "  " + weights[k]);
                }

                //update the weights of the NN
                for (int k = 0; k < weights.length; k++){
                    //System.out.println("old weights: " + k + "  " + weights[k]);
                    weights[k] += velocities[k];
                   // System.out.println("new weights: " + k + "  " + weights[k]);
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
                    bestNNWeights = population[j].cloneWeights();
                    bestNN = new NN(population[j]);
                }
            }

            if( bestFitness == (boardSize * boardSize) - 1){
                //perfect NN found, return it
                System.out.println("Finished training");
                System.out.println("Best NN average score: " + bestNN.averageScoreAndMoves[0] + " and average moves: " + bestNN.averageScoreAndMoves[1] + " and games won: " + bestNN.averageScoreAndMoves[2]);
                return bestNN;
            }
        }

        //finished training - we now have the best NN.
        System.out.println("Finished training");
        System.out.println("Best NN average score: " + bestNN.averageScoreAndMoves[0] + " and average moves: " + bestNN.averageScoreAndMoves[1] + " and games won: " + bestNN.averageScoreAndMoves[2]);
        return bestNN;
    }
    
}
