public class PersistentArray {

    private final int height; //maxSize = 2^(height-1), log(maxSize) = height-1
    private final Node rootNode;

    public PersistentArray(){
        this.height = 1; //löv har höjd 1
        this.rootNode = null;
    }

    private PersistentArray(int height, Node rootNode){//hålla persistens, genom denna konstruktor skapa nya objekt baserade på tidigare attribut.
        this.height=height;
        this.rootNode=rootNode;
    }

     PersistentArray newarray(){

        return new PersistentArray();
    }


    //Exemepel körning:
    //newarray()
    //vi har nu height = 1, rootNode = null
    //vi kallar set(a, 5, 10) : set värde av index till 10.     //Exemepel körning:
    //newarray()
    //vi har nu height = 1, rootNode = null
    //vi kallar set(a, 5, 10) : set värde av index till 10. 
    PersistentArray set(Node a, int i, int value){

        if(i < 0){
            throw new IndexOutOfBoundsException();
        }

        int neededBits = 32 - Integer.numberOfLeadingZeros(i);
        //5 = 101 (binärt) => neededBits = 3

        Node currentRoot = this.rootNode;
        // = null

        int currentHeight = this.height;
        // = 1

        int currentAmountBits = 1 << (currentHeight-1); //Samma sak som 2^(currentHeight-1) (alltid potens 2) maxSize-1 ger då index.
        // 1 << 0 = 1


        int newHeight = currentHeight;
        // = 1


        while (neededBits > currentAmountBits) { //Fixar så att om index's bit.rep > curr.amount av bitar, justerar vi genom att öka trädet.
        //3 > 1 => True

            Node newRoot = new Node(-1, currentRoot, null);
            currentRoot = newRoot;

            newHeight++;
            //newHeight 1 => 2
            int maxIndex = 1 << (newHeight-1);
            //height <=> this.height? => maxIndex = 1 << 1-1 = 1
            currentAmountBits = maxIndex-1;
            // = 0. Error right?
        }

        //this.height är fortfarande 1 för att det är immutable
        Node finalRoot = setRecursive(currentRoot, i, value, newHeight);

        return new PersistentArray(newHeight, finalRoot);



    }
    private Node setRecursive(Node current, int i, int value, int localHeight){

        //basfall
        if(localHeight < 1){ //orkar inte tänka, antingen är löv-nivån 0 eller 1, utifrån min tanke nu får det vara 1 som löv och < 1 -> vet att vi är på ett löv.
            return new Node(value, null, null);
        }

        int currentAmountBits = localHeight;

        Node currentLeft;
        Node currentRight;
        if(current != null){
             currentLeft = current.left;
             currentRight = current.right;

        }else{
            currentLeft = null;
            currentRight = null;}
    

        if(((i >> currentAmountBits) & 1) == 0){

            Node newLeft = setRecursive(currentLeft, i, value, localHeight-1);
            return new Node(value, newLeft, currentRight);

            
        }else{

            Node newRight = setRecursive(currentRight, i, value, localHeight-1);
            return new Node(value, currentLeft, newRight);

        }
    }

    int get(Node current, int i){
        
        if(i < 0){
            throw new IndexOutOfBoundsException();
        }

        




        return 0;
    }



    static void main(String[] args){
        //PersistentArray test = new PersistentArray();


        
    }
}

