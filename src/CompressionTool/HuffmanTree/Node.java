package CompressionTool.HuffmanTree;

public class Node {
    Character c;
    int freq;
    public Node left;
    public Node right;
    public Node(Character c, int freq) {
        this.c = c;
        this.freq = freq;
    }
}
