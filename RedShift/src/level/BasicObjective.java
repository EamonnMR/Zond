package level;

import level.actions.BasicAction;
import level.triggers.BasicTrigger;

/**
 * Basic Objective class that is held in the level data, it sorta has to be separate
 * from the Action and Trigger classes...sry EMR :| it's kinda necessary for now
 * @author proohr
 * @version 1.0
 */
public class BasicObjective {

	//vars
	private String name, blurb, full;	//blurb is for in-hud use || full is for during briefings
	private boolean complete, active;			//derp
	private BasicTrigger trigger;
	private BasicAction action;
	
	public BasicObjective(String name, BasicTrigger trig, BasicAction act){
		this.complete = false;
		this.name = name;
		this.action = act;
		this.trigger = trig;
	}

	public void setAction(BasicAction act){
		action = act;
	}
	
	public BasicAction getAction(){
		return action;
	}
	
	public void setTrigger(BasicTrigger trig){
		trigger = trig;
	}

	public BasicTrigger getTrigger(){
		return trigger;
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
	
	public void setActive(boolean act){
		this.active = act;
	}
	
	public boolean getActive(){
		return active;
	}
}
