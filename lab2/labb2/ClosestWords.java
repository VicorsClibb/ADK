/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@kth.se           */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  int[][] dynProgMatris = null;

  
  int dynPartDist(String w1, String w2, int w1len, int w2len, int p){

    int kostnad;

    for(int i = 1; i <= w1len; i++){

      for(int j = p + 1; j <= w2len; j++){
        
        if(w1.charAt(i - 1) == w2.charAt(j - 1)){
  
          kostnad = 0;
        } else {

          kostnad = 1;
        }

        dynProgMatris[i][j] = 
          Math.min(
            Math.min((dynProgMatris[i-1][j-1] + kostnad), dynProgMatris[i-1][j]+1),
            dynProgMatris[i][j-1]+1);
      }
    }

    return dynProgMatris[w1len][w2len];
  }

  int distance(String w1, String w2, int p) {

    return dynPartDist(w1, w2, w1.length(), w2.length(), p);
  }

  public ClosestWords(String w, List<String> wordList) {

    //
    dynProgMatris = new int[w.length() + 1][40];
    //Föredetta ord
    String tidigare = "";
    
    for (int i = 0; i <= w.length(); i++){
      dynProgMatris[i][0] = i;
    }

    for (int j = 0; j <= 39; j++){
      dynProgMatris[0][j] = j;
    }


    for (String s : wordList) {
      int p = 0;

      while(p < Math.min(s.length(), tidigare.length()) && s.charAt(p) == tidigare.charAt(p)){
        p++;
      }
      int dist = distance(w, s, p);

      tidigare = s;

      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
