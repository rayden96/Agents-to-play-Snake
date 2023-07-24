import java.util.Random;

public class Randomizer {

    int boardSize;
    Random rand;

    double[] averageScoreAndMoves = new double[3];

    //random hand written agent
    Randomizer(int boardSize, Random rand){
        this.boardSize = boardSize;
        this.rand = rand;
    }

    double[] run(int numGames){
        averageScoreAndMoves = new double[3];
        averageScoreAndMoves[0] = 0;
        averageScoreAndMoves[1] = 0;
        averageScoreAndMoves[2] = 0;
        for (int i = 0; i < numGames; i++){
            Snake snake = new Snake(boardSize, rand);
            char flag = 'C';
            while (flag != 'X' && flag != 'W'){
                char direction = randomDirection();
                if(snake.nextMoveLosesGame(direction)){
                    char temp1 = direction;
                    while(direction == temp1) direction = randomDirection();
                    if(snake.nextMoveLosesGame(direction)){
                        char temp2 = direction;
                        while(direction == temp1 || direction == temp2) direction = randomDirection();
                        if(snake.nextMoveLosesGame(direction)){
                            char temp3 = direction;
                            while(direction == temp1 || direction == temp2 || direction == temp3) direction = randomDirection();
                        }
                    }
                }

                flag = snake.move(direction);
            }
            averageScoreAndMoves[0] += snake.score;
            averageScoreAndMoves[1] += snake.numMoves;
            if (flag == 'W'){
                averageScoreAndMoves[2]++;
            }
        }
        
        averageScoreAndMoves[0] /= numGames;
        averageScoreAndMoves[1] /= numGames;

        return averageScoreAndMoves;
    }

    char randomDirection(){
        int direction = rand.nextInt(4);
        switch (direction){
            case 0:
                return 'U';
            case 1:
                return 'D';
            case 2:
                return 'L';
            case 3:
                return 'R';
        }
        return 'U';
    }

    
    
}
