import java.util.Random;

public class GPInitial {
    
    //take in the snakeplayingtrees and perform GP

    //function set: (arity 2)
    // - ifFood1Up - if food is in cell 2
    // - ifFood1Right - if food is in cell 4
    // - ifFood1Down - if food is in cell 6
    // - ifFood1Left - if food is in cell 8

    // - ifDanger1Up - if danger is in cell 2
    // - ifDanger1Right - if danger is in cell 4
    // - ifDanger1Down - if danger is in cell 6
    // - ifDanger1Left - if danger is in cell 8

    NodeInitial root;
    int MaxDepth;

    Random rand;

    double fitness;

    int boardSize;

    public GPInitial(int MaxDepth, Random rand, int boardSize) {
        this.MaxDepth = MaxDepth;
        this.rand = rand;
        root = null;
        this.boardSize = boardSize;
        fitness = 0;

        root = initialGeneration(30, 0);
    }

    //use grow method to grow trees
    public NodeInitial initialGeneration(int chance, int depth){
        boolean isTerminal = false;
        //set isTerminal to true if depth is equal to max depth or if a random number is less than chance percent
        if((depth == MaxDepth || rand.nextInt(100) < chance) && (depth != 0 && depth != 1)) {
            isTerminal = true;
        }

        if(isTerminal){
            NodeInitial newNode = new NodeInitial(isTerminal, rand);
            return newNode;
        }
        else{
            NodeInitial newNode = new NodeInitial(isTerminal, rand);
            newNode.leftChild = initialGeneration(chance, depth + 1);
            newNode.rightChild = initialGeneration(chance, depth + 1);
            return newNode;
        }
    }

    public double playAndGetFitness(int numGames){
        fitness = 0;
        for(int i = 0; i < numGames; i++){
            Snake snake = new Snake(boardSize, rand);
            double[] featureVector = new double[10];
            char move;
            char status = 'C';
            while(status != 'W' && status != 'X'){
                featureVector = snake.getFeatureInput();
                move = decideMove(featureVector, root);
                status = snake.move(move);
            }
            fitness += (boardSize * boardSize) - snake.score;
        }
        fitness = fitness / numGames;
        return fitness;
    }

    public char decideMove(double[] featureVector, NodeInitial node){
        if(node.isTerminal == false){
            if(node.value == 'A'){
                if(featureVector[1] == 1){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'B'){
                if(featureVector[3] == 1){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'C'){
                if(featureVector[5] == 1){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'D'){
                if(featureVector[7] == 1){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'E'){
                if(featureVector[1] < 0){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'F'){
                if(featureVector[3] < 0){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'G'){
                if(featureVector[5] < 0){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else if(node.value == 'H'){
                if(featureVector[7] < 0){
                    return decideMove(featureVector, node.leftChild);
                }
                else{
                    return decideMove(featureVector, node.rightChild);
                }
            }
            else throw new IllegalArgumentException("Invalid node value");
        }
        else{
            if(node.value == 'U'){
                return 'U';
            }
            else if(node.value == 'R'){
                return 'R';
            }
            else if(node.value == 'D'){
                return 'D';
            }
            else if(node.value == 'L'){
                return 'L';
            }
            else throw new IllegalArgumentException("Invalid node value");
        }
    }

    //copy the tree
    public GPInitial copyTree(){
        GPInitial newTree = new GPInitial(MaxDepth, rand, boardSize);
        newTree.root = root.copyNode();
        return newTree;
    }

    //mutate the tree
    public void mutateTree(int chance){
        root.mutate(chance, 0);
    }

    //crossover the tree
    public GPInitial crossoverTree(GPInitial tree2, int chance){
        GPInitial newTree = new GPInitial(MaxDepth, rand, boardSize);
        newTree.root = root.copyNode();
        newTree.root.crossover(tree2.root, chance);
        return newTree;
    }

    public double[] playAndGetStatistics(int numGames){
        double[] statistics = new double[3];
        statistics[0] = 0;
        statistics[1] = 0;
        statistics[2] = 0;
        for(int i = 0; i < numGames; i++){
            Snake snake = new Snake(boardSize, rand);
            double[] featureVector = new double[10];
            char move;
            char status = 'C';
            while(status != 'W' && status != 'X'){
                featureVector = snake.getFeatureInput();
                move = decideMove(featureVector, root);
                status = snake.move(move);
            }
            statistics[0] += snake.score;
            statistics[1] += snake.numMoves;
            if(snake.score == boardSize * boardSize -1){
                statistics[2] += 1;
            }

        }
        statistics[0] = statistics[0] / numGames;
        statistics[1] = statistics[1] / numGames;
        return statistics;
    }

}
