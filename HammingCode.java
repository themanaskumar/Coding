import java.util.*;

public class HammingCode{
	static int calcParity(int[] res, int pos){
		int parity = 0;
		for(int i = 0; i < res.length; i++){
			if((i & pos) != 0){
				parity ^= res[i];
			}
		}
		return parity;
	}
	static int[] encode(String data){
		int m = data.length();
		int p = 1;
		while(Math.pow(2, p) < (m+p+1)){
			p++;
		}
		int n = m+p;
		int[] res = new int[n+1];
		int j = 0;
		// Put the message bits into the resultant array
		for(int i = 1; i <= n; i++){
			if((i & (i-1)) == 0) continue;
			res[i] = data.charAt(j++)-'0';
		}
		// Calculate and put parity bits
		for(int i  = 0; i < p; i++){
			int pos = 1 << i;
			res[pos] = calcParity(res, pos);
		}
		return res;
	}
	
	//Funtion to detect and correct the error
	static void detectAndCorrect(int[] bits){
		int n = bits.length-1, errorPos = 0, i = 0;
		while((1<<i) <= n){
			int pos = 1 << i;
			int parity = 0;
			for(int j = 1; j <= n; j++){
				if((j & pos) != 0){
					parity ^= bits[j];
				}
			}
			if(parity != 0){
				errorPos += pos;
			}
			i++;
		}
		// Display the error position found and correct it
		if(errorPos != 0){
			System.out.println("Error detected at position: "+ errorPos);
			bits[errorPos] ^= 1;
		}else{
			System.out.println("No error detected.");
		}
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		//Read the bit string
		System.out.print("Enter the bit string: ");
		String bits = sc.nextLine();
		// Encode the message with parity bits
		int[] encoded = encode(bits);
		System.out.print("Encode message: ");
		for(int i = 0; i < encoded.length; i++){
			System.out.print(encoded[i]+" ");
		}
		System.out.println();
		
		// Introduce error in the encoded message
		System.out.print("Enter error position to flip (0 for no error): ");
		int errorPos = sc.nextInt();
		if(errorPos > 0 && errorPos < encoded.length){
			encoded[errorPos] ^= 1;
		}
		// Print the received message
		System.out.print("Received message: ");
		for(int i = 0; i < encoded.length; i++){
			System.out.print(encoded[i] + " ");
		}
		System.out.println();
		
		// Detect and correct the error
		detectAndCorrect(encoded);
		System.out.print("Corrected message: ");
		for(int i = 0; i < encoded.length; i++){
			System.out.print(encoded[i] + " ");
		}
		System.out.println();
		sc.close();
	}
}

/*
Algorithm: Hamming Code (7,4) Implementation

Step 1: Start.
Step 2: Read the binary string (data) from the user.
Step 3: Encode the message into a binary array:
    3.1: Calculate the number of redundant/parity bits (p) required using the formula: 2^p >= m + p + 1 (where m is data length).
    3.2: Create an array of size (m + p + 1).
    3.3: Place the data bits into the array at positions that are NOT powers of 2 (3, 5, 6, 7, etc.).
    3.4: Initialize positions 1, 2, 4, 8... (powers of 2) as 0.
    3.5: Calculate the value for each parity bit position:
         For each parity bit at position 2^i, perform XOR on all bits whose binary representation has a 1 at the i-th position. Store the result at position 2^i.

Step 4: Print the encoded binary array.
Step 5: Ask the user to enter a position to introduce an error.
Step 6: If the entered position is valid (> 0), flip the bit (0 to 1 or 1 to 0) at that position in the array.
Step 7: Print the binary array with the error (Received Message).

Step 8: Detect and correct the error:
    8.1: Initialize 'errorPos' variable to 0.
    8.2: Iterate through parity positions (1, 2, 4...) using variable 'p':
         a. Calculate the parity check for the current power of 2 (similar to Step 3.5 but including the parity bit itself).
         b. If the calculated parity is 1 (indicating an error in this subset), add the current position value 'p' to 'errorPos'.
    8.3: Check the final value of 'errorPos':
         a. If 'errorPos' is 0, print "No error detected."
         b. If 'errorPos' is not 0, print "Error detected at position: errorPos" and flip the bit at that index to correct it.

Step 9: Print the error-corrected binary array.
Step 10: Stop.
*/
