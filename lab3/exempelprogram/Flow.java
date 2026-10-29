import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Flow {
    Kattio io;
    int v, s, t, e;
    int[] till,rkap;
    int[] prev;

    ArrayList<Integer> [] grannListor;

    void readFlowGraph(){
        v = io.getInt(); //Antal hörn
        s = io.getInt(); //Källa
        t = io.getInt(); //Utlopp
        e = io.getInt(); //Antal kanter

        till = new int[e*2];
        rkap = new int[e*2];


        grannListor = new ArrayList[v + 1];

        for (int i = 0; i < v + 1; i++){
            grannListor[i] = new ArrayList<>(); 
        }

        for(int i = 0; i < e; i++){

            int a = io.getInt(); //Från noden
            int b = io.getInt(); //Till noden
            int c = io.getInt(); //Kapaciteten

            till[i*2] = b;
            till[i*2+1] = a;

            rkap[i*2] = c;
            rkap[i*2+1] = 0;

            grannListor[a].add(i*2);
            grannListor[b].add(i*2+1);
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
                }

                if(nod == t){
                    return true;
                }
            }
        }
        return false;
    }



    Flow() {
	io = new Kattio(System.in, System.out);

    readFlowGraph();

	// debugutskrift
	System.err.println("Flow avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new Flow();
    }
}
