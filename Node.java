public class Node{

    private int height = 1;
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

        int neededBits = 32 - Integer.numberOfLeadingZeros(i);
        System.out.println(neededBits);

        if(neededBits > (height-1)){
            height += neededBits - (height - 1);
        }

        


        if(i & (2^(height -1) == ))


        
        
    }


}
