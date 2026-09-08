public class Node{

    private final int value;
    private final Node left, right;
    private final int none = 0;
    //final för att upprätthålla beständighet (immutability)

    //konstruktor
    Node(int value, Node left, Node right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    Node newarray(){
        return new Node(none, null, null);
    }

    void set(Node a, int i, int value){

        Node copyReference = a;
        
        
    }


}
