import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo =
                  " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String separator = "------------------\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("No unicode allowed");
        Scanner in = new Scanner(System.in);
        String line;
        List<Task> store = new ArrayList<>();
        String tokens[] = {"invalid"};
        do{
            System.out.print(separator + "Listening to your input: ");
            line = in.nextLine();
            try {
                tokens = splitTokenIntoTwo(line," ", "bye");
                switch(tokens[0]){
                    case "bye":
                        System.out.println(separator + "Goodbye from\n" + logo);
                        break;
                    case "list":
                        for (int i = 0 ; i < store.size(); i++) {
                            System.out.println(formatOrderedPrint(store,i));
                        }
                        break;
                    case "done":
                        int index = Integer.valueOf(tokens[1]) - 1;
                        Task t = store.get(index);
                        t.isDone = true;
                        System.out.println("The following task is now marked as done:\n" +
                                formatOrderedPrint(store,-1));
                        break;
                    case "todo":
                        store.add(new ToDos(tokens[1]));
                        System.out.println("The following todo item has been added:\n" +
                                formatOrderedPrint(store,-1));
                        break;
                    case "deadline":
                        tokens = splitTokenIntoTwo(tokens[1]," /by ");
                        store.add(new Deadline(tokens[0],tokens[1]));
                        System.out.println("The following deadline item has been added:\n" +
                                formatOrderedPrint(store,-1));
                        break;
                    case "event":
                        tokens = splitTokenIntoTwo(tokens[1]," /by ");
                        store.add(new Event(tokens[0],tokens[1]));
                        System.out.println("The following event item has been added:\n" +
                                formatOrderedPrint(store,-1));
                        break;
                    default:
                        throw new InvalidCommandException(tokens[0]);
                }
            } catch (ParseException e) {
                System.out.println("Command has invalid parsing.");
                System.out.println(e.getMessage());
            } catch (InvalidCommandException e){
                System.out.println(e.getMessage());
            } catch(EmptyArgument e){
                System.out.println("Cannot have empty argument");
            }
        }while(!tokens[0].equals("bye"));
        in.close();
    }
    private static String formatOrderedPrint(List<Task> tasks, int i){
        final int size = tasks.size();
        while (i < 0){
            i += size;
        }
        while (i >= size){
            i -= size;
        }
        return "Entry " + (i+1) + "|" + tasks.get(i).toString();
    }
    private static String[] splitTokenIntoTwo(String parseTarget,String delimiter) throws ParseException{
        String[] tokens = parseTarget.split(delimiter,2);
        if (tokens.length < 2){
            throw new ParseException("Expected deliminter '"+ delimiter +"'", tokens[0].length());
        }
        return tokens;
    }
    
    private static String[] splitTokenIntoTwo(String parseTarget,String delimiter, String exception) throws ParseException{
        String[] tokens = parseTarget.split(delimiter,2);
        if (!tokens[0].equals(exception) && tokens.length < 2){
            throw new ParseException("Expected deliminter '"+ delimiter +"'", tokens[0].length());
        }
        return tokens;
    }
}
