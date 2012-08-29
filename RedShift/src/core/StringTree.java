package core;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
/**
 * Very simple tree implementation to represent trees of strings.
 * "Leaf nodes" are terminal nodes with no children and a string "value" associated with them
 * "Branch nodes" are nodes with 0 or more children (so they can technically be leaf nodes, but they're different here BECAUSE).
 * More features (remembering last nodes, etc) will be added later (when we actually need the class).
 * @author Eamonn
 */
public class StringTree{
	private static final boolean WALL = true;
	//That turns the massive walls of debugging text on and off
	private Node root;
	
	/**
	 * This is the workhorse of the whole tree, because walking isn't
	 * actually what we want.
	 * I only added it when I realized that it already existed in three or four places.
	 */
	protected static Node descend(Node initial, String...nodepath){
		//Iterative version - If Java's array could pop
		//Or otherwise change it's length, I'd definitely
		//give you a recursive version.
		Node toSender = initial;
		Dbg.psa("Nodepath inside wall", nodepath);
		if( nodepath.length == 0){ //Short circut, just in case.
			return initial;        //This should not need to exist.
		}
		for(String i : nodepath){
			//System.out.println(i + "\n");
			toSender = toSender.getChild(i);
		}
		return toSender;
	}
	
	public StringTree getSubTree(String... path){
		//The only ugly parts of this code are the ones that make a new path array.
		//Sadly, the notion of a string array path thing is only there because it makes the syntax HOT.
		Dbg.psa("Path inside getSubTree", path);
		return new StringTree(descend(root, path));
	}
	
	//Dumb constructor.  Used by getSubTree.
	public StringTree (Node root){
		this.root = root;
	}
	
	//This is the default constructor, and it assumes
	//that this tree stands alone.
	public StringTree(){
		root = branch("root");
	}
	
	/**
	 * Get the value owned by a particular leaf.
	 * @param name Names of nodes after root to get the leaf you want
	 */
	public String getValue(String... name){
		//Debug stuff, remove this!
		return getNode(name).getValue();
	}
	
	/**
	 * Make a new Leaf node and insert it into the tree with the given Name and Value.
	 * @param name   Name of the new Leaf 
	 * @param value  Value owned by the new leaf
	 * @param parent Parent node to attach the leaf to (leave blank for root)
	 */
	public void makeLeaf(String name, String value, String... parent){
		getNode(name).addChild(leaf(name, value));
	}
	
	/**
	 * Make a new branch node and insert it into the tree with the given name and value
	 * @param name   Branch's name
	 * @param parent Parent node to attach the branch to (blank will attach it to root)
	 */
	public void makeBranch(String name, String... parent){
		getNode(name).addChild(branch(name));
	}
	
	/**
	 * Parse a string tree from a string stream.
	 * @throws IOException 
	 */
	public static StringTree fromStream(InputStream inp) throws IOException{
		StringTree toSender = new StringTree();
		toSender.root = parseBranch(inp, "root");
		return toSender;
	}
	
	//Get a string from an inputStream ending in a specific character
	private static String getUntil(InputStream inp, char ender) throws IOException{
		String toSender = new String();   //Algol had the idea of declaring return vars at the beginning of a function...
		while( inp.available() > 0){ //I think they had the right idea.
			char currentChar = (char)inp.read();
			if (currentChar == ender){ //It's the end, bounce back up to parser
				return toSender;
			} else { //It's not the end, so add it to the end of the string
				toSender = toSender.concat( Character.toString(currentChar) );
			}
		}
		//If it does not reach char 'ender' it probably fowls the entire input read,
		//so throw an exception
		throw new RstSyntaxError( "(GetUntil) BAD INPUT: MISSING " + Character.toString(ender) + " ");
	}

	private static StringTree.Node parseBranch(InputStream inp, String name) throws IOException{
		StringTree.Node currentNode = branch(name);
		String nextName = new String(); //Name of the next node
		while( inp.available() > 0 ){
			switch( (char) inp.read() ){
				case ( '[' ): { //It's a label applying to the next node.
					nextName = getUntil(inp, ']');
					Dbg.line("[" + nextName + "]");
					break;
				}
				case ( '(' ): { //It's a leaf node, load the text and attach it to the branch
					currentNode.addChild( leaf( nextName, getUntil( inp, ')' ) ) );
					Dbg.line("(" + currentNode.getChild(nextName).getValue() + ")" );
					break;
				}
				case ( '{' ): { //This is the recursive bit-if it's another branch, recurse!
					Dbg.line("ENTERED NEW BRANCH");
					currentNode.addChild( parseBranch( inp, nextName) );
					break;
				}
				case ( '/' ): {//Comment-just there for completeness
					Dbg.line("Comment:/" + getUntil(inp, '/') + "/"); //This is discarded.
					break;
				}
				case ( '#' ): {//Line comment, in case you really need one.
					Dbg.line("Comment:#" + getUntil(inp, '\n'));
					break;
				}
				case ( '<' ): { //It's a branch without named child nodes
					Dbg.line("ENTERED NEW ANON BRNACH");
					currentNode.addChild( parseAnonBranch(inp, nextName) );
					break;
				}
				case ( '}' ): { //End of branch
					Dbg.line("END OF BRANCH");
					return currentNode;
				}
			}
		} //This should never be passed except if it's the root node, which is in fact terminated by
		if (name != "root"){ //The end of the file.
			throw new RstSyntaxError("Reached eof with no }, is not root branch.\n");
		}
		return currentNode; //If it is root, it's fine that it did not have a close bracket.
	}
	
	private static StringTree.Node parseAnonBranch(InputStream inp, String name) throws IOException{
		StringTree.Node currentNode = branch(name);
		int nextName = 0; //Name of the next node
		while( inp.available() > 0 ){
			switch( (char) inp.read() ){
				case ( '(' ): { //It's a leaf node, load the text and attach it to the branch
					currentNode.addChild( leaf( Integer.toString(nextName), getUntil( inp, ')' ) ) );
					nextName++;
					break;
				}
				case ( '{' ): { //This is the recursive bit-if it's another branch, recurse!
					currentNode.addChild( parseBranch( inp, Integer.toString(nextName) ) );
					nextName++;
					break;
				}
				case ( '<' ): { //It's a branch without named child nodes
					currentNode.addChild( parseAnonBranch(inp, Integer.toString(nextName) ) );
					nextName++;
					break;
				}
				case ( '/' ): {//Comment-just there for completeness
					Dbg.line("Comment:/" + getUntil(inp, '/') + "/"); //This is discarded.
					break;
				}
				case ( '#' ): {//Line comment, in case you really need one.
					Dbg.line("Comment:#" + getUntil(inp, '\n'));
					break;
				}
				case ( '>' ): { //End of branch
					return currentNode;
				}
			}
		} //This should never be passed, ever!
		throw new RstSyntaxError("Ran out of text at end of anon branch\n");
	}
			 
		

	private static StringTree.Node branch(String name){
		Node toSender = new Node(name);
		toSender.childNodes = new HashMap<String, Node>();
		return toSender;
	}
	
	private static StringTree.Node leaf(String name, String value){
		Node toSender = new Node(name);
		toSender.value = value;
		return toSender;
	}
	
	/**
	 * Get the names of the child nodes of a given Branch.
	 */

	public Set<String> childSet(String... name){
		return getNode(name).childNodes.keySet();
	}

	private Node getNode(String... name){
		return descend(root, name);
	}
	
	private static class Node{
		String name;
		String value;
		HashMap<String,Node> childNodes;
		
		private HashMap<String, Node> gcn(){
			if(childNodes == null){
				throw new ParserError(name + " is a leaf node, attempted to get children\n");
			}
			return childNodes;
		}
		
		private String gv(){
			if(value == null){
				throw new ParserError(name + "is a branch node, attempted to get value");
			}
			return value;
		}
		
		Node(String name){
			this.name = name;
		}
		
		String getValue(){
			return gv();
		}
		Node getChild(String name){
			//Debug stuff, comment out:
			if( ! gcn().containsKey(name) ){
				throw new ParserError(this.name + " does not contain node " + name);
			}
			//This probably introduces way too much parser inefficiency.
			//Also note that I didn't notice the name clash until I added this check.
			return childNodes.get(name);
		}
		
		
		void addChild(Node newChild){
			gcn().put(newChild.name, newChild);
		}
	}
	
	public static class ParserError extends RuntimeException{
		/**
		 * A tree parser tried to do something bad with a node.
		 */
		private static final long serialVersionUID = 1L;

		ParserError(String s){
			super(s);
		}
	}
	
	public static class RstSyntaxError extends RuntimeException{
		/**
		 * An error was encountered while making a tree.
		 */
		private static final long serialVersionUID = 1L;

		RstSyntaxError(String s){
			super(s);
		}
	}
}
