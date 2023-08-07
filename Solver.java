import java.util.Random;
import java.util.Scanner;

public class Solver {

    public static void main(String[] args) {

        Random rand = new Random(119);

        // PSOTrainedNN pso = new PSOTrainedNN(100, 3, rand);
        // NN best = pso.train(1000);

        // best.getFitness2();

    //     Snake snake = new Snake(3, rand);
    //     snake.printBoard();

    //    // recieve input from the user and keep moving the snake until the game is over\
    //     char flag = 'C';
    //     while (flag == 'C') {
    //         Scanner sc = new Scanner(System.in);
    //         char direction = sc.next().charAt(0);
    //         flag = snake.move(direction);
    //         snake.printBoard();
    //     }

    // Randomizer randomizer = new Randomizer(3, rand);
    // double[] ret = randomizer.run(100);

    // System.out.println("Average score: " + ret[0] + " Average moves: " + ret[1] + " Games won: " + ret[2]);

    TowardsLessDense towardsLessDense = new TowardsLessDense(3, rand);
    double[] ret = towardsLessDense.run(100);
    System.out.println("Average score: " + ret[0] + " Average moves: " + ret[1] + " Games won: " + ret[2]);

    WallHugger wallHugger = new WallHugger(rand, 3);
    double[] ret2 = wallHugger.run(100);
    System.out.println("Average score: " + ret2[0] + " Average moves: " + ret2[1] + " Games won: " + ret2[2]);



    }
}
