package tests;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FxLdrTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		core.StringTree s = null;
		//Why is it not counted as initialized without that line?
		
		try {
			s = core.GameDatabase.loadRst("assets/text/test1.rst");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String i : s.childSet()){
			System.out.print(core.GameDatabase.getEffect(s.getSubTree(i)));
		}
	}

}
