package level;

import org.newdawn.slick.geom.Shape;

/**
 * Defines a basic Trigger entity that is used by the 
 * BasicLevel class. 
 * @author proohr
 * @version 1.0
 */
public class BasicTrigger {

	//variables
	private Shape collider;
	private String triggerName;
	private String triggerTarget;
	private float triggerX;
	private float triggerY;
	private boolean isActive;
	private String triggerAction;

	//constructor
	public BasicTrigger(){}
	
	//methods
	public void update(int delta, boolean status, boolean triggered){
		isActive = status;	//only do stuff if still alive
		if(isActive){
			
			if(triggered){	//only do if triggered
				
			}
		}
	}
	
	public Shape getCollider() {
		return collider;
	}


	public void setCollider(Shape collider) {
		this.collider = collider;
	}


	public String getTriggerName() {
		return triggerName;
	}


	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}


	public String getTriggerTarget() {
		return triggerTarget;
	}


	public void setTriggerTarget(String triggerTarget) {
		this.triggerTarget = triggerTarget;
	}


	public float getTriggerX() {
		return triggerX;
	}


	public void setTriggerX(float triggerX) {
		this.triggerX = triggerX;
		collider.setX(triggerX);
	}


	public float getTriggerY() {
		return triggerY;
	}


	public void setTriggerY(float triggerY) {
		this.triggerY = triggerY;
		collider.setX(triggerY);
	}
	
	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getTriggerAction() {
		return triggerAction;
	}


	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}
	
}
