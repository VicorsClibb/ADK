/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;

	int[] cache4xy = new int[2];
    
    void readBipartiteGraph() {
	// Läs antal hörn och kanter
	int x = io.getInt();
	int y = io.getInt();
	cache4xy[0] = x;
	cache4xy[1] = y;
	//int sizeV = x + y;
	int e = io.getInt();
	
	// Läs in kanterna
	int[] a = new int[e];
	int[] b = new int[e];
	for (int i = 0; i < e; ++i) {
	    a[i] = io.getInt();
	    b[i] = io.getInt();
	}
	writeFlowGraph(e, a, b);
    }
    
    
    void writeFlowGraph(int sizeE, int[] a, int[] b) {
	int x = cache4xy[0], y = cache4xy[1];
	int v = x + y + 2, e = sizeE, s = v - 1, t = v; 
	
	// Skriv ut antal hörn och kanter samt källa och sänka
	io.println(v);
	io.println(s + " " + t);
	io.println(e + x + y);
	int c = 1;

	//printar kanterna från inloppet s till varje nod i vänstra delmängden.
	for(int i = 0; i < x; i++){
		int nodX = i + 1;
		
		io.println(s + " " + nodX + " " + c);
	}

	for (int i = 0; i < e; ++i) {
		int n = a[i], m = b[i];
	    // Kant från n till m med kapacitet c
	    io.println(n + " " + m + " " + c);
	}

	//printar kanterna från varje nod i den högra delmängden till utloppet t.
	for(int i = 0; i < y; i++){

		int nodY = (x+1) + i;
		io.println(nodY + " " + t + " " + c);
	}

	// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
	io.flush();
	
	// Debugutskrift
	System.err.println("Skickade iväg flödesgrafen");

    }
    
    
    void readMaxFlowSolution() {
	// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
	// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
	// skickade iväg)
	//int v = io.getInt();
	io.getInt();
	int s = io.getInt();
	int t = io.getInt();
	int totflow = io.getInt();
	int e = io.getInt();

	int[] a = new int[totflow];
	int[] b = new int[totflow];
	int index = 0;
	
	for (int i = 0; i < e; ++i) {
	    // Flöde f från a till b
		int aTemp = io.getInt();
		int bTemp = io.getInt();
		io.getInt();

		if(aTemp != s && bTemp != t){
		a[index] = aTemp;
	    b[index] = bTemp;
		index++;
		}                                                                  
	}

	//assertion
	assert (index + 1) == totflow;


	writeBipMatchSolution(totflow, a, b);
    }
    
    
    void writeBipMatchSolution(int sizeOfM, int[] l, int[] r) {

	int x = cache4xy[0], y = cache4xy[1], maxMatch = sizeOfM;
	// Skriv ut antal hörn och storleken på matchningen
	io.println(x + " " + y);
	io.println(maxMatch);
	
	for (int i = 0; i < maxMatch; ++i) {
	    int a = l[i], b = r[i];
	    // Kant mellan a och b ingår i vår matchningslösning
	    io.println(a + " " + b);
	}
	
    }
    
    BipRed() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	//writeFlowGraph();
	
	readMaxFlowSolution();
	
	//writeBipMatchSolution();

	// debugutskrift
	System.err.println("Bipred avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new BipRed();
    }
}

