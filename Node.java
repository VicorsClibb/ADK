public class Node{

    private int height = 1; //potential bug
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


        //Basfall

        //

        Node copyReference = a;

        int neededBits = 32 - Integer.numberOfLeadingZeros(i);
        System.out.println(neededBits);

        if(neededBits > (height-1)){
            height += neededBits - (height - 1);
        }

        int amountOfBits = height - 1; //antalet bitar för att representera samtliga index-platser i listan
        int msb = 2^(amountOfBits); // _ _ _ -> 1 0 0 = 4 (exempel med 3 bitar) -> 2^(3-1)=4

        if((i & msb) == 0){

            set(copyReference.left, i, value);

        }




        if(i & (2^(height -1) == ))


        
        
    }


}
