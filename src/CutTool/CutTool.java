package CutTool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CutTool {
    StringBuilder inputStream;
    List<String [] > entities;
    List<Integer> fieldsNumbers;
    Character delimiter;
    String fileName;
    boolean tail;
    int numOfEntities;
    StringBuilder result;

    public CutTool(){
        entities = new ArrayList<String [] >();
        fieldsNumbers = new ArrayList<Integer>();
        result = new StringBuilder();
        delimiter = '\t';
    }

    private void run() throws IOException {
        String command = readCommand();
        // run the command
        runCommand(command);
    }
    public void readTSVFile(String filePath) throws IOException{
        String fileDelemiter = "\t";
        if(filePath.endsWith(".csv")){
            fileDelemiter = ",";
        }
        if(filePath.equals("-")){
            String [] lines = inputStream.toString().split("\n");
            for (String line : lines) {
                // Split the line by tab character
                entities.add(line.split(fileDelemiter));
            }
        }
        // Read all lines from the TSV file
        List<String> lines = Files.readAllLines(Paths.get("src/CutTool/tests/"+filePath), StandardCharsets.UTF_8);
        for (String line : lines) {
            // Split the line by tab character
            entities.add(line.split(fileDelemiter));
        }
    }

    private String readCommand(){
        Scanner sc = new Scanner(System.in);
        // String command = sc.nextLine();
        inputStream = new StringBuilder();
//        while(sc.hasNextLine()){
//            inputStream.append(sc.nextLine());
//        }
        return "cut -d, -f\"1 2\" fourchords.csv | head -n5";
    }
    private void execute(int numOfEntities){
        if(numOfEntities == 0){
            numOfEntities = entities.size();
        }
        int inc = 1;
        int i = 0;
        int count = 0;
        if(tail){
            i = entities.size()-1;
            inc = -1;
        }
        for (;count<numOfEntities && i>=0 && i <entities.size();i = i + inc){
            String [] entity = entities.get(i);
            String [] newEntity = new String[fieldsNumbers.size()];
            for (int j = 0; j < fieldsNumbers.size(); j++) {
                int fieldNumber = fieldsNumbers.get(j);
                if(fieldNumber > entity.length){
                    throw new IllegalArgumentException("Invalid field number");
                }
                newEntity[j] = entity[fieldNumber-1];
            }
            count++;
            result.append(String.join(delimiter+"", newEntity));
            result.append("\n");
        }
    }
    private void runCommand(String command) throws IOException {
        // Split the command by space character
        command = command.trim();
        String [] commandParts = command.split("\\|");
        // Check if the command is valid
        if(commandParts.length > 2){
            throw new IllegalArgumentException("Invalid command");
        }
        if(commandParts.length == 1){
            processCommandType1(command);
        }else{
            processCommandType2(command);
        }
        readTSVFile(fileName);
        execute(numOfEntities);
        System.out.println(result.toString());
    }
    private void processCommandType1(String command){
        // check cut keyword
        String [] commandParts = command.split(" ");
        if(commandParts.length < 3){
            throw new IllegalArgumentException("Invalid command");
        }
        if(!commandParts[0].equals("cut")){
            throw new IllegalArgumentException("Invalid command");
        }
        int i = 1;
        for(; i < commandParts.length-1; i++){
            if(commandParts[i].equals("")) continue;
            if(commandParts[i].startsWith("-f")){
                String [] columnNumbers = new String [0];
                String fields = commandParts[i].substring(2);
                if(fields.startsWith("\"")){
                    i++;
                    for(;i<commandParts.length;i++){
                        fields += " "+commandParts[i];
                        if(commandParts[i].endsWith("\"")){
                            break;
                        }
                    }
                    fields = fields.substring(1,fields.length()-1);
                    columnNumbers = fields.split(" ");
                }
                else{
                    // handle commas
                    columnNumbers = fields.split(",");
                }
                for (String columnNumber : columnNumbers) {
                    // Check if the column number is valid
                    int fieldNumber = Integer.parseInt(columnNumber);
                    if(fieldNumber <= 0){
                        throw new IllegalArgumentException("field numbers must be positive integers");
                    }
                    fieldsNumbers.add(fieldNumber);
                }
            }
            if (commandParts[i].startsWith("-d")){
                // find the delimiter
                delimiter = commandParts[i].charAt(2);
            }
        }
        if(i<commandParts.length){
            fileName = commandParts[commandParts.length-1];
        }
        if(fileName.length() == 0){
            throw new IllegalArgumentException("No file name specified");
        }
        if(!fileName.endsWith(".tsv") && !fileName.endsWith(".csv") && !fileName.equals("-")){
            throw new IllegalArgumentException("file must be a TSV or a CSV file");
        }
    }
    private void processCommandType2(String command) {
        String commandParts[] = command.split("\\|");
        String mainCommand = "";
        String optionCommand = "";
        if(commandParts[0].startsWith("cut")) {
            mainCommand = commandParts[0];
            optionCommand = commandParts[1];
        }
        else{
            mainCommand = commandParts[1];
            optionCommand = commandParts[0];
        }
        mainCommand = mainCommand.trim();
        optionCommand = optionCommand.trim();
        processCommandType1(mainCommand);
        String [] optionParts = optionCommand.split(" ");
        if(optionParts.length < 2 ){
            throw new IllegalArgumentException("Invalid command");
        }
        // handle head and tail keyWord
        for(int i = 0; i < optionParts.length; i++) {
            if (optionParts[i].equals("head")) {
                continue;
            }
            if (optionParts[i].equals("tail")) {
                tail = true;
                continue;
            }
            if (optionParts[i].startsWith("-n")) {
                numOfEntities = Integer.parseInt(optionParts[1].substring(2));
                continue;
            }
            if (optionParts[i].endsWith("csv") || optionParts[i].endsWith("tsv")) {
                fileName = optionParts[i];
                continue;
            }
            throw new IllegalArgumentException("Invalid command");
        }
    }
    public static void main(String[] args) {
        CutTool cutTool = new CutTool();
        try {
            cutTool.run();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
