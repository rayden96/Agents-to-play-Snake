
import java.util.Random;
import java.util.stream.IntStream;

public class NN {
    //the nueral network to play and learn snake
    //the input layer is 10 neurons, the hidden layer is 10 neurons and the output layer is 4 neurons, there is 1 bias neuron in the input layer and 1 bias neuron in the hidden layer which will always be -1
    //hyperbolic tangent is the activation function for the hidden layer and the output layer

    public int inputLayerSize = 10;
    public int hiddenLayerSize = 10;
    public int outputLayerSize = 4;

    public double[] weights = new double[hiddenLayerSize * (inputLayerSize + 1) + outputLayerSize * (hiddenLayerSize + 1)];

    public double[] inputLayer = new double[inputLayerSize + 1];
    public double[] hiddenLayer = new double[hiddenLayerSize + 1];
    public double[] outputLayer = new double[outputLayerSize];

    public double[] velocities = new double[hiddenLayerSize * (inputLayerSize + 1) + outputLayerSize * (hiddenLayerSize + 1)];

    int boardSize;

    double[] averageScoreAndMoves;

    Random rand;

    //PSO variables
    double[] bestWeights = new double[hiddenLayerSize * (inputLayerSize + 1) + outputLayerSize * (hiddenLayerSize + 1)];
    double bestFitness = -1;

    int numGames = 100;

    public NN(double[] weights, int boardSize, Random rand){
        this.weights = weights;
        this.boardSize = boardSize;
        this.rand = rand;
        for(int i = 0; i < velocities.length; i++){
            velocities[i] = 0;
        }
    }

    //full deep copy of an NN
    public NN(NN nn){
        this.rand = nn.rand;
        this.weights = nn.cloneWeights();
        this.boardSize = nn.boardSize;
        this.averageScoreAndMoves = new double[3];

        this.averageScoreAndMoves[0] = nn.averageScoreAndMoves[0];
        this.averageScoreAndMoves[1] = nn.averageScoreAndMoves[1];
        this.averageScoreAndMoves[2] = nn.averageScoreAndMoves[2];
    }

    public void initializeNN(Snake snake){

        //set the input layer
        double[] input = snake.getFeatureInput();
        for (int i = 0; i < inputLayerSize; i++){
            inputLayer[i] = input[i];
        }
        inputLayer[inputLayerSize] = -1;

        //set the hidden layer
        for (int i = 0; i < hiddenLayerSize; i++){
            hiddenLayer[i] = 0;
            for (int j = 0; j < inputLayerSize + 1; j++){
                hiddenLayer[i] += weights[i * (inputLayerSize + 1) + j] * inputLayer[j];
            }
            hiddenLayer[i] = Math.tanh(hiddenLayer[i]);
        }

        //set the output layer
        for (int i = 0; i < outputLayerSize; i++){
            outputLayer[i] = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++){
                outputLayer[i] += weights[hiddenLayerSize * (inputLayerSize + 1) + i * (hiddenLayerSize + 1) + j] * hiddenLayer[j];
            }
            outputLayer[i] = Math.tanh(outputLayer[i]);
        }
    }

    public char getDirection(){
        //get the direction from the NN, hiddenLayer[0] = up, hiddenLayer[1] = down, hiddenLayer[2] = left, hiddenLayer[3] = right
        //NN already initialized
        char direction = 'U';
        double max = outputLayer[0];
        if( outputLayer[1] > max){
            direction = 'D';
            max = outputLayer[1];
        }
        if( outputLayer[2] > max){
            direction = 'L';
            max = outputLayer[2];
        }
        if( outputLayer[3] > max){
            direction = 'R';
            max = outputLayer[3];
        }
        return direction;
    }

    public double getFitness(){
        numGames = 100;
        this.averageScoreAndMoves = new double[3];
        //pay 100 games and return the mean score over those games.
        this.averageScoreAndMoves[0] = 0;
        this.averageScoreAndMoves[1] = 0;
        this.averageScoreAndMoves[2] = 0;
            
        for (int i = 0; i < numGames; i++){
            Snake snake1 = new Snake(boardSize, rand);
            
            initializeNN(snake1);
            char direction = getDirection();
            char state = snake1.move(direction);
            //play the game
            while (state != 'X' && state != 'W'){
                //snake.printBoard();
                initializeNN(snake1);
                direction = getDirection();
                
                state = snake1.move(direction);
            }
            this.averageScoreAndMoves[0] += snake1.score;
            this.averageScoreAndMoves[1] += snake1.numMoves;
            if (snake1.score == snake1.boardSize * snake1.boardSize - 1){
                this.averageScoreAndMoves[2]++;
            }
        }
        this.averageScoreAndMoves[0] /= numGames;
        this.averageScoreAndMoves[1] /= numGames;


        //update the best fitness
        if(this.averageScoreAndMoves[0] > bestFitness){
            bestFitness = this.averageScoreAndMoves[0];
            bestWeights = cloneWeights();
        }
        return averageScoreAndMoves[0];
    }

    public double[] getFitness2(Random randToUse){
        //pay 100 games and return the mean score over those games.

        this.averageScoreAndMoves = new double[3];
        
        this.averageScoreAndMoves[0] = 0;
        this.averageScoreAndMoves[1] = 0;
        this.averageScoreAndMoves[2] = 0;

        for (int i = 0; i < 100; i++){
            Snake snake1 = new Snake(boardSize, randToUse);
            
            initializeNN(snake1);
            char direction = getDirection();
            //snake1.printBoard();
            char state = snake1.move(direction);
            //play the game
            while (state != 'X' && state != 'W'){
                //wait 1 second
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                initializeNN(snake1);
                direction = getDirection();
               //snake1.printBoard();
                state = snake1.move(direction);
                //System.out.println("snake moved " + direction + " and the state is " + state);
            }
            // System.out.println();
            // System.out.println();
            // snake1.printBoard();
            // System.out.println();
            // System.out.println("Game Over");

            this.averageScoreAndMoves[0] += snake1.score;
            this.averageScoreAndMoves[1] += snake1.numMoves;
            if (snake1.score == snake1.boardSize * snake1.boardSize - 1){
                this.averageScoreAndMoves[2]++;
            }
        }//potentially parallelize this
        this.averageScoreAndMoves[0] /= 100;
        this.averageScoreAndMoves[1] /= 100;

        return this.averageScoreAndMoves;
    }

    public double[] returnOutput(){
        return averageScoreAndMoves;
    }

    public void setWeights(double[] weights){
        this.weights = weights;
    }

    public double[] getWeights(){
        return weights;
    }

    //clone copy the weights
    public double[] cloneWeights(){
        double[] clone = new double[weights.length];
        for (int i = 0; i < weights.length; i++){
            clone[i] = weights[i];
        }
        return clone;
    }
}
