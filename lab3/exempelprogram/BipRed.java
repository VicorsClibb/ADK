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
    
    void readBipartiteGraph() {
	// Läs antal hörn och kanter
	int x = io.getInt();
	int y = io.getInt();
	//int sizeV = x + y;
	int e = io.getInt();
	
	// Läs in kanterna
	int[] a = new int[e]; //innehåller vänstra de
	int[] b = new int[e];
	for (int i = 0; i < e; ++i) {
	    a[i] = io.getInt();
	    b[i] = io.getInt();
	}
	writeFlowGraph(x, y, e, a, b);
    }
    
    
    void writeFlowGraph(int x, int y, int sizeE, int[] a, int[] b) {
	int v = x + y + 2, e = sizeE, s = v - 1, t = v; 
	
	// Skriv ut antal hörn och kanter samt källa och sänka
	io.println(v);
	io.println(s + " " + t);
	io.println(e);
	int c = 1;

	//printar kanterna från inloppet s till varje nod i vänstra delmängden.
	for(int i = 0; i < x; i++){
		int nodX = i + 1;
		
		io.print(s + " " + nodX + " " + c);
	}

	for (int i = 0; i < e; ++i) {
		int n = a[i], m = b[i];
	    // Kant från n till m med kapacitet c
	    io.println(n + " " + m + " " + c);
	}

	//printar kanterna från varje nod i den högra delmängden till utloppet t.
	for(int i = 0; i < y; i++){

		int nodY = (x+1) + i;
		io.print(nodY + " " + t + " " + c);
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
	int v = io.getInt();
	int s = io.getInt();
	int t = io.getInt();
	int totflow = io.getInt();
	int e = io.getInt();
	
	for (int i = 0; i < e; ++i) {
	    // Flöde f från a till b
	    int a = io.getInt();
	    int b = io.getInt();
	    int f = io.getInt();
	}
    }
    
    
    void writeBipMatchSolution() {
	int x = 17, y = 4711, maxMatch = 0;
	
	// Skriv ut antal hörn och storleken på matchningen
	io.println(x + " " + y);
	io.println(maxMatch);
	
	for (int i = 0; i < maxMatch; ++i) {
	    int a = 5, b = 2323;
	    // Kant mellan a och b ingår i vår matchningslösning
	    io.println(a + " " + b);
	}
	
    }
    
    BipRed() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	//writeFlowGraph();
	
	//readMaxFlowSolution();
	
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

