public class Node{

   // private int height; 
    //potential bug. Larger problem: Height is being mutated, not persistent. 
    // Möjligen skapa en tree class som har nod och height fält
    // som man kan skapa nya versioner av? 
    final int value;
    final Node left, right;
    //private final int none = 0;
    //final för att upprätthålla beständighet (immutability)

    //konstruktor för löv
    Node(int value, Node left, Node right){
        this.value = value;
        this.left = left;
        this.right = right;
    }
    Node(Node old){
        this.value = old.value;
        this.left = old.left;
        this.right = old.right;
    }


}
