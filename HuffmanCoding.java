import java.util.*;

// Node for huffman tree
class HuffmanNode{
	int data;
	char c;
	HuffmanNode left;
	HuffmanNode right;
}

// Comparator to order nodes by frequency
class MyComparator implements Comparator<HuffmanNode>{
	public int compare(HuffmanNode x, HuffmanNode y){
		return (x.data - y.data);
	}
}

public class HuffmanCoding{
	public static void printCode(HuffmanNode root, String s, Map<Character, String> huffmanCode){
		// Base case: reached the leaf node
		if(root.left == null && root.right == null){
			huffmanCode.put(root.c, s);
			System.out.println(root.c + " : " + s);
			return;
		}
		printCode(root.left, s+"0", huffmanCode);
		printCode(root.right, s+"1", huffmanCode);
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		// Read the string from user
		System.out.print("Enter a string to encode using huffman coding: ");
		String msg = sc.nextLine();
		sc.close();
		
		// Count the frequesncies and store in a hadhmap
		Map<Character, Integer> freq = new HashMap<>();
		for(char c: msg.toCharArray()){
			freq.put(c, freq.getOrDefault(c, 0)+1);
		}
		
		// Create a priority queue (min heap)
		PriorityQueue<HuffmanNode> q = new PriorityQueue<>(new MyComparator());
		
		// Create a leaf node for each character
		for(Map.Entry<Character, Integer> entry: freq.entrySet()){
			HuffmanNode hn = new HuffmanNode();
			hn.c = entry.getKey();
			hn.data = entry.getValue();
			hn.left = null;
			hn.right = null;
			q.add(hn);
		} 
		
		HuffmanNode root = new HuffmanNode();
		// Create the huffman tree
		while(q.size() > 1){
			HuffmanNode x = q.poll();
			HuffmanNode y = q.poll();
			
			HuffmanNode f = new HuffmanNode();
			f.data = x.data + y.data;
			f.c = '-';
			f.left = x;
			f.right = y;
			root = f;
			q.add(f);
		}
		
		// Print huffman codes
		System.out.println("\nHuffman Codes for Characters:");
		Map<Character, String> huffmanCode = new HashMap<>();
		printCode(root, "", huffmanCode);
		
		// Encode the input string
		StringBuilder encoded = new StringBuilder("");
		for(char c: msg.toCharArray()){
			encoded.append(huffmanCode.get(c));
		}
		System.out.println("Encoded string: " + encoded);
		
		// Decode the encoded string
		StringBuilder decoded = new StringBuilder("");
		HuffmanNode current = root;
		for(int i = 0; i < encoded.length(); i++){
			current = (encoded.charAt(i) == '0') ? current.left : current.right;
			if(current.left == null && current.right == null){
				decoded.append(current.c);
				current = root;
			}
		}
		System.out.println("Decode string: " + decoded);
	}
}

/*
Algorithm: Huffman Coding
Step 1: Start.
Step 2: Read the input string (message) from the user.
Step 3: Calculate character frequencies:
	3.1: Create a frequency map (or table) to store the count of occurrences for each unique character in the string.
Step 4: Initialize the Priority Queue:
	4.1: Create a leaf node for each unique character containing its character and frequency data.
	4.2: Insert all leaf nodes into a Min-Priority Queue (Min-Heap). The queue orders nodes based on frequency (ascending).
Step 5: Build the Huffman Tree:
	5.1: While the Priority Queue contains more than one node:
		a. Extract the node with the lowest frequency (call it left).
		b. Extract the node with the next lowest frequency (call it right).
		c. Create a new internal parent node. Set its frequency as the sum of left and right frequencies.
		d. Set the left node as the left child and the right node as the right child of the new parent node.
		e. Insert the new parent node back into the Priority Queue.
	5.2: The remaining node in the queue is the Root of the Huffman Tree.
Step 6: Generate Huffman Codes:
	6.1: Traverse the tree starting from the Root to each leaf node.
	6.2: Assign '0' for every left edge traversal and '1' for every right edge traversal.
	6.3: Store the generated binary code for each character in a map/table.
	6.4: Print the code for each character.
Step 7: Encode the String:
	7.1: Create an empty string encodedString.
	7.2: For each character in the original input string, retrieve its corresponding binary code from the map and append it to encodedString.	
	7.3: Print the encodedString.
Step 8: Decode the String (Verification):
	8.1: Initialize a pointer current to the Root of the tree.
	8.2: Iterate through the encodedString bit by bit:
		a. If the bit is '0', move current to the left child.
		b. If the bit is '1', move current to the right child.
		c. If current is a leaf node Print the character stored in the node. Reset current back to the Root to decode the next character.
Step 9: Stop.
 */