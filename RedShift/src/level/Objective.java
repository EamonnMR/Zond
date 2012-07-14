package level;

/**
 * Basic Objective class that is held in the level data, it sorta has to be separate
 * from the Action and Trigger classes...sry EMR :| it's kinda necessary for now
 * @author proohr
 * @version 1.0
 */
public class Objective {

	//vars
	private String name, blurb, full;
	private boolean complete;
	
	
	public Objective(){
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBlurb() {
		return blurb;
	}


	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}


	public String getFullDesc() {
		return full;
	}


	public void setFullDesc(String full) {
		this.full = full;
	}


	public boolean getComplete() {
		return complete;
	}


	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
