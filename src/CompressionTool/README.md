# CompressionTool

## Overview
`CompressionTool` is a Java application that demonstrates the compression of a text file using Huffman coding. The project contains two main components:
- `Compress.java`: The main class that handles the compression process.
- `HuffmanTree.java`: A helper class that builds the Huffman Tree and generates Huffman codes.

## Directory Structure

- project/
- └── src/ 
- └── CompressionTool/
- ├── Compress.java
- ├── Decompress.java
- └── HuffmanTree/
- - └── HuffmanTree.java
- - └── Node.java
- - └── NodeComparator.java

## Requirements
- Java Development Kit (JDK) 8 or higher
- Git (for cloning the repository)

## Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/MahmoudAbdelkhalek5o5o/CodingChallenge1.git
   cd CodingChallenge1/src

2. **Compile src folders**
    ```sh
   javac -d out -sourcepath src src/CompressionTool/*.java src/CompressionTool/HuffmanTree/*.java
3. **Run the program**
    ```sh
   java -cp out CompressionTool.Compress test.txt test.ser
   
4. **To decode**
    ```sh
   java -cp out CompressionTool.Decompress test.ser out.txt
