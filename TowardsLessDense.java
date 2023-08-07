import java.util.Random;

public class TowardsLessDense {

    int boardSize;
    Random rand;

    double[] averageScoreAndMoves = new double[3];

    //constructor

    TowardsLessDense(int boardSize, Random rand){
        this.boardSize = boardSize;
        this.rand = rand;
    }

    //this agent gets the feature vector from the board and uses it to make a decision
    //It determines the best direction by calculating a density score for each direction
    //the density score is the number of obstacles in the cells in the direction of movement and their neighbors
    //for example the density for up is the number of obstacles in the cells above the snake's head, above and to the right, and above and to the left
    //the direction with the lowest density score is chosen
    //the density is decremented if the snake will move closer to food
    //if there is a tie, a direction is chosen randomly from the tied directions
    //if the snake will die in the next move, the next lowest density score is chosen unless all moves will kill the snake
    //if all moves will kill the snake, choose the lowest density

    double[] run(int numGames){
        averageScoreAndMoves = new double[3];
        averageScoreAndMoves[0] = 0;
        averageScoreAndMoves[1] = 0;
        averageScoreAndMoves[2] = 0;
            char directionChar = ' ';
        
        for(int i = 0; i<numGames; i++){
            Snake snake = new Snake(boardSize, rand);
            char flag = 'C';
            while(flag != 'X' && flag != 'W'){
                //snake.printBoard();
                double[] featureVector = snake.getFeatureInput();
                double[] densities = calculateDensities(featureVector, snake.foodX, snake.foodY, snake.snakeHeadX, snake.snakeHeadY);
                //get the lowest density from the array, if there is a tie, choose randomly from the tied directions
                double lowestDensity = 100;
                int[] lowestDensityIndices = new int[4];
                int numTied = 0;
                for(int j = 0; j<4; j++){
                    if(densities[j] < lowestDensity){
                        lowestDensity = densities[j];
                        lowestDensityIndices[0] = j;
                        numTied = 1;
                    }
                    else if(densities[j] == lowestDensity){
                        lowestDensityIndices[numTied] = j;
                        numTied++;
                    }
                }
                int direction = lowestDensityIndices[rand.nextInt(numTied)];
                
                if(direction == 0){
                    directionChar = 'U';
                }
                else if(direction == 1){
                    directionChar = 'R';
                }
                else if(direction == 2){
                    directionChar = 'D';
                }
                else if(direction == 3){
                    directionChar = 'L';
                }
                //check if next move will kill the snake, if it does, choose the next lowest density unless all moves will kill the snake
                if(snake.nextMoveLosesGame(directionChar)){
                    //check if all moves will kill the snake
                    boolean allMovesLose = true;
                    for(int j = 0; j<4; j++){
                        if(!snake.nextMoveLosesGame(directionChar)){
                            allMovesLose = false;
                        }
                    }
                    if(allMovesLose){
                        direction = lowestDensityIndices[0];
                    }
                    else{
                        //find the next lowest density
                        double nextLowestDensity = 100;
                        int[] nextLowestDensityIndices = new int[4];
                        int numNextTied = 0;
                        for(int j = 0; j<4; j++){
                            if(densities[j] < nextLowestDensity && densities[j] > lowestDensity){
                                nextLowestDensity = densities[j];
                                nextLowestDensityIndices[0] = j;
                                numNextTied = 1;
                            }
                            else if(densities[j] == nextLowestDensity){
                                nextLowestDensityIndices[numNextTied] = j;
                                numNextTied++;
                            }
                        }
                        direction = nextLowestDensityIndices[rand.nextInt(numNextTied)];

                    }
                    if(direction == 0){
                        directionChar = 'U';
                    }
                    else if(direction == 1){
                        directionChar = 'R';
                    }
                    else if(direction == 2){
                        directionChar = 'D';
                    }
                    else if(direction == 3){
                        directionChar = 'L';
                    }
                }
                //System.out.println("Densities: " + densities[0] + " " + densities[1] + " " + densities[2] + " " + densities[3] + " " + " Direction: " + directionChar);
                flag = snake.move(directionChar);
            }
            averageScoreAndMoves[0] += snake.score;
            averageScoreAndMoves[1] += snake.numMoves;
            if (snake.score == snake.boardSize * snake.boardSize - 1){
                averageScoreAndMoves[2]++;
            }
        }
        averageScoreAndMoves[0] = averageScoreAndMoves[0]/numGames;
        averageScoreAndMoves[1] = averageScoreAndMoves[1]/numGames;



        return averageScoreAndMoves;
    }

    double[] calculateDensities(double[] featureVector, int foodX, int foodY, int snakeX, int snakeY){
        double[] ret = new double[4];
        ret[0] = 0;
        ret[1] = 0;
        ret[2] = 0;
        ret[3] = 0;

        if(featureVector[0]<0){//top left
            ret[0]++;
            ret[3]++;
        }
        if(featureVector[1]<0){ //top
            ret[0]++;
        }
        if(featureVector[2]<0){ //top right
            ret[0]++;
            ret[1]++;
        }
        if(featureVector[3]<0){
            ret[1]++;
        }
        if(featureVector[4]<0){
            ret[1]++;
            ret[2]++;
        }
        if(featureVector[5]<0){
            ret[2]++;
        }
        if(featureVector[6]<0){
            ret[2]++;
            ret[3]++;
        }
        if(featureVector[7]<0){
            ret[3]++;
        }

        //System.out.println("Feature Vector: " + featureVector[0] + " " + featureVector[1] + " " + featureVector[2] + " " + featureVector[3] + " " + featureVector[4] + " " + featureVector[5] + " " + featureVector[6] + " " + featureVector[7] + "");

        //calculate euclidean distance to food

        //decrement density if moving in that direction will move the snake closer to food
        if(featureVector[8] == 1){
            ret[0]--;
        }
        if( featureVector[9] == -1){
            ret[1]--;
        }
        if(featureVector[8] == -1){
            ret[2]--;
        }
        if(featureVector[9] == 1){
            ret[3]--;
        }

        return ret;

    }

    //play games with moves based on the lowest density score for each move
    
}
