package CompressionTool;

import CompressionTool.HuffmanTree.HuffmanTree;
import CompressionTool.HuffmanTree.Node;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

public class Decompress {
    private HashMap<Character, String> characterCodeMap;
    private BitSet compressedBits;

    private HashMap<String, Character> reverseCharacterCodeMap = new HashMap<>();

    private void deserialize(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/CompressionTool/tests/"+fileName));
        characterCodeMap = (HashMap<Character, String>) in.readObject();
        compressedBits = (BitSet) in.readObject();
        for (Character c : characterCodeMap.keySet()) {
            reverseCharacterCodeMap.put(characterCodeMap.get(c), c);
        }
    }

    private void decompress(String inputFileName, String outputFileName) throws IOException, ClassNotFoundException {
        deserialize(inputFileName);
        String decompressedText = decompressText();
        writeDecompressedFile(decompressedText, outputFileName);
    }

    private String decompressText(){
        StringBuilder decompressedText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        for(int i = 0;i<compressedBits.length();i++){
            if(compressedBits.get(i)){
                currentCode.append("1");
            }
            else{
                currentCode.append("0");
            }
            if(reverseCharacterCodeMap.containsKey(currentCode.toString())){
                decompressedText.append(reverseCharacterCodeMap.get(currentCode.toString()));
                currentCode = new StringBuilder();
            }
        }
        return decompressedText.toString();
    }

    private void writeDecompressedFile(String decompressedText, String outputFileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/CompressionTool/tests/"+outputFileName));
        writer.write(decompressedText);
        System.out.println("Data has been written to " + outputFileName);
    }

    public static void main(String[] args) throws Exception {
        try {
            if(args.length == 0)
                throw new Exception("No file name provided");
            if(args.length == 1)
                throw new Exception("No output file name provided");
            if(args.length > 2)
                throw new Exception("Too many arguments");
            Decompress decompress = new Decompress();
            decompress.decompress(args[0], args[1]);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(255);
        }
    }
}
