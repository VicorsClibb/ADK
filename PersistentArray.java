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
        //3 > 0 => Fortsätt förbi
        if(i < 0){
            throw new IllegalArgumentException("index not valid");
        }

        //needeBits = 32 - 30 = 2
        int neededBits = 32 - Integer.numberOfLeadingZeros(i);

        //currentRoot = null
        Node currentRoot = a.rootNode;

        //currentHeight = 0
        int currentHeight = a.height;

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
            throw new IllegalArgumentException("interval not valid");
        } else if (current.rootNode == null){
            return 0;
        } else if(i > ((1 << current.height) - 1)){

            return 0;
        }else{
            return getHelpFunc(current.rootNode, i, current.height);
        }
    }

    //
    int getHelpFunc(Node current, int i, int treeHeight){

        if(treeHeight == 0){
            int leafValue = current.value;
            return leafValue;
        }

        int bitIndexToCheck = treeHeight - 1;
        int bit = ((i >> bitIndexToCheck) & 1);

        if(bit == 0){
            return (current.left == null) ? 0 : getHelpFunc(current.left, i, treeHeight - 1);
        } else{
            return (current.right == null) ? 0 : getHelpFunc(current.right, i, treeHeight - 1);
        } 
    }

    //Testa för right utanför maxindex
    int maxininterval(PersistentArray current, int left, int right){

        if(left < 0 || right < 0 || left > right){ //Jämförelse av index som tal, men används inte för navigering så borde vara okej.
             throw new IllegalArgumentException("interval not valid");

        }else if(left > ((1 << current.height) - 1)){//Om nedre gränsen är större än max index => inget värde kan vara tilldelat där.
            return 0;

        }else if(right > ((1 << current.height) - 1)){//Om övre gränsen är större än max index, kolla all värden från nedregränsen till max index.

            right = ((1 << current.height) - 1);
        }

        int returnValue = maxsegment(current.rootNode, left, right, current.height);

        if(returnValue == -1){
            return 0;

        }else{

            return returnValue;
        }
        
    }

    private int maxsegment(Node current, int left, int right, int height){ //height = level


        if(left > right){throw new IllegalArgumentException("nonsensical interval");}

        int currentAmountBits = height -1;
        int leftBit = ((left >> currentAmountBits) & 1);
        int rightBit = ((right >> currentAmountBits) & 1);

        //Case A 
        if(current == null){return -1;

        }else if(height == 0){//Case B

            {return current.value;}

        }else if(leftBit == 0 && rightBit == 0){//Case C

            Node leftChild = current.left;
            return maxsegment(leftChild, left, right, height-1);

        }else if(leftBit == 1 && rightBit == 1){//Case D 

            Node rightChild = current.right;
            return maxsegment(rightChild, left, right, height-1);

        }else if(leftBit == 0 && rightBit == 1){//Case E 

            return Math.max(maxrightsegment(current.left, left, height-1), maxleftsegment(current.right, right,  height-1));
        }else{

            return -2;
        }
    }

    private int maxrightsegment(Node leftChild, int left, int height){//största till höger om vänstra index

        if(leftChild == null){
            return -1;
        }

        if(height == 0){
            return leftChild.value;
        }
        
        int bitLeft = (left >> (height-1)) & 1; //kikar på msb bit

        if(bitLeft == 0){
            return Math.max(maxrightsegment(leftChild.left, left, height -1), fetchChild(leftChild.right));

        }else{

            return maxrightsegment(leftChild.right, left, height - 1);
        }

   
    }
    private int maxleftsegment(Node rightChild, int right, int height){

        if(rightChild == null){
            return -1;
        }

        if(height == 0){
            return rightChild.value;
        }    
        
        int bitRight = (right >> (height-1)) & 1; //kikar på msb bit

        if(bitRight == 1){

            return Math.max(maxleftsegment(rightChild.right, right, height -1), fetchChild(rightChild.left));
            
        }else{
            return maxleftsegment(rightChild.left, right, height-1);
        }

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

        //Tester för maxininterval
        
        PersistentArray test2 = newarray();

        //Case A
        int a = test2.maxininterval(test2, 0, 0);
        System.out.println(a + " // Should give 0");

        //Case B
        Node nod = new Node(67, null, null);
        PersistentArray b = new PersistentArray(0, nod);
        System.out.println(b.maxininterval(b, 0, 0)+ " // Should give 67");

        //Case C
        PersistentArray b1 = b.set(b, 0, 10);//2 nivåer
        PersistentArray c = newarray();
        PersistentArray c1 = c.set(c, 1, 67);
        PersistentArray c2 = c1.set(c1, 3, 69);
        System.out.println(b1.maxininterval(b1, 0, 1)+ " // Should give 10");

        //Case D
        //System.out.println(b1.);
        System.out.println(b1.get(b1, 1));
        System.out.println(b1.maxininterval(b1, 1, 1)+ " // Should give 0");
        
        //Case E
        PersistentArray testE = newarray();
        PersistentArray testE1 = testE.set(testE, 7, 5);
        PersistentArray testE2 = testE1.set(testE1, 1, 3);
        PersistentArray testE3 = testE2.set(testE2, 2, 6);
        PersistentArray testE4 = testE3.set(testE3, 3, 20);

        System.out.println(testE4.maxininterval(testE4, 1, 7));
        //*/
        
        //get testing
        PersistentArray testGet = newarray();
        PersistentArray testGet1 = testGet.set(testGet, 2, 10);
        System.out.println(testGet1.get(testGet1, 2));
        System.out.println(testGet1.get(testGet1, 10)); //Index större än max index test
        System.out.println(testGet1.get(testGet1, 20)); //Index större än max index test
    }

}

