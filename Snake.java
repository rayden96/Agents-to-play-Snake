import java.util.Random;

public class Snake {

    int boardSize;
    char[][] board;
    int snakeLength;
    int snakeHeadX, snakeHeadY, foodX, foodY, score;
    int[] snakeX, snakeY;
    Random rand;
    int numMoves;

    public Snake(int boardSize, Random rand) {
        this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
        snakeLength = 1;
        snakeHeadX = rand.nextInt(boardSize);
        snakeHeadY = rand.nextInt(boardSize);
        snakeX = new int[boardSize * boardSize];
        snakeY = new int[boardSize * boardSize];
        snakeX[0] = snakeHeadX;
        snakeY[0] = snakeHeadY;
        this.rand = rand;
        
        //place food in a position that is not occupied by the snake
        do {
            foodX = rand.nextInt(boardSize);
            foodY = rand.nextInt(boardSize);
        } while (foodX == snakeHeadX && foodY == snakeHeadY);

        score = 0;

        //initialize board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ' ';
            }
        }
        board[snakeHeadX][snakeHeadY] = 'S';
        board[foodX][foodY] = 'F';
        numMoves = 0;
    }

    public char move(char direction) {

        //printBoard();
        //System.out.println("Moving " + direction);

        //check number of moves, if the snake has not moved in 1000 moves, end the game and return 'Z'
        if (numMoves == 1000) {
            return 'X';
        }


        switch (direction) {
            case 'U':
                snakeHeadX--;
                break;
            case 'D':
                snakeHeadX++;
                break;
            case 'L':
                snakeHeadY--;
                break;
            case 'R':
                snakeHeadY++;
                break;
        }

        //==============================Game Loss Conditions==============================

        //check if snake is out of bounds - game loss
        if (snakeHeadX < 0 || snakeHeadX >= boardSize || snakeHeadY < 0 || snakeHeadY >= boardSize) {
            return 'X';
        }

        //check if snake has eaten itself -- game loss
        for (int i = 0; i < snakeLength-1; i++) {
            if (snakeHeadX == snakeX[i] && snakeHeadY == snakeY[i]) {
                return 'X';
            }
        }

        //==============================Game Continue/Win Conditions==============================

        //check if snake has eaten food
        if (snakeHeadX == foodX && snakeHeadY == foodY) {
            snakeLength++;
            score++;

            //update snake body
            for (int i = snakeLength - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            snakeX[0] = snakeHeadX;
            snakeY[0] = snakeHeadY;

            //update board
            board[snakeHeadX][snakeHeadY] = 'S';

            //check if snake has filled the board
            if (snakeLength == boardSize * boardSize) {
                return 'W';
            }

            //place food in a position that is not occupied by the snakes body
            do {
                foodX = rand.nextInt(boardSize);
                foodY = rand.nextInt(boardSize);
            } while (board[foodX][foodY] == 'S');
            board[foodX][foodY] = 'F';

            
        }
        //check if snake has not eaten food
        else { 
         //update the snake and board to reflect the movement made
            board[snakeX[snakeLength - 1]][snakeY[snakeLength - 1]] = ' ';
            board[snakeHeadX][snakeHeadY] = 'S';
            for (int i = snakeLength - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            snakeX[0] = snakeHeadX;
            snakeY[0] = snakeHeadY;
        }

        numMoves++;
        
        return 'C';
    }

    public double getfeatureValue(int x, int y){
        //look at cell above and left to snake head, if it is food, set ret[0] to 1, if it is nothing set ret[0] to 0.5, it it is snake set ret[0] to -0.5 and if it is a wall set ret[0] to -1
        //for a cell given
        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize){
            return -1;
        }
        if(board[x][y] == 'F'){
            return 1;
        }
        else if(board[x][y] == ' '){
            return 0.5;
        }
        else if(board[x][y] == 'S'){
            return -0.5;
        }
        else{
            return -1;
        }
    }

    public double[] getFeatureInput(){
        //Return an array of size 10 that represents the feature set of the PSO trained NN
        
        double[] ret = new double[10];

        //look at cell above and left to snake head, if it is food, set ret[0] to 1, if it is nothing set ret[0] to 0.5, it it is snake set ret[0] to -0.5 and if it is a wall set ret[0] to -1
        //do this in a clockwise fashion for the rest of the cells around the snake head
        
        //up left
        ret[0] = getfeatureValue(snakeHeadX - 1, snakeHeadY - 1);
        //up
        ret[1] = getfeatureValue(snakeHeadX - 1, snakeHeadY);
        //up right
        ret[2] = getfeatureValue(snakeHeadX - 1, snakeHeadY + 1);
        //right
        ret[3] = getfeatureValue(snakeHeadX, snakeHeadY + 1);
        //down right
        ret[4] = getfeatureValue(snakeHeadX + 1, snakeHeadY + 1);
        //down
        ret[5] = getfeatureValue(snakeHeadX + 1, snakeHeadY);
        //down left
        ret[6] = getfeatureValue(snakeHeadX + 1, snakeHeadY - 1);
        //left
        ret[7] = getfeatureValue(snakeHeadX, snakeHeadY - 1);

        //feature 9 returns 1 if a food is in a cell to the left of the snake head, 0 if a food is in the same column as the snake head and -1 if a food is in a cell to the right of the snake head
        for( int i = 0; i < snakeHeadX; i++){
            for( int j = 0; j < boardSize; j++){
                if(board[i][j] == 'F'){
                    ret[8] = 1;
                }
            }
        }

        for( int i = snakeHeadX; i < boardSize; i++){
            for( int j = 0; j < boardSize; j++){
                if(board[i][j] == 'F'){
                    ret[8] = -1;
                }
            }
        }

        for( int j = 0; j < boardSize; j++){
            if(board[snakeHeadX][j] == 'F'){
                ret[8] = 0;
            }
        }

        //feature 10 returns 1 if a food is in a cell above the snake head, 0 if a food is in the same row as the snake head and -1 if a food is in a cell below the snake head
        for( int i = 0; i < snakeHeadY; i++){
            for( int j = 0; j < boardSize; j++){
                if(board[j][i] == 'F'){
                    ret[9] = 1;
                }
            }
        }

        for( int i = snakeHeadY; i < boardSize; i++){
            for( int j = 0; j < boardSize; j++){
                if(board[j][i] == 'F'){
                    ret[9] = -1;
                }
            }
        }

        for( int j = 0; j < boardSize; j++){
            if(board[j][snakeHeadY] == 'F'){
                ret[9] = 0;
            }
        }
        return ret;
    }

    //decide on feature input of GP. - GP will essentially use the same feature set to make functions and deductions.

    //method to give back results.
    public double[] getResults(){
        double[] ret = new double[2];
        ret[0] = score;
        ret[1] = numMoves;
        return ret;
    }

    //visualizer? - do potentially later
    //print out the board
    public void printBoard() {
        System.out.println("Score: " + score);
        System.out.println("Number of Moves: " + numMoves);
        System.out.println("Snake Length: " + snakeLength);
        System.out.println("Snake Head: (" + snakeHeadX + ", " + snakeHeadY + ")");
        System.out.println("Food: (" + foodX + ", " + foodY + ")");
        System.out.println("Board: ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }
    }

}