import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Solver {

    public static void main(String[] args) {

        Random rand = new Random(100);
        //start the actual output:

        double startTime;
        double endTime;
        double totalTime;
        double averageTime = 0;

        String timeFileName = "time.txt";

        //check if the file exists, if it doesnt create it, if it does clear it
        try{
            File file = new File(timeFileName);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
 
        //First the hand trained agents:
        System.out.println("Hand trained agents:");
        System.out.println();
        System.out.println("Randomizer:");
        String filename = "randomizer.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }

        for(int i = 3; i<41; i++){
            averageTime = 0;
            double[][] stats = new double[50][3];
            for(int j = 0; j<50; j++){
                Random randToUse = new Random(rand.nextInt());
                Randomizer randomizer = new Randomizer(i, randToUse);
                startTime = System.currentTimeMillis();
                double[] ret = randomizer.run(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
                stats[j] = ret;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Randomizer: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Board size: " + i + "Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
            try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }
        System.out.println();
        System.out.println("Towards less dense:");
        filename = "towardslessdense.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i = 3; i<41; i++){
            averageTime = 0;
            double[][] stats = new double[50][3];
            for(int j = 0; j<50; j++){
                Random randToUse = new Random(rand.nextInt());
                TowardsLessDense towardsLessDense = new TowardsLessDense(i, randToUse);
                startTime = System.currentTimeMillis();
                double[] ret = towardsLessDense.run(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
                stats[j] = ret;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Towards less dense: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Board size: " + i + "Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
            try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }
        System.out.println();
        System.out.println("Wall hugger:");
        filename = "wallhugger.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i = 3; i<41; i++){
            double[][] stats = new double[50][3];
            averageTime = 0;
            for(int j = 0; j<50; j++){
                Random randToUse = new Random(rand.nextInt());
                WallHugger wallHugger = new WallHugger(randToUse, i);
                startTime = System.currentTimeMillis();
                double[] ret = wallHugger.run(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
                stats[j] = ret;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Wall hugger: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Board size: " + i + "Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
            try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }

        //The PSO trained agent:
         System.out.println("PSO trained agent:");
        System.out.println();
        filename = "PSOTrained.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i = 3; i<41; i++){
            Random randToUse = new Random(rand.nextInt());
            double [][] stats = new double[50][3];
            PSOTrainedNN pso = new PSOTrainedNN(100, i, randToUse);
            startTime = System.currentTimeMillis();
            NN best = pso.train(1000);
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            //write the time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("PSO training: boardsize: " + i + ": " + totalTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            averageTime = 0;
            for(int j = 0; j<50; j++){
                Random rand2 = new Random(rand.nextInt());
                startTime = System.currentTimeMillis();
                stats[j] = best.getFitness2(rand2);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("PSO testing: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Board size: " + i + " Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
             try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }
         
        //The GP trained agent:
         System.out.println("GP trained agent:");
        System.out.println();
        System.out.println("First GP:");
        filename = "GPFirst.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i = 3; i<41; i++){
            double[][] stats = new double[50][3];
            Random randToUse = new Random(rand.nextInt());
            GPRunnerInitial gprunner = new GPRunnerInitial(1000, 800, 10, 5, i, randToUse, 5, 20, 4);
            startTime = System.currentTimeMillis();
            GPInitial gp = gprunner.run();
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            //write the time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP training: boardsize: " + i + ": " + totalTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            averageTime = 0;
            for(int j = 0; j<50; j++){
                startTime = System.currentTimeMillis();
                stats[j] = gp.playAndGetStatistics(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP testing: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Board size: " + i + "Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
            try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }
        System.out.println();
        System.out.println("Second and Third GP:");
        filename = "GPSecond.txt";
        //clear the file if it exists
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        String filename2 = "GPThird.txt";
        //clear the file if it exists
        try{
            File file = new File(filename2);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i = 3; i<41; i++){
            averageTime = 0;
            double[][] stats = new double[50][3];
            double[][] stats2 = new double[50][3];
            Random randToUse = new Random(rand.nextInt());
            GPRunnerSecond gprunner = new GPRunnerSecond(2000, 800, 20, 8, i, randToUse, 1, 5, 4);
            startTime = System.currentTimeMillis();
            GPSecond gp = gprunner.run();
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            //write the time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP training: boardsize: " + i + ": " + totalTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            for(int j = 0; j<50; j++){
                startTime = System.currentTimeMillis();
                stats[j] = gp.playAndGetStatistics(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
            }
            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP testing: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            double[] average = new double[3];
            for(int j = 0; j<50; j++){
                average[0] += stats[j][0];
                average[1] += stats[j][1];
                average[2] += stats[j][2];
            }
            average[0] /= 50;
            average[1] /= 50;
            average[2] /= 50;
            System.out.println("Second GP");
            System.out.println("Board size: " + i + "Average score: " + average[0] + " Average moves: " + average[1] + " Games won: " + average[2]);
            //write the stats to a file:
            try{
                File file = new File(filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            startTime = System.currentTimeMillis();
            GPSecond gp2 = gprunner.runForThirdInstance();
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            //write the time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP training: boardsize: " + i + ": " + totalTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            averageTime = 0;
            for(int j = 0; j<50; j++){
                startTime = System.currentTimeMillis();
                stats2[j] = gp2.playAndGetStatistics(100);
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                //add the total time to average time
                averageTime += totalTime;
            }

            averageTime /= 50;
            //write the average time to a file:
            try{
                File file = new File(timeFileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("GP testing: boardsize: " + i + ": " + averageTime + "\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }

            double[] average2 = new double[3];
            for(int j = 0; j<50; j++){
                average2[0] += stats2[j][0];
                average2[1] += stats2[j][1];
                average2[2] += stats2[j][2];
            }
            average2[0] /= 50;
            average2[1] /= 50;
            average2[2] /= 50;
            System.out.println("Third GP");
            System.out.println("Board size: " + i + "Average score: " + average2[0] + " Average moves: " + average2[1] + " Games won: " + average2[2]);
            //write the stats to a file:
            try{
                File file = new File(filename2);
                if(!file.exists()){
                    file.createNewFile();
                }
                java.io.FileWriter fw = new java.io.FileWriter(file, true);
                fw.write("Board size: " + i);
                fw.write("\n");
                fw.write(stats2[0][0] + "");
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats2[j][0]);
                }
                fw.write("\n");
                fw.write("" + stats2[0][1]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats2[j][1]);
                }
                fw.write("\n");
                    fw.write("" + stats2[0][2]);
                for(int j = 1; j<50; j++){
                    fw.write(", " + stats2[j][2]);
                }
                fw.write("\n");
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(i == 9){
                i = 39;
            }
        }

    }

    // //play the snake game and display it from user input
    // System.out.println("Play the snake game and display it from user input:");
    // System.out.println();
    // System.out.println("Enter the board size:");
    // Scanner sc = new Scanner(System.in);
    // int boardSize = sc.nextInt();
    // Snake snake = new Snake(boardSize, rand);
    // char move;
    // char status = 'C';
    // while(status != 'W' && status != 'X'){
    //     snake.printBoard();
    //     move = sc.next().charAt(0);
    //     status = snake.move(move);
    // }
    // snake.printBoard();
    // System.out.println("Game over");
    // System.out.println("Score: " + snake.score);
    // System.out.println("Moves: " + snake.numMoves);
    // System.out.println("Status: " + status);
    // }

}
