package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import level.BasicObjective;
import level.LevelDataModel;
import level.actions.BasicAction;
import level.triggers.BasicTrigger;
import level.triggers.CountTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * This is a port of the Basiclevel render and update mehods. they've been removed and place here
 * as a means to compartmentalize the logic processing compared to BasicLevel which should only store data.
 * Importantly this is only really called by the CGS
 * 
 *--- -----------------------Old descript ------------------------------------------------
 * the level class is primarily a data class that holds a collection of triggers
 * and actions. It handles the calling of triggers to actions, and handling updates
 * on specific actions. Updates on the level are run per-frame inside the main 
 * update loop in ClientGameplayState; aka Level's update DOES NOT take control of
 * the update loop, rather it executes in sequence with everything else in the primary
 * update.
 * 
 * right now, we can pass whole HashMaps of triggers and actions to the level for easy setup
 * I still need to make a 'validate' method that checks to make sure triggers hit the right actions
 * 
 * in the future, I plan on us being able to pass trigger and action queues as a form of simple
 * script injection, but it may not come to that; so for now, one cannot pass queues to Level.
 * but the queues are polled in clientgameplaystate
 * 
 * @author Roohr
 * 

 */
public class LevelHandler {

	private LevelDataModel level;
	private Queue<BasicTrigger> executeTriggers;
	private Queue<BasicAction> executeActions;
	
	public LevelHandler(LevelDataModel ldm){
		this.level = ldm;
		executeTriggers = new LinkedList<BasicTrigger>();
		executeActions = new LinkedList<BasicAction>();
	}
	/**
	 * the meat and potatoes of the level class
	 * this method goes through the triggers to find which are triggered, and puts those in a queue
	 * going in FIFO, it activates the corresponding actions and places them in a queue (to maintain FIFO)
	 * then runs the proper methods for the actions, either action.ini() or action.update()
	 * PS: this method is For-Loop city :P
	 * @param delta
	 */
	public void update(int delta, ClientGameplayState cgs){
		//checks for any remaining trigs or actions
		int itemCnt = 0;
		//find which triggers are active
		for (BasicTrigger trig : level.getTriggerMap().values()) {
			//these trigger-specific checks are necessary :( however tedious
			if (trig.isTrigged()) {
				if(trig.getClass().equals(CountTrigger.class)){
					CountTrigger counter = (CountTrigger)trig;
					counter.add();
					System.out.println(counter.getName() + " at "+counter.getCount());
					counter.trigger(false);
					if(counter.getCount()>=counter.getTotal()){
						executeTriggers.add(trig);
					}
				}else{
					// put these triggers into a queue, the whole trigger rather
					// than just the target
					// why? so we can remove the triggers that have been fired
					executeTriggers.add(trig);
					System.out.println("Trigger: " + trig.getName() +" added to queue");
				}
			}
		}
		

		
		//8/10/2012 new functionality
			//scan all triggers, see if they have a target trigger or target action to fire
		for(BasicTrigger trig : executeTriggers){
			trig.go(cgs);
			if(trig.getTargetName()!=null){
				for(String trigName : level.getTriggerMap().keySet()){
					if(trigName.equals(trig.getTargetName())){
						level.getTrigger(trigName).trigger(true);
						level.setNeedUpdate(true);
					}
				}
				
				for(String actName : level.getActionMap().keySet()){
					if(actName.equals(trig.getTargetName())){
						executeActions.add(level.getAction(actName));
					}
				}
				itemCnt++;
			}
			System.out.println("Trigger: "+ trig.getName()+" has been executed");	
		}
		
		//fire the actions
		activateActions(cgs, delta);
		
		cleanTriggers();
		cleanActions();
		
		checkObjectives();
		
		if(itemCnt ==0){
			level.setNeedUpdate(false);
		}	
	}
	

	private void activateActions(ClientGameplayState cgs, int delta) {
		for(BasicAction act : executeActions){
			
			//if the action has not started; start it, and flag as started
			if(act.isIni()){
				act.ini(cgs);
				System.out.println("Action: "+act.getName()+" is initialized");
			//if the action has started, run its update for this frame	
			}else if(act.isUpdate()){
				//note: to end the 'update' state, simply set isUpdate=false, isDone=true inside action.update()
				act.update(delta, cgs);
				System.out.println("Action: "+act.getName()+" is updating");
	
			//if the action has finished, remove the action off the queue
			}
			if (act.isDone()){
				if(act.getTriggerTargetName()!=null){
					for(BasicTrigger trg : level.getTriggerMap().values()){
						if(act.getTriggerTargetName().equals(trg.getName())){
							trg.trigger(true);
							level.setNeedUpdate(true);
						}
					}
				}
			}
		}
		
	}
	public void render(Graphics gfx, int cx, int cy){
		gfx.setColor(Color.yellow);
		gfx.draw(offsetShape(level.getActiveArea(), (int) cx, (int) cy));
		gfx.setColor(Color.red);
		gfx.draw(offsetShape(level.getWarnArea(), (int) cx, (int) cy));
		gfx.setColor(Color.green);
		
		for(BasicAction acts : getExecuteActions()){
			if(acts.isUpdate()){
				acts.render(gfx);
			}
		}
	}
	
	private void checkObjectives() {

	}
	
	//garbage day! 
	private void cleanTriggers(){
		ArrayList<String> cleanTrigs = new ArrayList<String>();
		while(executeTriggers.iterator().hasNext()){
			cleanTrigs.add(executeTriggers.element().getName());
			executeTriggers.remove();
		}
		
		for(String str : cleanTrigs){
			System.out.println("Trigger: "+str+" has been removed");
			level.getTriggerMap().remove(str);
		}

	}
	
	private void cleanActions(){
		ArrayList<String> cleanAct = new ArrayList<String>();
		for(BasicAction act : level.getActionMap().values()){
			if(act.isDone()){
				System.out.println("Action: "+ act.getName()+" has been removed");
				cleanAct.add(act.getName());
			}
		}
		
		for(String str : cleanAct){
			level.getActionMap().remove(str);
		}
	}
	
	public int checkBounds(Shape s){
		if(level.getWarnArea().intersects(s)){
			if(level.getActiveArea().intersects(s)){
				return 1;
			}
			return 0;
		}else{
			return -1;
		}
	}
	
	public static Shape offsetShape(Shape s, int dx, int dy){
	    float  x = s.getCenterX();
	    float y = s.getCenterY();
	    Shape toSender;
	    toSender = s.transform(new Transform() );
	    toSender.setCenterX( x + dx);
	    toSender.setCenterY( y + dy);
	    return toSender;
	}
	public LevelDataModel getLevel() {
		return level;
	}
	public void setLevel(LevelDataModel level) {
		this.level = level;
	}
	public Queue<BasicTrigger> getExecuteTriggers() {
		return executeTriggers;
	}
	public void setExecuteTriggers(Queue<BasicTrigger> executeTriggers) {
		this.executeTriggers = executeTriggers;
	}
	public Queue<BasicAction> getExecuteActions() {
		return executeActions;
	}
	public void setExecuteActions(Queue<BasicAction> executeActions) {
		this.executeActions = executeActions;
	}
	
	
}
