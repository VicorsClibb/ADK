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
        if(current.rootNode == null) return 0;

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

            return (current.left == null) ? 0 : getHelpFunc(current.left, i, treeHeight - 1);

        }

        return (current.right == null) ? 0 : getHelpFunc(current.right, i, treeHeight - 1);
        
    }

    



    public static void main(String[] args){
        PersistentArray test = new PersistentArray();

        Node rootV0 = null;

        PersistentArray arr1 = test.set(rootV0, 0, 67);
        System.out.println(arr1.rootNode.value + " // should give 67 , set sen hämta rotvärdet"); 


        PersistentArray arr2 = arr1.set(arr1.rootNode, 1, 42);
        System.out.println(arr2.get(arr2, 1) + " // should give 42, set sen get på nya indexet"); 

        PersistentArray arr2x = arr1.set(arr1.rootNode, 1, 45);
        System.out.println(arr2x.get(arr2x, 1) + " // should give 45, uppdaterar värde på samma index korrekt"); 


        PersistentArray arr3 = arr2.set(arr2.rootNode, 2, 13);

        PersistentArray arr4 = arr3.set(arr2.rootNode, 3, 100);
        System.out.println(arr4.rootNode.value + " // should give 100, max updaterar med högre värde insatt"); 

        PersistentArray arr5 = arr4.set(arr3.rootNode, 4, 150);
        //System.out.println(arr5.rootNode.value + " // should give 150"); 

        PersistentArray arr6 = arr5.set(arr4.rootNode, 5, 200);
        System.out.println(arr6.rootNode.value + " // should give 200, max updaterar med högre värde insatt"); 
        

        PersistentArray arr7 = arr6.set(arr5.rootNode, 6, 2);
        System.out.println(arr7.rootNode.value + " // should give 200, max sänks inte av inmatning av lägre värde på annan index"); 

        PersistentArray arr8 = arr7.set(arr6.rootNode, 5, 2);
        System.out.println(arr8.rootNode.value + " // should give 150, skriva över index med högsta (200) uppdaterar korrekt"); 

        

        System.out.println(arr4.rootNode.value + " // should give 100, arr4 har fortfarande samma max, dvs persistence håller"); 

        PersistentArray arr9 = arr8.set(arr7.rootNode, 20, 250);
        System.out.println(arr9.rootNode.value + " // should give 250, träd måste växa i storlek uppdaterar max korrekt"); 

        PersistentArray arr10 = arr9.set(arr8.rootNode, 0, 350);
        System.out.println(arr10.rootNode.value + " // should give 350, uppdaterar lägre index med nytt max värde"); 

        //PersistentArray arr11 = arr10.set(arr9.rootNode, -1, 350); //Index out of bound exception
        //System.out.println(arr11.rootNode.value + " // should give 350"); 



        PersistentArray arr11 = arr10.set(arr9.rootNode, 21, 0);
        System.out.println(arr11.rootNode.value + " // should give 350, man kan sätta ett värde till 0");

        PersistentArray arr12 = arr11.set(arr10.rootNode, 11, 500);
        System.out.println(arr12.rootNode.value + " // should give 500, max uppdaterar korrekt med uppdatering av ett index som var tomt innan men inte max index");

    }
}

