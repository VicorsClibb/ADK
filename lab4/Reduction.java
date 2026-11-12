import java.util.ArrayList;
import java.util.Iterator;


public class Reduction{




    Kattio io;
    //int[] sceneList1;
    //int[] sceneList2;
    int role1 = 1;
    ArrayList<Integer> isolated = new ArrayList<Integer>();


    void readGraph(){


        int nrOfVertices = io.getInt();
        int nrOfEdges = io.getInt();
        int colorPalette = io.getInt();
        int[] sceneList1 = new int[nrOfEdges];
        int[] sceneList2 = new int[nrOfEdges];
       


        for(int i = 0; i < nrOfEdges; i++){
            int temp_a = io.getInt();
            int temp_b = io.getInt();
            sceneList1[i] = temp_a;
            sceneList2[i] = temp_b;


            if (!isolated.contains(temp_a)){
                isolated.add(temp_a);
            }
            if (!isolated.contains(temp_b)){
                isolated.add(temp_b);
            }


        }


        for(int i = 0; i < nrOfVertices; i++){
            if(isolated.contains(i)){
                isolated.remove(i); //kvar är de isolerade noderna.
            }
        }




        writeRoleAssignmentProblem(nrOfVertices, nrOfEdges, colorPalette, sceneList1, sceneList2);


    }




    void writeRoleAssignmentProblem(int vertices, int edges, int colors, int[] sceneList1, int[] sceneList2){


        int nrRoles = vertices + 2;
        int nrScenes = edges + 2 + isolated.size();
        int nrActors = colors + 2;
        int updtColors = colors;


        io.println(nrRoles);
        io.println(nrScenes);
        io.println(nrActors);


        for(int i = 0; i < vertices; i++){


            io.println(colors);


            while(colors > 0){
                io.println(updtColors - 1);
                updtColors = updtColors - 1;
            }
        }


        io.println(1);
        io.println(colors + 1);


        io.println(1);
        io.println(colors + 2);
         
        for(int i = 0; i < edges; i++){
            io.println(2);
            io.println(sceneList1[i]);
            io.println(sceneList2[i]);
        }


        io.println(2);
        io.println(vertices + 1);
        io.println(role1);


        io.println(2);
        io.println(vertices + 2);
        io.println(role1);


        Iterator<Integer> iterateIsolated = isolated.iterator();


        while(iterateIsolated.hasNext()){
            io.println(2);
            io.println(vertices + 1); //använder diva 1 som utfyllnad för alla isolerade noder.
            io.println(iterateIsolated.next());
        }




    }
}
