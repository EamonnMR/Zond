package core;
import java.util.HashMap;
/**
 * Very simple tree implementation to represent trees of strings.
 * "Leaf nodes" are terminal nodes with no children and a string "value" associated with them
 * "Branch nodes" are nodes with 0 or more children (so they can technically be leaf nodes, but they're different here BECAUSE).
 * More features (remembering last nodes, etc) will be added later (when we actually need the class).
 * @author Eamonn
 */
public class StringTree {

	Node root;
	
	public StringTree (){
		root = branch("root");
	}
	
	/**
	 * Get the value owned by a particular leaf.
	 * @param name Names of nodes after root to get the leaf you want
	 */
	public String getValue(String... name){
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
	 * /
	public static StringTree fromStream(InputStream inp){
		toSender = StringTree();
		toSender.root = parseBranch(inp, "root");
	}

	
	//Get a string from an inputStream ending in a specific charicter
	private static String getUntil(InputStream inp, char ender){
		toSender = new String   //Algol had the idea of declaring return vars at the beginning of a function...
		while( inp.hasNext() ){ //I think they had the right idea.
			currentChar = (char)inp.next()
			if (currentChar == ender){
				return toSender;
			} else {
				toSender.ADDCHAR( currentChar );
			}
		}
		//If it does not reach char 'ender' it probably fowls the entire input read,
		//so print a warning.
		System.out.println( "BAD INPUT: MISSING ".ADDCHAR( ender ) );
		return toSender;
	}

	private static StringTree.Node parseBranch(InputStream inp, String name){
		currentNode = branch(name);
		nextName = String(); //Name of the next node
		while( inp.hasNext() ){
			switch( (char) inp.next() ){
				case ( '[' ){ //It's a label applying to the next node.
					nextName = getUntil(inp, ']');
				}
				case ( '(' ){ //It's a leaf node, load the text and attach it to the branch
					currentNode.addChild( leaf( nextName, getUntil( inp, ')' ) ) );
				}
				case ( '{' ){ //This is the recursive bit-if it's another branch, recurse!
					currentNode.addChild( parseBranch( inp, nextName) )
				}
				case ( '}' ){ //End of branch
					return currentNode
				}
			}
		} //This should never be passed except if it's the root node, which is in fact terminated by
		if (name != root){ //The end of the file.
			System.out.println( "BAD INPUT: MISSING } ");
			 
		

	private StringTree.Node branch(String name){
		Node toSender = new Node(name);
		toSender.childNodes = new HashMap<String, Node>();
		return toSender;
	}
	
	private StringTree.Node leaf(String name, String value){
		Node toSender = new Node(name);
		toSender.value = value;
		return toSender;
	}
	
	/**
	 * Get the names of the child nodes of a given Branch.
	 */

	public ____ listChildren(String... name){
		Node parent = root;
		for (String n : name){
			parent = parent.getChild(n);
		}
		return parent.children.keys();
	}

	private Node getNode(String... name){
		Node toSender = root;
		for (String n : name){
			toSender = toSender.getChild(n);
		}
		return toSender;
	}
	
	private static class Node{
		String name;
		String value;
		HashMap<String,Node> childNodes;
		
		Node(String name){
			this.name = name;
		}
		
		String getValue(){
			return value;
		}
		Node getChild(String name){
			return childNodes.get(name);
		}
		
		void addChild(Node newChild){
			childNodes.put(newChild.name, newChild);
		}
	}
}
