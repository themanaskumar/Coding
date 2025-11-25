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
