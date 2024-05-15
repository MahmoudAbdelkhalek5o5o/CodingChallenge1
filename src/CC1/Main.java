package CC1;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static long countBytes(String text) {
        return text.getBytes().length;
    }
    private static long countLines(String text) {
        return text.split("\n").length;
    }
    private static long countWords(String text) {
        String lines = text.replaceAll("\n", " ");
        return lines.split(" ").length;
    }
    private static long countCharacters(String text) {
        return text.length();
    }
    public static void main(String[] args) {
        while(true) {
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine().toLowerCase();
            String [] keywords = command.split(" ");
            if(keywords[0].equals("exit")) {
                System.out.println("Exiting the program...");
                break;
            }
            if(keywords.length<2) {
                System.out.println("Invalid Command");
                continue;
            }
            if (!keywords[0].equals("ccwc")) {
                System.out.println("ccwc command not found. Type 'help' to display help.");
            } else{
                String fileName = keywords.length > 2? keywords[2] : keywords[1];
                String operation = keywords.length > 2? keywords[1] : "all";
                File myObj = new File("src/CC1/Files/"+fileName);
                StringBuilder text = new StringBuilder();
                try {
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        text.append(myReader.nextLine()+"\n");
                    }
                    myReader.close();
                } catch (Exception e) {
                    System.out.println("No such file found. Please check the file name and try again. " + fileName);
                    continue;
                }
                switch(keywords[1]) {
                    case "-c":
                        // number of bytes in a file
                        long bytes = countBytes(text.toString());
                        System.out.println(bytes + " " + fileName);
                        break;
                    case "-l":
                        // number of line in a file
                        long lines = countLines(text.toString());
                        System.out.println(lines + " " + fileName);
                        break;
                    case "-w":
                        // number of words in a file
                        long words = countWords(text.toString());
                        System.out.println(words + " " + fileName);
                        break;
                    case "-m":
                        // number of characters in a file
                        long characters = countCharacters(text.toString());
                        System.out.println(characters + " " + fileName);
                        break;
                    case "all":
                        // number of bytes, lines, words, characters in a file
//                        long bytess = countBytes(text.toString());
//                        long liness = countBytes(text.toString());
//                        long wordss = countBytes(text.toString());
                        System.out.println("");
                        break;
                    default:
                        System.out.println("Invalid command. Type 'help' to display help.");
                        break;
                }
            }
        }
    }
}