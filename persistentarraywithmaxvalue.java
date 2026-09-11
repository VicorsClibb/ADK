import java.util.Scanner;
import java.util.Stack;

public class persistentarraywithmaxvalue {
    

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        PersistentArray array = PersistentArray.newarray();

        Stack<PersistentArray> versions = new Stack<>();


        while (scanner.hasNextLine()){
            String string = scanner.nextLine().trim();

            if (string.isEmpty()) continue;
            
            String [] tokens = string.split("\\s+");

            String command = tokens[0];
            try{
            if (command.equals("set")){

                if (tokens.length < 3) {
                    continue; 
                }

                int index = Integer.parseInt(tokens[1]);
                int value = Integer.parseInt(tokens[2]);

                versions.push(array);

                array = array.set(array, index, value);

            }else if(command.equals("get")){

                if (tokens.length < 2) {
                    continue;
                }

                int index = Integer.parseInt(tokens[1]);

                int i = array.get(array, index);

                System.out.println(i);

            }else if(command.equals("unset")){
                if(versions.size() > 0){
                    array = versions.pop();
                }

            }else if(command.equals("maxininterval")){
                
                if (tokens.length < 3) {
                    continue;
                }

                int left = Integer.parseInt(tokens[1]);
                int right = Integer.parseInt(tokens[2]);

                int max = array.maxininterval(array, left, right);

                System.out.println(max);

            }
        }catch(Exception e){}
        }
        scanner.close();
    }
}
