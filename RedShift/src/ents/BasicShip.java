package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * the big bad, this class is for making any type of ship for RedShift and beyond!
 * @author Roohr
 * @version 1.0
 */
public class BasicShip extends BaseEnt{

	//vars
	private int totalWeight;
	private double points;				//points to award to killer
	private double health;				//base health of the ship
	private BasicArmor armor;			//not implemented
	private BasicGun weapon;			//the current weapon on the ship
	private double wepOffX, wepOffY;;	//the offset for the weapon - does not handle more tha one weapon
	private BasicEngine engine;			//the engine mounted to the ship
	private double engOffX, engOffY;	//the offset for the engine image
	private double engPtLength;			//the offset for where to draw the engine
	private double gunPtLength;			//the offset for where to draw the weapon
	private double theta;

	//constructor
	public BasicShip(){}

	//methods
	public void ini(double x, double y, float rotation){
		setX(x);
		setY(y);
		getImg().setRotation(rotation);
	}
	
	public void render(){
		//draw the engine
		if(weapon!=null){
			weapon.getImg().drawCentered((float)getWepOffX(),(float) getWepOffY());
		}
		//draw the gun
		if(engine!=null){
			engine.getInGameImg().drawCentered((float)getEngOffX(),(float)getEngOffY());
		}
		//draw the ship
		getImg().setCenterOfRotation((getImg().getWidth()/2),(getImg().getHeight()/2));
		getImg().drawCentered((float)getX(), (float)getY());	
	}

	public void update(double rot, double x, double y){
		//update ship
			setX(x);
			setY(y);
			getCollider().setCenterX((float)x);
			getCollider().setCenterY((float)y);
		
		//update gun
//			gunPtLength * 
			double angle = (Math.toRadians(getImg().getRotation()+theta));
			double wx = getWepOffX();
			double wy = getWepOffY();
			
			wx += gunPtLength * Math.sin(angle);
			wy -= gunPtLength * Math.cos(angle);
			
//			double gx = (gunPtLength*Math.sin(Math.toRadians(getImg().getRotation())+theta))+getX(); 
//			double gy = (gunPtLength*Math.cos(Math.toRadians(getImg().getRotation())+theta))+getY(); 
		
			setWepOffX(wx);
			setWepOffY(wy);
			
			weapon.setX(wx);
			weapon.setY(wy);
			weapon.setAngle(getImg().getRotation());
		//update engine
//			gx = (engPtLength * Math.sin(Math.toRadians(getImg().getRotation())+theta))+x; 
//			gy = (engPtLength * Math.sin(Math.toRadians(getImg().getRotation())+theta))+y; 
			
			double ex = getEngOffX();
			double ey = getEngOffY();
			
			ex += engPtLength *Math.sin(angle);
			ey -= engPtLength *Math.cos(angle);
			
			setEngOffX(ex);
			setEngOffY(ey);
	}
	
	/**
	 * rotate the ship to the left
	 * @param delta
	 */
	public void rotateLeft(int delta){
    	float rot = (-getEngine().getTurnrate())*delta;
		getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	/**
	 * rotate the ship to the right
	 * @param delta
	 */
	public void rotateRight(int delta){
    	float rot = getEngine().getTurnrate()*delta;
    	getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	/**
	 * move the ship forward
	 * @param delta
	 */
	public void moveForward(int delta){
		float hip = getEngine().getThrustX() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx += hip * Math.sin(Math.toRadians(rotation));
        dy -= hip * Math.cos(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
	}
	/**
	 * move the ship backwards
	 * @param delta
	 */
	public void moveBackward(int delta){
		float hip = getEngine().getThrustY() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx -= hip * Math.sin(Math.toRadians(rotation));
        dy += hip * Math.cos(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
	}
	
	/**
	 * although not fully working, this will strafe the ship left
	 * @param delta
	 */
	public void strafeLeft(int delta){
      float hip = getEngine().getTurnrate() * delta;
      double rotation = getImg().getRotation()+(Math.PI/2); 
      double dx  = getX();
      double dy = getY();
      dx -= hip * Math.sin(Math.toRadians(rotation));
      dy -= hip * Math.cos(Math.toRadians(rotation));
      setX(dx);
      setY(dy);
	
	}
	/**
	 * although not fully working, this will strafe the ship right
	 * @param delta
	 */
	public void strafeRight(int delta){
      float hip = getEngine().getTurnrate() * delta;
      double rotation = getImg().getRotation()-(Math.PI/2); 
      double dx  = getX();
      double dy = getY();
      dx += hip * Math.sin(Math.toRadians(rotation));
      dy += hip * Math.cos(Math.toRadians(rotation));
      setX(dx);
      setY(dy);
	}
	
	/**
	 * basic length calculating method
	 * @param x
	 * @param y
	 * @return
	 */
	public double length(double x, double y){ //How far this point is away from the origin. -EMR
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int wt) {
		totalWeight = wt;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public BasicArmor getArmor() {
		return armor;
	}

	public void setArmor(BasicArmor armor) {
		this.armor = armor;
	}

	public BasicGun getWeapon() {
		return weapon;
	}

	public void setWeapon(BasicGun weapon) {
		this.weapon = weapon;
	}

	public BasicEngine getEngine() {
		return engine;
	}

	public void setEngine(BasicEngine engine) {
		this.engine = engine;
	}
	
	public double getWepOffX() {
		return wepOffX;
	}

	public void setWepOffX(double wepOffX) {
		this.wepOffX = wepOffX;
	}

	public double getWepOffY() {
		return wepOffY;
	}

	public void setWepOffY(double wepOffY) {
		this.wepOffY = wepOffY;
	}

	public double getEngOffX() {
		return engOffX;
	}

	public void setEngOffX(double engOffX) {
		this.engOffX = engOffX;
	}

	public double getEngOffY() {
		return engOffY;
	}

	public void setEngOffY(double engOffY) {
		this.engOffY = engOffY;
	}
	public double getEngPtLength() {
		return engPtLength;
	}

	public void setEngPtLength(double engPtLength) {
		this.engPtLength = engPtLength;
	}

	public double getGunPtLength() {
		return gunPtLength;
	}

	public void setGunPtLength(double gunPtLength) {
		this.gunPtLength = gunPtLength;
	}


}
