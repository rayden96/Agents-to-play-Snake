
import java.util.Random;

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

    Snake snake;
    int boardSize;

    double[] averageScoreAndMoves = new double[2];

    Random rand;

    //PSO variables
    double[] bestWeights = new double[hiddenLayerSize * (inputLayerSize + 1) + outputLayerSize * (hiddenLayerSize + 1)];
    double bestFitness = 0;

    public NN(double[] weights, int boardSize, Random rand){
        this.weights = weights;
        this.boardSize = boardSize;
        this.rand = rand;
    }

    public void initializeNN(){

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
        //pay 100 games and return the mean score over those games.

        for (int i = 0; i < 100; i++){
            snake = new Snake(boardSize, rand);
            
            initializeNN();
            char direction = getDirection();

            //play the game
            while (snake.move(direction) == 'C'){
                initializeNN();
                direction = getDirection();
            }
            averageScoreAndMoves[0] += snake.score;
            averageScoreAndMoves[1] += snake.numMoves;
        }//potentially parallelize this
        averageScoreAndMoves[0] /= 100;
        averageScoreAndMoves[1] /= 100;

        if(averageScoreAndMoves[0] > bestFitness){
            bestFitness = averageScoreAndMoves[0];
            bestWeights = weights;
        }

        return averageScoreAndMoves[0];
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
}
