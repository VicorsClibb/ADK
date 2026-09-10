public class PersistentArray {

    private final int height; //maxSize = 2^(height-1), log(maxSize) = height-1
    private final Node rootNode;

    public PersistentArray(){
        this.height = 0; //löv har höjd 0
        this.rootNode = null;
    }


    //Exempel körning ger: new PersistenArray(2, finalRoot)
    //Alltså returneras PersistentArray(2, finalRoot) från set(a, 3, 10) där a är en tom array.
    //Och finalRoot utvecklas till =>
    //finalRoot = Node(10, currentRoot1, newRight) 
    //finalRoot = Node(10, Node(-1, null, null), Node(10, null, Node(10, null, null)))
    private PersistentArray(int height, Node rootNode){//hålla persistens, genom denna konstruktor skapa nya objekt baserade på tidigare attribut.
        this.height=height;
        this.rootNode=rootNode;
    }

    private static final PersistentArray empty = new PersistentArray();
    static PersistentArray newarray(){

        return empty; //Ty persistence kan vi returnera samma tomma array!
    }


    private int fetchChild(Node n) {

        return (n == null) ? -1 : n.value;
    }

    //ny array, height = 0, rootNode = null
    //kallar set(a, 3, 10)
    PersistentArray set(PersistentArray a, int i, int value){
        //3 > 0 => No problemo
        if(i < 0){
            throw new IndexOutOfBoundsException();
        }
        //needeBits = 32 - 30 = 2
        int neededBits = 32 - Integer.numberOfLeadingZeros(i);
        //currentRoot = null
        Node currentRoot = a.rootNode;
        //currentHeight = 0
        int currentHeight = a.height;

        //Behövs det här fortfarande?
        //int currentAmountBits = currentHeight; //Samma sak som 2^(currentHeight-1) (alltid potens 2) maxSize-1 ger då index.

        //newHeight = 0
        int newHeight = currentHeight;

        //Loop 1: 2 > 0 => gå in i loop
        //Loop 2: 2 > 1 => gå in i loop
        //Loop 3: 2 !> 2 => fortsätt förbi
        while (neededBits > newHeight) { //Fixar så att om index's bit.rep > curr.amount av bitar, justerar vi genom att öka trädet.
            //Loop 1: newRoot1 = new Node(-1, null, null)
            //Loop 2: newRoot2 = new Node(-1, currenRoot1, null)
            Node newRoot = new Node(fetchChild(currentRoot), currentRoot, null);
            //Loop 1: currentRoot1 = newRoot1
            //Loop 2: currentRoot2 = newRoot2
            currentRoot = newRoot;

            //Loop 1: newHeight = 1
            //Loop 2: newHeight = 2
            newHeight++;
        }

        //finalRoot = setRecursive(currentRoot2, 3, 10, 2) => Call 1 => Node(10, currentRoot1, newRight) returneras
        // => finalRoot = Node(10, currentRoot1, newRight) 
        Node finalRoot = setRecursive(currentRoot, i, value, newHeight);
        // ny persistentArray skapas den nya höjden och den utvecklade nya "noden (aka den som är ett träd i praktiken pga av alla rekursiva anrop)"
        return new PersistentArray(newHeight, finalRoot);
    }

   
    //Call 1: setRecursive(currentRoot2, 3, 10, 2) (från set(a, 3, 10))
    //Call 1: current = currentRoot2, i = 3, value = 10, localHeight = 2
    //Call 2: setRecursive(currentRight, 3, 10, 1)
    //Call 2: current2 = null, i2 = 3, value2 = 10, localHeight2 = 1
    //Call 3: setRecursive(null, 3, 10, 0)
    //Call 3: current3 = null, i3= 3, value3 = 10, localHeight3 = 0
    private Node setRecursive(Node current, int i, int value, int localHeight){

        //Call 1: localHeight = 2 => 2 !< 1 => forstätt.
        //Call 2: localHeight2 = 1 => 1 !< 1 => forstätt.
        //Call 3: localHeight3 = 0 => 0 < 1 = gå in i if-bracen
        if(localHeight < 1){ //basfall. Orkar inte tänka, antingen är löv-nivån 0 eller 1, utifrån min tanke nu får det vara 1 som löv och < 1 -> vet att vi är på ett löv.
            //Call 3: return new Node(10, null, null) to call 2!
            return new Node(value, null, null);
        }

        //Call 1: bitIndexToCheck = 2 - 1 = 1
        //Call 2: bitIndexToCheck2 = 1 - 1 = 0
        int bitIndexToCheck = localHeight -1;
        
        Node currentLeft;
        Node currentRight;

        //Call 1: currentRoot2 != null => gå in i if-bracen
        //Call 2: null = null => gå in i else-bracen
        if(current != null){
            //Call 1: currentLeft = currentRoot2.left = currentRoot1
            currentLeft = current.left;
            //Call 1: currentRight = null
            currentRight = current.right;

        }else{
            //Call 2: currentLeft2 = null
            currentLeft = null;
            //Call 2: currentRight2 = null
            currentRight = null;}
    
        //Call 1: 3 >> 1 = 1 => 1 & 1 = 1 => 1 =/= 0 => Gå in i else-bracen
        //Call 2: 3 >> 0 = 3 => 3 & 1 = 11 & 01 = 1 => 1 != 0 => Gå in i else-bracen
        if(((i >> bitIndexToCheck) & 1) == 0){

            Node newLeft = setRecursive(currentLeft, i, value, localHeight-1);

            int maxInSubTree = Math.max(fetchChild(newLeft), fetchChild(currentRight));

            return new Node(maxInSubTree, newLeft, currentRight);

            
        }else{
            //Call 1: newRight = setRecursive(null, 3, 10, 1) => Call 2 => Node(10, null, Node(10, null, null)) returneras => newRight = Node(10, null, Node(10, null, null))
            //Call 2: newRight2 = setRecursive(null, 3, 10, 0) => Call 3 => returnerar Node(10, null, null) => newRight2 = Node(10, null, null)
            Node newRight = setRecursive(currentRight, i, value, localHeight-1);
            //Call 2: maxinSubTreeCall2 = Math.max(10 (fetchChild(newRight2)), -1 (fetchChild(null))) => maxinSubTreeCall2 = 10
            //Call 1: maxInSubTreeCall = Math.max(10 (fetchChild(Node(10, null, Node(10, null, null)))), -1 (fetchChild(currentRoot1)))
            int maxInSubTree = Math.max(fetchChild(newRight), fetchChild(currentLeft));
            //Call 2: return new Node(10, null, Node(10, null, null)) till där Call 2 utfärdades! 
            //Call 1: return new Node(10, currentRoot1, newRight) => Call 1 returnerar denna nya node till där det utfärdades!
            return new Node(maxInSubTree, currentLeft, newRight);

        }
    }

    int get(PersistentArray current, int i){
        
        if(i < 0){
            throw new IndexOutOfBoundsException();
        }
        if(current.rootNode == null) return 0;

        return getHelpFunc(current.rootNode, i, current.height);

    }

    int getHelpFunc(Node current, int i, int treeHeight){

        if(treeHeight < 1){
            int leafValue = current.value;
            return leafValue;
        }

        int bitIndexToCheck = treeHeight -1;
        int bit = ((i >> bitIndexToCheck) & 1);

        if(bit == 0){

            return (current.left == null) ? 0 : getHelpFunc(current.left, i, treeHeight - 1);

        }

        return (current.right == null) ? 0 : getHelpFunc(current.right, i, treeHeight - 1);
        
    }




    private int maxininterval(PersistentArray current, int left, int right){

        if(left < 0 || right < 0 || left > right){
             throw new IllegalArgumentException("interval not valid");
        }

        return maxsegment(current.rootNode, left, right, current.height);

    }

    private int maxsegment(Node current, int left, int right, int height){ //height = level


        if(left > right){throw new IllegalArgumentException("nonsensical interval");}

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
            return Math.max(maxrightsegment(current.left, left, height-1), maxleftsegment(current.right, right,  height-1));
        }
    
        return -1;
    }

    private int maxrightsegment(Node leftChild, int left, int height){//största till höger om vänstra index

        int maxIndex = (1 << height) - 1;

        int bitLeft = (left >> (height-1)) & 1; //kikar på msb bit
        int bitMax = (maxIndex >> (height-1)) & 1;
    
        if(bitLeft== 0 && bitMax == 0)return leftChild.left.value;
        if(bitLeft== 1 && bitMax == 1)return leftChild.right.value;
        return maxsegment(leftChild, left, maxIndex, height);
    }
    private int maxleftsegment(Node leftChild, int right, int height){

        int minIndex = (1 << height) / 2;

        int bitLeft = (right >> (height-1)) & 1; //kikar på msb bit
        int bitMin = (minIndex >> (height-1)) & 1;
    
        if(bitLeft== 0 && bitMin == 0)return leftChild.left.value;
        if(bitLeft== 1 && bitMin == 1)return leftChild.right.value;
        return maxsegment(leftChild, right, minIndex, height);
    }
    



    public static void main(String[] args){

        PersistentArray test = newarray();

        // Node rootV0 = null;

        PersistentArray arr1 = test.set(test, 0, 67);
        System.out.println(arr1.rootNode.value + " // should give 67 , set sen hämta rotvärdet"); 
        System.out.println(arr1.get(arr1, 0) + "// ska vara 67");

        PersistentArray arr2 = arr1.set(arr1, 1, 42);
        System.out.println(arr2.get(arr2, 1) + " // should give 42, set sen get på nya indexet"); 

        PersistentArray arr2x = arr1.set(arr1, 1, 45);
        System.out.println(arr2x.get(arr2x, 1) + " // should give 45, uppdaterar värde på samma index korrekt"); 

        PersistentArray arr3 = arr2.set(arr2, 2, 13);

        PersistentArray arr4 = arr3.set(arr3, 3, 100); //probelm i think.
        System.out.println(arr4.rootNode.value + " // should give 100, max updaterar med högre värde insatt"); 

        PersistentArray arr5 = arr4.set(arr4, 4, 150);
        //System.out.println(arr5.rootNode.value + " // should give 150"); 

        PersistentArray arr6 = arr5.set(arr5, 5, 200);
        System.out.println(arr6.rootNode.value + " // should give 200, max updaterar med högre värde insatt");         
 
        PersistentArray arr7 = arr6.set(arr6, 6, 2); //PROBLEMET!!!!!!!
        System.out.println(arr7.rootNode.value + " // should give 200, max sänks inte av inmatning av lägre värde på annan index"); 

        PersistentArray arr8 = arr7.set(arr7, 5, 2);
        System.out.println(arr8.rootNode.value + " // should give 150, skriva över index med högsta (200) uppdaterar korrekt"); 

        System.out.println(arr4.rootNode.value + " // should give 100, arr4 har fortfarande samma max, dvs persistence håller"); 

        PersistentArray arr9 = arr8.set(arr8, 20, 250);
        System.out.println(arr9.rootNode.value + " // should give 250, träd måste växa i storlek uppdaterar max korrekt"); 

        PersistentArray arr10 = arr9.set(arr9, 0, 350);
        System.out.println(arr10.rootNode.value + " // should give 350, uppdaterar lägre index med nytt max värde"); 

        //PersistentArray arr11 = arr10.set(arr10.rootNode, -1, 350); //Index out of bound exception
        //System.out.println(arr11.rootNode.value + " // should give 350"); 

        PersistentArray arr11 = arr10.set(arr10, 21, 0);
        System.out.println(arr11.rootNode.value + " // should give 350, man kan sätta ett värde till 0");

        PersistentArray arr12 = arr11.set(arr11, 11, 500);
        System.out.println(arr12.rootNode.value + " // should give 500, max uppdaterar korrekt med uppdatering av ett index som var tomt innan men inte max index");


    }
}

