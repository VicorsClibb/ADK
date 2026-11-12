import java.util.ArrayList;
import java.util.Iterator;


public class Reduction{

    Kattio io;
    //int[] sceneList1;
    //int[] sceneList2;
    int fillerRole = 3;
    ArrayList<Integer> isolated = new ArrayList<Integer>();

    void readGraph(){


        int nrOfVertices = io.getInt();

        int nrOfEdges = io.getInt();
        int colorPalette = io.getInt();

        int[] sceneList1 = new int[nrOfEdges];
        int[] sceneList2 = new int[nrOfEdges];
       
        //Börjar från 2 då noder börjar från 1 och nod 1 blir med i en scen per definition,
        //då den används som utfyllnads nod.
        for(int i = 2; i <= nrOfVertices; i++){
            isolated.add(i);
        }

        for(int i = 0; i < nrOfEdges; i++){
            int temp_a = io.getInt();
            int temp_b = io.getInt();
            sceneList1[i] = temp_a;
            sceneList2[i] = temp_b;


            if (isolated.contains(temp_a)){
                isolated.remove((Integer) temp_a);
            }

            if (isolated.contains(temp_b)){
                isolated.remove((Integer) temp_b);
            }
        }
        writeRoleAssignmentProblem(nrOfVertices, nrOfEdges, colorPalette, sceneList1, sceneList2);
    }

    void writeRoleAssignmentProblem(int vertices, int edges, int colors, int[] sceneList1, int[] sceneList2){

        int nrRoles = vertices + 2;

        int nrScenes = edges + 2 + isolated.size();

        //int maxValens = vertices - 1;

        int maxColorsNeeded = (vertices > 1) ? Math.min(colors, vertices) : 1;

        int totalActors = maxColorsNeeded + 2;
        int availableActors = totalActors; //tilldelade skådespelare = max färger


        io.println(nrRoles);
        io.println(nrScenes);
        io.println(totalActors);

        //Roller som kan besättas av skådespelare
        io.print(1 + " ");
        io.println(1);

        io.print(1 + " ");
        io.println(2);

        for(int i = 1; i <= vertices; i++){


            io.print(maxColorsNeeded);
            
            while(availableActors > 2){
                io.print(" " + availableActors);
                availableActors--;
            }
            availableActors = totalActors;
            io.println();
        }
        
        //Scener som kan spelas av roller         
        for(int i = 0; i < edges; i++){
            io.print(2);

            int firstRoll = sceneList1[i] + 2;
            int secondRoll = sceneList2[i] + 2;
    
            io.print(" " + firstRoll);
            io.println(" " + secondRoll);
        }

        io.print(2 + " ");
        io.print(1 + " ");
        io.println(fillerRole);


        io.print(2 + " ");
        io.print(2 + " ");
        io.println(fillerRole);

        Iterator<Integer> iterateIsolated = isolated.iterator();

        
        while(iterateIsolated.hasNext()){
            io.print(2);
            io.print(" " + 1); //använder diva 1 som utfyllnad för alla isolerade noder.
            io.println(" " + (iterateIsolated.next() + 2));
        }
    }
    public static void main(String[] args) {

        Reduction reduction = new Reduction();

        reduction.io = new Kattio(System.in, System.out);

        reduction.readGraph();

        reduction.io.close();


    }
}
