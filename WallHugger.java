import java.util.Random;

public class WallHugger {

    //wall hugger hand written agent

    int[] increments;

    Random rand;

    int boardSize;

    public WallHugger(Random rand, int boardSize) {
        increments = new int[4];

        this.rand = rand;
        this.boardSize = boardSize;
    }

    public void calculateIncrements(double[] featureVector){
        increments[0] = 0;
        increments[1] = 0;
        increments[2] = 0;
        increments[3] = 0;

        if(featureVector[0]<0 || featureVector[7]<0){//top left
            increments[0]++;
        }
        if(featureVector[1]<0 || featureVector[2]<0){ //top right
            increments[1]++;
        }
        if(featureVector[3]<0 || featureVector[4]<0){ //bottom right
            increments[2]++;
        }
        if(featureVector[5]<0 || featureVector[6]<0){ //bottom left
            increments[3]++;
        }

        if(featureVector[8] == 1){
            increments[0]++;
        }
        if( featureVector[8] == -1){
            increments[2]++;
        }
        if(featureVector[9] == -1){
            increments[1]++;
        }
        if(featureVector[9] == 1){
            increments[3]++;
        }
    }

    double[] run(int numGames){
        double[] averageScoreAndMoves = new double[3];
        averageScoreAndMoves[0] = 0;
        averageScoreAndMoves[1] = 0;
        averageScoreAndMoves[2] = 0;
        char directionChar = ' ';

        for(int i = 0; i < numGames; i++){
            Snake snake = new Snake(boardSize, rand);

            char flag = 'C';
            while (flag != 'X' && flag != 'W') {
                //snake.printBoard();
                double[] featureVector = snake.getFeatureInput();
                calculateIncrements(featureVector);
                //create an array that stores the indexes of increments in descending order
                int[] sortedIncrements = new int[4];
                int[] listInOrder = new int[4];
                sortedIncrements[0] = 0;
                sortedIncrements[1] = 1;
                sortedIncrements[2] = 2;
                sortedIncrements[3] = 3;
                for(int j = 0; j < 4; j++){
                    for(int k = j+1; k < 4; k++){
                        if(increments[sortedIncrements[j]] < increments[sortedIncrements[k]]){
                            int temp = sortedIncrements[j];
                            sortedIncrements[j] = sortedIncrements[k];
                            sortedIncrements[k] = temp;
                        }
                    }
                }

                if(increments[sortedIncrements[0]] == increments[sortedIncrements[1]]){
                    if(increments[sortedIncrements[1]] == increments[sortedIncrements[2]]){
                        if(increments[sortedIncrements[2]] == increments[sortedIncrements[3]]){
                            //all the same, fill ListInOrder randomly
                            listInOrder[0] = sortedIncrements[rand.nextInt(4)];
                            while(listInOrder[1] == listInOrder[0]){
                                listInOrder[1] = sortedIncrements[rand.nextInt(4)];
                            }
                            while(listInOrder[2] == listInOrder[0] || listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sortedIncrements[rand.nextInt(4)];
                            }
                            while(listInOrder[3] == listInOrder[0] || listInOrder[3] == listInOrder[1] || listInOrder[3] == listInOrder[2]){
                                listInOrder[3] = sortedIncrements[rand.nextInt(4)];
                            }
                        }
                        else{
                            //3 are the same, fill listInOrder with the 3 that are the same
                            listInOrder[0] = sortedIncrements[rand.nextInt(3)];
                            while(listInOrder[1] == listInOrder[0]){
                                listInOrder[1] = sortedIncrements[rand.nextInt(3)];
                            }
                            while(listInOrder[2] == listInOrder[0] || listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sortedIncrements[rand.nextInt(3)];
                            }
                            listInOrder[3] = sortedIncrements[3];
                        }
                            
                    }
                    else{
                        //2 are the same, fill listInOrder with the 2 that are the same
                        listInOrder[0] = sortedIncrements[rand.nextInt(2)];
                        while(listInOrder[1] == listInOrder[0]){
                            listInOrder[1] = sortedIncrements[rand.nextInt(2)];
                        }
                        if(sortedIncrements[2] == sortedIncrements[3]){
                            if(rand.nextInt(2) > 0){
                                listInOrder[2] = sortedIncrements[2];
                                listInOrder[3] = sortedIncrements[3];
                            }
                            else {
                                listInOrder[2] = sortedIncrements[3];
                                listInOrder[3] = sortedIncrements[2];
                            }
                        }
                        else{
                            listInOrder[2] = sortedIncrements[2];
                            listInOrder[3] = sortedIncrements[3];
                        }
                    }
                }
                else{
                    listInOrder[0] = sortedIncrements[0];
                    if(sortedIncrements[1] == sortedIncrements[2]){
                        if(sortedIncrements[2] == sortedIncrements[3]){
                            //next three the same, fill randomly
                            listInOrder[1] = sortedIncrements[rand.nextInt(3)+1];
                            while(listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sortedIncrements[rand.nextInt(3)+1];
                            }
                            while(listInOrder[3] == listInOrder[1] || listInOrder[3] == listInOrder[2]){
                                listInOrder[3] = sortedIncrements[rand.nextInt(3)+1];
                            }
                        }
                        else{
                            //next two the same, fill listInOrder with the 2 that are the same
                            listInOrder[1] = sortedIncrements[rand.nextInt(2)+1];
                            while(listInOrder[2] == listInOrder[1]){
                                listInOrder[2] = sortedIncrements[rand.nextInt(2)+1];
                            }
                            listInOrder[3] = sortedIncrements[3];
                        }
                    }
                    else{
                        listInOrder[1] = sortedIncrements[1];
                        if(sortedIncrements[2] == sortedIncrements[3]){
                            if(rand.nextInt(2) > 0){
                                listInOrder[2] = sortedIncrements[2];
                                listInOrder[3] = sortedIncrements[3];
                            }
                            else {
                                listInOrder[2] = sortedIncrements[3];
                                listInOrder[3] = sortedIncrements[2];
                            }
                        }
                        else{
                            listInOrder[2] = sortedIncrements[2];
                            listInOrder[3] = sortedIncrements[3];
                        }
                    }
                }
            
                if(snake.nextMoveLosesGame(getDirection(listInOrder, 0))){
                    if(snake.nextMoveLosesGame(getDirection(listInOrder, 1))){
                        if(snake.nextMoveLosesGame(getDirection(listInOrder, 2))){
                            if(snake.nextMoveLosesGame(getDirection(listInOrder, 3))){
                                directionChar = getDirection(sortedIncrements, 0);
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

                // System.out.println("Direction: " + directionChar);
                // System.out.println("Increment: " + increments[0] + " " + increments[1] + " " + increments[2] + " " + increments[3]);
                flag = snake.move(directionChar);
            }
            // snake.printBoard();
            // System.out.println("Direction: " + directionChar);
            // System.out.println("Increment: " + increments[0] + " " + increments[1] + " " + increments[2] + " " + increments[3]);
                
          
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
}