package core;

public class Dbg {
	public static boolean WALL = true;
	public static void line(String s){
		if(WALL){
			System.out.println(s);
		}
	}
	
	public static void forceLine(String s){
		System.out.println(s);
	}
	
	public static void psa(String t, String[] s){
		if(WALL){
			System.out.println(t + "------------------");
			for(String i : s){
				System.out.println(i);
			}
		}
	}
}
