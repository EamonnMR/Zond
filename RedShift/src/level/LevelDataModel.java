package level;

import java.awt.Point;
import java.util.HashMap;

import level.actions.BasicAction;
import level.triggers.BasicTrigger;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * vaunted version 2 of the level data/controller system. The actual processing logic has been moved to a class called LevelHandler.
 * that bad boy does the thinking. LevelDataModel now is a pure data class. 
 * @author Roohr
 *
 */
public class LevelDataModel {

	private String name;
	private HashMap<String, BasicTrigger> triggerMap;
	private HashMap<String, BasicAction> actionMap;
	private HashMap<String, NavPoint> navMap;
	private Shape activeArea, warnArea;
	private boolean needUpdate;
	private Point spawn;
	
	public LevelDataModel(String name){
		this.name = name;
		this.triggerMap = new HashMap<String, BasicTrigger>();
		this.actionMap = new HashMap<String, BasicAction>();
		this.navMap = new HashMap<String, NavPoint>();
		this.needUpdate = false;
	}

	public String getName() {
		return name;
	}

	public void addTrigger(BasicTrigger trig){
		triggerMap.put(trig.getName(), trig);
	}

	public void addAction(BasicAction act){
		actionMap.put(act.getName(), act);
	}
	
	public void addNavPoint(NavPoint p){
		this.navMap.put(p.getName(), p);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, BasicTrigger> getTriggerMap() {
		return triggerMap;
	}

	public void setTriggerMap(HashMap<String, BasicTrigger> triggerMap) {
		this.triggerMap = triggerMap;
	}

	public HashMap<String, BasicAction> getActionMap() {
		return actionMap;
	}

	public void setActionMap(HashMap<String, BasicAction> actionMap) {
		this.actionMap = actionMap;
	}

	public HashMap<String, NavPoint> getNavMap() {
		return navMap;
	}

	public void setNavMap(HashMap<String, NavPoint> navMap) {
		this.navMap = navMap;
	}

	public Shape getActiveArea() {
		return activeArea;
	}

	public void setActiveArea(Shape activeArea) {
		this.activeArea = activeArea;
	}

	public Shape getWarnArea() {
		return warnArea;
	}

	public void setWarnArea(Shape warnArea) {
		this.warnArea = warnArea;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	
	public Point getSpawn() {
		return spawn;
	}

	public void setSpawn(Point spawn) {
		this.spawn = spawn;
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
	
	/**
	 * set the active and warning areas of the level
	 * @param activeArea
	 * @param warningArea
	 */
	public void setBounds(Shape a, Shape b){
		this.activeArea = a;
		this.warnArea = b;
	}
	
}
