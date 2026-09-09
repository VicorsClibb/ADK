public class persistentArray {

    private final int height; //maxSize = 2^(height-1), log(maxSize) = height-1
    private final int currentAmountBits;
    private final Node rootNode;

    public persistentArray(){
        this.height = 1; //löv har höjd 1
        this.currentAmountBits = 1;
        this.rootNode = null;
    }

    private persistentArray(int height, int currentAmountBits, Node rooNode){//hålla persistens, genom denna konstruktor skapa nya objekt baserade på tidigare attribut.
        this.height=height;
        this.currentAmountBits=currentAmountBits;
        this.rootNode=rooNode;
    }

     persistentArray newarray(){

        return new persistentArray();
    }


    void set(Node a, int i, int value){

        int neededBits = 32 - Integer.numberOfLeadingZeros(i);
        Node currentRoot = this.rootNode;
        int currentHeight = this.height;

        int maxSize = 1 << (height-1); //(alltid potens 2) maxSize-1 ger då index.
        this.currentAmountBits = maxSize -1;

        while (neededBits > currentAmountBits) { //Fixar så att om index bit.rep > curr.amount justerar genom att öka trädet.
            Node newRoot = new Node(-1);
            newRoot.left = currentRoot;
            currentRoot = newRoot;

            currentHeight++;
            

           

        }



        
        System.out.println(neededBits);

        if(neededBits > (height-1)){
            height += neededBits - (height - 1);
        }

        int amountOfBits = height - 1; //antalet bitar för att representera samtliga index-platser i listan
        //int msb = 2^(amountOfBits); // _ _ _ -> 1 0 0 = 4 (exempel med 3 bitar) -> 2^(3-1)=4
        int msb = 1 << (height - 1); // msb som kan skickas med rekursivt så den fortsätter shifta
        //1 << (3 - 1) -> 1 << 2 -> 1 0 0


        if((i & msb) == 0){

            set(copyReference.left, i, value);

        }




        if(i & (2^(height -1) == ))

        
    }
}
