package JSONParser;

import java.io.File;
import java.util.*;

public class JSONParser {
    public static void main(String[] args) {
        StringBuilder text = new StringBuilder();
        try {
//            if(args.length == 0)
//                throw new Exception("No file name provided");
            //production code
            File jsonFile = new File("tests/"+args[0]);
            // test code
//            File jsonFile = new File("src/CC2/tests/step4/valid.json");
            Scanner myReader = new Scanner(jsonFile);
            while (myReader.hasNextLine()) {
                text.append(myReader.nextLine()+"");
            }
            text = new StringBuilder(text.toString().trim());
            myReader.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(255);
        }
        try {
            if (!verifyParenthesis(text.toString())) {
                throw new Exception("Invalid Json");
            }
            isJson(text.substring(1,text.length()-1).toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
            System.exit(1);
        }
        System.out.println("Valid Json");
    }
    private static boolean isJson(String text) throws Exception {
        // verify key value pairs
        if(text.length() == 0) return true;
        text = text.trim();
        if(text.charAt(0) == ',' || text.charAt(text.length()-1) == ',')
            throw new Exception("Invalid Json, key-value pair expected");
        // draft
        int i = 0;
        text = text.replaceAll(" ", "");
        while(i<text.length()){
            //get key
            String key = "";
            while (text.charAt(i) != ':'){
                key += text.charAt(i);
                i++;
            }
            // skip ':'
            i++;
            // check key validity
            if(key.charAt(0) != '"' || key.charAt(key.length()-1) != '"')
                throw new Exception("Invalid Json, keys must be strings");

            i = getJsonValueAndVerifyToReturnIndexAfter(text, i);
        }
        return true;
    }
    private static boolean verifyParenthesis(String text){
        if(text.length()<2)
            return false;
        if(text.charAt(0) != '{' || text.charAt(text.length()-1) != '}')
            return false;
        return true;
    }

    private static boolean verifyValue(String value) throws Exception {
        // check if value is a string
        if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"')
            return true;
        // check if value is a boolean
        if(value.equals("true") || value.equals("false"))
            return true;
        // check if value is a null
        if(value.equals("null"))
            return true;
        // check if value is a number
        if(value.matches("[-+]?\\d*\\.?\\d+"))
            return true;
        throw new Exception("Invalid Json, value is not a string, number, boolean or null");
    }
    private static boolean validArray(String text) throws Exception{
        int i = 0;
        while (i<text.length()){
            i = getJsonValueAndVerifyToReturnIndexAfter(text, i);
        }
        return true;
    }
    public static int getJsonValueAndVerifyToReturnIndexAfter(String text, int i) throws Exception{
        String value = "";
        Stack<Character> stack = new Stack<>();
        // get JSON value
        if (text.charAt(i) == '{') {
            value+=text.charAt(i);
            stack.push(text.charAt(i++));
            while(i<text.length() && !stack.isEmpty()) {
                if (text.charAt(i) == '{' || text.charAt(i) == '[')
                    stack.push(text.charAt(i));
                if ((text.charAt(i) == '}' && stack.peek() == '{') || (text.charAt(i) == ']' && stack.peek() == '[')) {
                    stack.pop();
                }
                value += text.charAt(i);
                i++;
            }
            if(!isJson(value.substring(1,value.length()-1))){
                throw new Exception("Invalid Json");
            }
        }
        // get array value
        else if (text.charAt(i) == '[') {
            value+=text.charAt(i);
            stack.push(text.charAt(i++));
            while (i < text.length() && !stack.isEmpty()) {
                if (text.charAt(i) == '{' || text.charAt(i) == '[')
                    stack.push(text.charAt(i));
                if ((text.charAt(i) == '}' && stack.peek() == '{') || (text.charAt(i) == ']' && stack.peek() == '[')) {
                    stack.pop();
                }
                value += text.charAt(i);
                i++;
            }
            if(!validArray(value.substring(1,value.length()-1))){
                throw new Exception("Invalid Json");
            }
        }
        // get ordinary value
        else {
            while (i<text.length() && text.charAt(i)!=','){
                value += text.charAt(i);
                i++;
            }
            if(!verifyValue(value)){
                throw new Exception("Invalid Json");
            }
        }


        if(!stack.isEmpty()){
            throw new Exception("Invalid Json");
        }
        //not ending with comma
        if(i == text.length()-1 && text.charAt(i) == ','){
            throw new Exception("Invalid Json");
        }
        i++;
        return i;
    }
}
