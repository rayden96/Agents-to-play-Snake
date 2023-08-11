import java.util.Random;

public class NodeInitial {
    Boolean isTerminal;

    char value;

    NodeInitial leftChild;
    NodeInitial rightChild;

    Random rand;


    //function set: (arity 2)
    // - ifFood1Up - if food is in cell 2 - 'A'
    // - ifFood1Right - if food is in cell 4 - 'B'
    // - ifFood1Down - if food is in cell 6 - 'C'
    // - ifFood1Left - if food is in cell 8 - 'D'

    // - ifDanger1Up - if danger is in cell 2 - 'E'
    // - ifDanger1Right - if danger is in cell 4 - 'F'
    // - ifDanger1Down - if danger is in cell 6 - 'G'
    // - ifDanger1Left - if danger is in cell 8- 'H'
    char[] functionSet = {'A' , 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    //terminal set:
    // - moveUp - move up - 'U'
    // - moveRight - move right - 'R'
    // - moveDown - move down - 'D'
    // - moveLeft - move left - 'L'
    char[] terminalSet = {'U', 'R', 'D', 'L'};

    public NodeInitial(Boolean isTerminal, Random rand) {
        this.isTerminal = isTerminal;
        leftChild = null;
        rightChild = null;
        this.rand = rand;

        if(isTerminal) {
            value = terminalSet[rand.nextInt(4)];
        }
        else {
            value = functionSet[rand.nextInt(8)];
        }
    }

    public NodeInitial copyNode() {
        if(isTerminal) {
            NodeInitial newNode = new NodeInitial(isTerminal, rand);
            newNode.value = value;
            return newNode;
        }
        else {
            NodeInitial newNode = new NodeInitial(isTerminal, rand);
            newNode.value = value;
            newNode.leftChild = leftChild.copyNode();
            newNode.rightChild = rightChild.copyNode();
            return newNode;
        }
    }

    //mutate the node
    public void mutate(int chance, int depth){
        //randomly change the value if the chance is less than the chance percent + depth
        if(rand.nextInt(100) < chance + (depth * 5)){
            if(isTerminal){
                value = terminalSet[rand.nextInt(4)];
            }
            else{
                value = functionSet[rand.nextInt(8)];
            }
        }
        else{
            if(!isTerminal){
                leftChild.mutate(chance, depth + 1);
                rightChild.mutate(chance, depth + 1);
            }
        }
        
    }

    public Boolean crossover(NodeInitial node, int chance){
        if (rand.nextInt(100) < chance){
            if(this.isTerminal == node.isTerminal){
                this.value = node.value;
                if(!isTerminal){
                    this.leftChild = node.leftChild.copyNode();
                    this.rightChild = node.rightChild.copyNode();
                }
            }
            return true;
        }
        else{
            double randNum = rand.nextDouble();
            if(this.isTerminal != node.isTerminal){
                return false;
            }
            if(randNum < 0.5){
                if(!isTerminal){
                    if(!leftChild.crossover(node.leftChild, chance));
                        return rightChild.crossover(node.rightChild, chance);
                }
                else{
                    return false;
                }
            }
            else{
                if(!isTerminal){
                    if(!rightChild.crossover(node.rightChild, chance));
                        return leftChild.crossover(node.leftChild, chance);
                }
                else{
                    return false;
                }
            }
        }
    }


}
