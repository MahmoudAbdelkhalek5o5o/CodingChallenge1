package CompressionTool.HuffmanTree;

import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanTree {

    private Node root;
    private HashMap<Character, String> characterCodeMap = new HashMap<>();
    public HuffmanTree(HashMap<Character, Integer> characterFrequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());
        for (Character c : characterFrequencyMap.keySet()) {
            priorityQueue.add(new Node(c, characterFrequencyMap.get(c)));
        }
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node(null, left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }
        root = priorityQueue.poll();
        generateCharacterCode(root, "");
    }
    private void generateCharacterCode(Node node, String code) {
        if(node.left == null && node.right == null) {
            characterCodeMap.put(node.c, code);
            return;
        }
        generateCharacterCode(node.left, code + "0");
        generateCharacterCode(node.right, code + "1");
    }
    public HashMap<Character, String> getCharacterCodeMap() {
        return characterCodeMap;
    }
}
