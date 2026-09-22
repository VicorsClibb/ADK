public class Node{

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
