import java.util.Random;

public class NodeSecond {
    Boolean isTerminal;

    char value;

    NodeSecond leftChild;
    NodeSecond rightChild;

    Random rand;


    //function set: (arity 2)
    // - A: If food in column up
    // - B: If food in row right
    // - C: If food in column down
    // - D: If food in row left

    // - ifDanger1Up - if danger is in cell 2 - 'E'
    // - ifDanger1Right - if danger is in cell 4 - 'F'
    // - ifDanger1Down - if danger is in cell 6 - 'G'
    // - ifDanger1Left - if danger is in cell 8- 'H'

    // - I ifNextMoveEndsGame - if the first operator ends the game, use the second option
    // - J ifNextMoveLocksIn - if the next move puts the snake in a position whereby he is locked in, do the second 		operand
    char[] functionSet = {'A' , 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};

    //terminal set:
    // - moveUp - move up - 'U'
    // - moveRight - move right - 'R'
    // - moveDown - move down - 'D'
    // - moveLeft - move left - 'L'
    char[] terminalSet = {'U', 'R', 'D', 'L'};

    public NodeSecond(Boolean isTerminal, Random rand) {
        this.isTerminal = isTerminal;
        leftChild = null;
        rightChild = null;
        this.rand = rand;

        if(isTerminal) {
            value = terminalSet[rand.nextInt(4)];
        }
        else {
            value = functionSet[rand.nextInt(13)];
        }
    }

    public NodeSecond copyNode() {
        if(isTerminal) {
            NodeSecond newNode = new NodeSecond(isTerminal, rand);
            newNode.value = value;
            return newNode;
        }
        else {
            NodeSecond newNode = new NodeSecond(isTerminal, rand);
            newNode.value = value;
            newNode.leftChild = leftChild.copyNode();
            newNode.rightChild = rightChild.copyNode();
            return newNode;
        }
    }

    //mutate the node
    public void mutate(int chance, int depth){
        //randomly change the value if the chance is less than the chance percent + depth
        if(rand.nextInt(100) < chance ){
            if(isTerminal){
                value = terminalSet[rand.nextInt(4)];
            }
            else{
                //small chance to change the node to a terminal, else randomly change the value
                if(rand.nextInt(100) < 30){
                    isTerminal = true;
                    value = terminalSet[rand.nextInt(4)];
                    leftChild = null;
                    rightChild = null;
                }
                else{
                    value = functionSet[rand.nextInt(13)];
                }
            }
        }
        else{
            if(!isTerminal){
                if(rand.nextInt(100)<50){
                    leftChild.mutate(chance, depth + 1);
                }
                else{
                    rightChild.mutate(chance, depth + 1);
                }
            }
        }
        
    }

    public Boolean crossover(NodeSecond node, int chance){
        if (rand.nextInt(100) < chance){
            //replace this node with the other node
            value = node.value;
            isTerminal = node.isTerminal;
            leftChild = node.leftChild;
            rightChild = node.rightChild;
            return true;
        }
        else{
            if(!isTerminal && !node.isTerminal){
                if(this.leftChild.crossover(node.leftChild, chance)){
                    return true;
                }
                else if(this.rightChild.crossover(node.rightChild, chance)){
                    return true;
                }
            }
            return false;
        }
    }

    public String printTree(){
        if(isTerminal){
            if(value == 'U'){
                return "moveUp";
            }
            else if(value == 'R'){
                return "moveRight";
            }
            else if(value == 'D'){
                return "moveDown";
            }
            else{
                return "moveLeft";
            }
        }
        else{
            if(value == 'A'){
                return "ifFoodUp(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'B'){
                return "ifFoodRight(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'C'){
                return "ifFoodDown(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'D'){
                return "ifFoodLeft(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'E'){
                return "ifDanger1Up(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'F'){
                return "ifDanger1Right(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'G'){
                return "ifDanger1Down(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'H'){
                return "ifDanger1Left(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'I'){
                return "ifNextMoveEndsGame(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'J'){
                return "ifFoodAnyLeft(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'K'){
                return "ifFoodAnyRight(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'L'){
                return "ifFoodAnyUp(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'M'){
                return "ifFoodAnyDown(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else throw new IllegalArgumentException("Invalid node value");
        }
    }


}
