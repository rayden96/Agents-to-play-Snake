import java.util.Random;

public class Snake {

    int boardSize;
    char[][] board;
    int snakeLength;
    int snakeHeadX, snakeHeadY, foodX, foodY, score;
    int[] snakeX, snakeY;
    Random rand;

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
    }

    public char move(char direction) {

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
        for (int i = 0; i < snakeLength; i++) {
            if (snakeHeadX == snakeX[i] && snakeHeadY == snakeY[i]) {
                return 'X';
            }
        }

        //==============================Game Continue/Win Conditions==============================

        //check if snake has eaten food
        if (snakeHeadX == foodX && snakeHeadY == foodY) {
            snakeLength++;
            score++;

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

            //update snake body
            for (int i = snakeLength - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            snakeX[0] = snakeHeadX;
            snakeY[0] = snakeHeadY;

            //update board
            board[snakeHeadX][snakeHeadY] = 'S';
        }
        //check if snake has not eaten food
        else {
            //update snake body
            for (int i = snakeLength - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            snakeX[0] = snakeHeadX;
            snakeY[0] = snakeHeadY;

            //update board
            board[snakeHeadX][snakeHeadY] = 'S';
            board[snakeX[snakeLength - 1]][snakeY[snakeLength - 1]] = ' ';
        }

        return 'C';
    }

}