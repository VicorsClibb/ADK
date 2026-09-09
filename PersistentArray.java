import java.security.InvalidAlgorithmParameterException;

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

     PersistentArray newArray(){

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

    private int fetchChild(Node n) {

        return (n == null) ? -1 : n.value;
    }

    private Node setRecursive(Node current, int i, int value, int localHeight){

        //basfall
        if(localHeight < 1){ //orkar inte tänka, antingen är löv-nivån 0 eller 1, utifrån min tanke nu får det vara 1 som löv och < 1 -> vet att vi är på ett löv.
            return new Node(value, null, null);
        }

        int currentAmountBits = localHeight - 1;
        
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

            int maxInSubTree = Math.max(fetchChild(newLeft), fetchChild(currentRight));

            return new Node(maxInSubTree, newLeft, currentRight);

            
        }else{

            Node newRight = setRecursive(currentRight, i, value, localHeight-1);

            int maxInSubTree = Math.max(fetchChild(newRight), fetchChild(currentLeft));

            return new Node(maxInSubTree, currentLeft, newRight);

        }
    }

    int get(PersistentArray current, int i){
        
        if(i < 0){
            throw new IndexOutOfBoundsException();
        }
        if(current.rootNode == null)return 0;

        return getHelpFunc(current.rootNode, i, current.height);

    }

    int getHelpFunc(Node current, int i, int treeHeight){

        if(treeHeight < 1){
            int leafValue = current.value;
            return leafValue;
        }

        int currentAmountBits = treeHeight - 1;
        int bit = ((i >> currentAmountBits) & 1);

        if(bit == 0){
            return getHelpFunc(current.left, i, treeHeight - 1);
        }

        return getHelpFunc(current.right, i, treeHeight - 1);
        
    }




    private int maxininterval(PersistentArray current, int left, int right){

        if(left < 0 || right < 0 || left > right){
             throw new IllegalArgumentException("interval not valid");
        }

        return maxsegment(current.rootNode, left, right, current.height);

    }

    private int maxsegment(Node current, int left, int right, int height){ //height = level

        //Case A 
        if(current == null){return -1;}

        //Case B 
        if(height < 1){return current.value;}

        //Case C 
        int currentAmountBits = height -1;
        int leftBit = ((left >> currentAmountBits) & 1);
        int rightBit = ((right >> currentAmountBits) & 1);

        if(leftBit == 0 && rightBit == 0){
            Node leftChild = current.left;
            return maxsegment(leftChild, left, right, height-1);
        }

        //Case D 
        if(leftBit == 1 && rightBit == 1){
            Node rightChild = current.right;
            return maxsegment(rightChild, left, right, height-1);
        }

        //Case E 
        if(leftBit == 0 && rightBit == 1){
            Math.max(maxrightsegment(current.left), maxleftsegment(current.right));
        }
    }

    private int maxrightsegment(Node leftChild){

        
    }
    private int maxleftsegment(Node leftChild){


    }
    



    public static void main(String[] args){
        PersistentArray test = new PersistentArray();

        Node rootV0 = null;
        PersistentArray arr1 = test.set(rootV0, 2, 67);
        PersistentArray arr2 = arr1.set(arr1.rootNode, 0, 42);
        System.out.println(arr2.get(arr2, 0)); // should give 42
        PersistentArray arr3 = arr2.set(arr2.rootNode, 0, 13);


        System.out.println(arr1.get(arr1, 2)); // 67
        System.out.println(arr2.get(arr2, 0)); // 42
        System.out.println(arr3.get(arr3, 0)); // 13



        
    }
}

