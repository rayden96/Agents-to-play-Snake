import java.util.Random;
import java.util.Scanner;

public class Solver {

    public static void main(String[] args) {

         Random rand = new Random(116);

        // PSOTrainedNN pso = new PSOTrainedNN(100, 3, rand);
        // NN best = pso.train(1000);

        // best.getFitness2();

        Snake snake = new Snake(3, rand);
        snake.printBoard();

       // recieve input from the user and keep moving the snake until the game is over\
        char flag = 'C';
        while (flag == 'C') {
            Scanner sc = new Scanner(System.in);
            char direction = sc.next().charAt(0);
            flag = snake.move(direction);
            snake.printBoard();
        }





    }
}
