package edu.iastate.cs2280.hw4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jared Krug
 */

public class MsgTree {

	/**
	 * character stored in this node
	 */
	public char payloadChar;
	
	/**
	 * left child/subtree
	 */
	public MsgTree left;
	
	/**
	 * right child/subtree
	 */
	public MsgTree right;
	
	/**
	 * index used to iterate through encoded string
	 */
	private static int staticCharIdx = 0;
	
	/**
	 * binary message in a string
	 */
	private static String encodingString;
	
	
	/**
	 * Constructor that constructs a MsgTree object using the given encodingString
	 * @param encodingString - Encoded string used to build up the MsgTree
	 */
	public MsgTree(String encodingString) {
		//Error/debug check
		if (staticCharIdx >= encodingString.length()) {
			System.err.println("staticCharIdx: " + staticCharIdx + ", encodingString length: " + encodingString.length());
			throw new IllegalStateException("Encoding string ended at index " + staticCharIdx);
		}
		
		char current = encodingString.charAt(staticCharIdx); //gets current char from encodingString
		staticCharIdx++;
		
		
//		//Debug Statement:
//		System.out.println("Processing character: '" + current + "' at index: " + (staticCharIdx - 1));
		
		
		if (current == '^') { //internal node
			this.payloadChar = current;
			
			
//			//Debug Statement:
//			System.out.println("Creating internal node: '^'");
			
			
			if (staticCharIdx < encodingString.length()) {
				this.left = new MsgTree(encodingString); //recursively constructs left subtree
			}
			else { //for debugging
				throw new IllegalStateException("Encoding string ended prematurely before constructing left child.");
			}
			
			
			
			if (staticCharIdx < encodingString.length()) {
				this.right = new MsgTree(encodingString); //recursively constructs right subtree
		    }
			else { //for debugging
				throw new IllegalStateException("Encoding string ended prematurely before constructing right child.");
			}
		}
		else { //leaf node
			this.payloadChar = current;
			
			
//			//Debug Statement:
//			System.out.println("Creating leaf node: '" + current + "'");
			
			
			this.left = null;
			this.right = null;
		}
	}
	
	/**
	 * Constructor for a single node with null children
	 * @param payloadChar - character to set to this.payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}
	
	
	/**
	 * Recursively prints the binary codes in a MsgTree
	 * @param root - root of MsgTree
	 * @param code - binary code for current node
	 */
	public static void printCodes(MsgTree root, String code) {
		//base case
		if (root == null) {
			return;
		}
		
		if (root.payloadChar != '^') {
			
			if (root.payloadChar == ' ') {
				System.out.println("   " + "space" + "    " + code);
			}
			
			else if (root.payloadChar == '\n') {
				System.out.println("   " + "\\n" + "\t    " + code);
			}
			
			else {
				System.out.println("   " + root.payloadChar + "\t    " + code);
			}
		}
		
		//recursion
		printCodes(root.left, code + "0"); //recursively traverse left subtree, append 0
		printCodes(root.right, code + "1"); //recursively traverse right subtree, append 1
	}
	
	/**
	 * Method that decodes a binary message using the given MsgTree
	 * @param codes - MsgTree for decoding
	 * @param msg - binary string to be decoded
	 */
	public void decode(MsgTree codes, String msg) {
		MsgTree currNode = codes; //node at the root of MsgTree
		
		System.out.println("MESSAGE");
		
		for (char digit : msg.toCharArray()) {
			if (digit == '0') { //move left for 0
				currNode = currNode.left; 
			}
			else if (digit == '1') { //move right for 1
				currNode = currNode.right;
			}
			
			if (currNode.left == null && currNode.right == null) { //checks for leaf node
				System.out.print(currNode.payloadChar);
				currNode = codes; //resets to root of MsgTree
			}
		}
		System.out.println();
	}
	
	/**
	 * Gets the .arch file from the command line argument, builds a binary tree with the encoded string,
	 * prints the character codes, decodes the message, and finally prints the decoded message
	 * @param args
	 */
	public static void main(String[] args) {
		//error check - checks that only 1 argument is given
		if (args.length != 1) {
			System.err.println("Error: 1 argumemt needed...");
			return;
		}
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(args[0]));
			
			if (lines.isEmpty()) {
				throw new IllegalStateException("Empty file...");
			}
			
			encodingString = "";
			//iterates through all lines except for the last and adds them to encodingString
			//adds the newline character after every newline.
			for (int i = 0; i < lines.size() - 1; i++) {
				encodingString += lines.get(i) + "\n";
			}
			
			//gets rid of the last newline character added
			encodingString = encodingString.substring(0, encodingString.length() - 1);
			
			
//			//Debug Statement:
//			System.out.println(encodingString);

			
			//the message is the last line. trimmed to ensure no whitespace
			String encodedMessage = lines.get(lines.size() - 1).trim(); 
			
			MsgTree root = new MsgTree(encodingString);
			
			
//			//Debug Statement:
//			root.printTree(root, "");
			
			
			//print the codes
			System.out.println("character   code");
			System.out.println("-------------------------");
			MsgTree.printCodes(root, "");
			
			//decode the message
			root.decode(root, encodedMessage);
			
			
		} catch (Exception e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}
	
//	/**
//	 * HELPER METHOD
//	 * Only here to assist me with visualizing how the tree looks to make sure it is set up correctly
//	 * Commented out
//	 * @param node
//	 * @param prefix
//	 */
//	public void printTree(MsgTree node, String prefix) {
//		if (node == null) return;
//		System.out.println(prefix + (node.payloadChar == '^' ? "Internal node" : "Leaf: " + node.payloadChar));
//		printTree(node.left, prefix + " ");
//		printTree(node.right, prefix + " ");
//	}
}
