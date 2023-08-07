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
                //create an array that stores the indexes of increments in descending order
                int[] sotredDensities = new int[4];
                int[] listInOrder = new int[4];
                sotredDensities[0] = 0;
                sotredDensities[1] = 1;
                sotredDensities[2] = 2;
                sotredDensities[3] = 3;
                for(int j = 0; j < 4; j++){
                    for(int k = j+1; k < 4; k++){
                        if(densities[sotredDensities[j]] > densities[sotredDensities[k]]){
                            int temp = sotredDensities[j];
                            sotredDensities[j] = sotredDensities[k];
                            sotredDensities[k] = temp;
                        }
                    }
                }

                if(densities[sotredDensities[0]] == densities[sotredDensities[1]]){
                    if(densities[sotredDensities[1]] == densities[sotredDensities[2]]){
                        if(densities[sotredDensities[2]] == densities[sotredDensities[3]]){
                            //all the same, fill ListInOrder randomly
                            listInOrder[0] = sotredDensities[rand.nextInt(4)];
                            while(listInOrder[1] == listInOrder[0]){
                                listInOrder[1] = sotredDensities[rand.nextInt(4)];
                            }
                            while(listInOrder[2] == listInOrder[0] || listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sotredDensities[rand.nextInt(4)];
                            }
                            while(listInOrder[3] == listInOrder[0] || listInOrder[3] == listInOrder[1] || listInOrder[3] == listInOrder[2]){
                                listInOrder[3] = sotredDensities[rand.nextInt(4)];
                            }
                        }
                        else{
                            //3 are the same, fill listInOrder with the 3 that are the same
                            listInOrder[0] = sotredDensities[rand.nextInt(3)];
                            while(listInOrder[1] == listInOrder[0]){
                                listInOrder[1] = sotredDensities[rand.nextInt(3)];
                            }
                            while(listInOrder[2] == listInOrder[0] || listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sotredDensities[rand.nextInt(3)];
                            }
                            listInOrder[3] = sotredDensities[3];
                        }
                            
                    }
                    else{
                        //2 are the same, fill listInOrder with the 2 that are the same
                        listInOrder[0] = sotredDensities[rand.nextInt(2)];
                        while(listInOrder[1] == listInOrder[0]){
                            listInOrder[1] = sotredDensities[rand.nextInt(2)];
                        }
                        if(sotredDensities[2] == sotredDensities[3]){
                            if(rand.nextInt(2) > 0){
                                listInOrder[2] = sotredDensities[2];
                                listInOrder[3] = sotredDensities[3];
                            }
                            else {
                                listInOrder[2] = sotredDensities[3];
                                listInOrder[3] = sotredDensities[2];
                            }
                        }
                        else{
                            listInOrder[2] = sotredDensities[2];
                            listInOrder[3] = sotredDensities[3];
                        }
                    }
                }
                else{
                    listInOrder[0] = sotredDensities[0];
                    if(sotredDensities[1] == sotredDensities[2]){
                        if(sotredDensities[2] == sotredDensities[3]){
                            //next three the same, fill randomly
                            listInOrder[1] = sotredDensities[rand.nextInt(3)+1];
                            while(listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sotredDensities[rand.nextInt(3)+1];
                            }
                            while(listInOrder[3] == listInOrder[1] || listInOrder[3] == listInOrder[2]){
                                listInOrder[3] = sotredDensities[rand.nextInt(3)+1];
                            }
                        }
                        else{
                            //next two the same, fill listInOrder with the 2 that are the same
                            listInOrder[1] = sotredDensities[rand.nextInt(2)+1];
                            while(listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sotredDensities[rand.nextInt(2)+1];
                            }
                            listInOrder[3] = sotredDensities[3];
                        }
                    }
                    else{
                        listInOrder[1] = sotredDensities[1];
                        if(sotredDensities[2] == sotredDensities[3]){
                            if(rand.nextInt(2) > 0){
                                listInOrder[2] = sotredDensities[2];
                                listInOrder[3] = sotredDensities[3];
                            }
                            else {
                                listInOrder[2] = sotredDensities[3];
                                listInOrder[3] = sotredDensities[2];
                            }
                        }
                        else{
                            listInOrder[2] = sotredDensities[2];
                            listInOrder[3] = sotredDensities[3];
                        }
                    }
                }
            
                if(snake.nextMoveLosesGame(getDirection(listInOrder, 0))){
                    if(snake.nextMoveLosesGame(getDirection(listInOrder, 1))){
                        if(snake.nextMoveLosesGame(getDirection(listInOrder, 2))){
                            if(snake.nextMoveLosesGame(getDirection(listInOrder, 3))){
                                directionChar = getDirection(listInOrder, 0);
                            }
                            else{
                                directionChar = getDirection(listInOrder, 3);
                            }
                        }
                        else{
                            directionChar = getDirection(listInOrder, 2);
                        }
                    }
                    else{
                        directionChar = getDirection(listInOrder, 1);
                    }
                }
                else{
                    directionChar = getDirection(listInOrder, 0);
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

    public char getDirection(int[] sortedIncrements, int val){
        char directionChar = ' ';
        if(sortedIncrements[val] == 0){
            directionChar = 'U';
        }
        if(sortedIncrements[val] == 1){
            directionChar = 'R';
        }
        if(sortedIncrements[val] == 2){
            directionChar = 'D';
        }
        if(sortedIncrements[val] == 3){
            directionChar = 'L';
        }
        return directionChar;
    }

    //play games with moves based on the lowest density score for each move
    
}
