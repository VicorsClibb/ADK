import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BipMatch {
    Kattio io;
    int v, s, t, e, antalKanter;
    int[] till,rkap;
    int[] prev, a, b;
    int[] cache4xy = new int[2];

    ArrayList<Integer> [] grannListor;


    void readBipartiteGraph() {
	// Läs antal hörn och kanter
	int x = io.getInt();
	int y = io.getInt();
    
    cache4xy[0] = x; cache4xy[1] = y;

	//int sizeV = x + y;
	e = io.getInt();
	
	// Läs in kanterna
    a = new int[e];
	b = new int[e];

	for (int i = 0; i < e; ++i) {
	    a[i] = io.getInt();
	    b[i] = io.getInt();
    }
    buildFlowGraph();
    }

    void addKant(int tidigare, int nästa, int kap){
            till[antalKanter*2] = nästa;
            till[antalKanter*2+1] = tidigare;

            rkap[antalKanter*2] = kap;
            rkap[antalKanter*2+1] = 0;

            grannListor[tidigare].add(antalKanter*2);
            grannListor[nästa].add(antalKanter*2+1);
            antalKanter++;
    }
    
    void buildFlowGraph() {

        int x = cache4xy[0], y = cache4xy[1];

        v = x + y + 2;
        s = v - 1;
        t = v;

        grannListor = new ArrayList[v + 1];

        for (int i = 0; i < v + 1; i++){
            grannListor[i] = new ArrayList<>(); 
        }

        antalKanter = 0;

        till = new int[2*(e + x + y)];
        rkap = new int[2*(e + x + y)];
        
        for (int i = 0; i < e; ++i) {
            int n = a[i], m = b[i];
            addKant(n, m, 1);
        }

        for(int i = 0; i < x; i++){
            int nodX = i + 1;
            addKant(s, nodX, 1);
        }

        for(int i = 0; i < y; i++){
            int nodY = (x+1) + i;
            addKant(nodY, t, 1);
        }
    }

    boolean bfs(){

        prev = new int[v + 1];

        for(int i = 0; i < prev.length; i++){
            prev[i] = -1;
        }        
        Queue<Integer> queue = new LinkedList<>();

        queue.add(s);

        prev[s] = -2;

        while(!queue.isEmpty()){

            int u = queue.poll();

            for(int k: grannListor[u]){

                int nod = till[k];

                if(prev[nod] == -1 && rkap[k] > 0){
                    queue.add(nod);

                    prev[nod] = k;

                    if(nod == t){
                        return true;
                    }

                }

                
            }
        }
        return false;
    }

    int updtFlowGraph(){
        int k = prev[t];
        int n = k ^ 1;

        ArrayList<Integer> p = new ArrayList<>();
        p.add(k);

        while(till[n] != s){

            int nod = (till[n]);

            int prevKant = prev[nod];

            p.add(prevKant);

            n = prevKant ^ 1;
        }

        int minRkap = Integer.MAX_VALUE;

        for(int kant: p){
            minRkap = Math.min(rkap[kant], minRkap);
        }

        for(int kant: p){
            rkap[kant] -= minRkap;
            rkap[kant ^ 1] += minRkap;
        }
        return minRkap;
    }

    int maxFlow(){
        int flöde = 0;

        while(bfs()){
            flöde += updtFlowGraph();
        }
        return flöde;
    }

    void writeFlow(){

        int mf = maxFlow();

        io.println(cache4xy[0] + " " + cache4xy[1]);
        io.println(mf);

        for(int i = 0; i < e; i++){
            if(rkap[2*i+1] > 0){
                io.println(a[i] + " " + b[i]);
            }
        }
    }


    BipMatch() {
	io = new Kattio(System.in, System.out);

    readBipartiteGraph();
    
    writeFlow();
    
	// debugutskrift
	System.err.println("Flow avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new BipMatch();
    }
}
