package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class BasicShip extends BaseEnt{

	//vars
	private int ID;
	private double points;
	private double health;
	private BasicArmor armor;	//not implemented
	private BasicGun weapon;
	private double wepOffX, wepOffY;;
	private BasicEngine engine;
	private double engOffX, engOffY;
	private double engPtLength;
	private double gunPtLength;
	private double theta;

	//const
	public BasicShip(int i, Image img){
		ID = i;
		setImg(img);
		gunPtLength = -30;
		engPtLength = 24;
		theta = Math.PI/2;
	}
	
	//FULL BUILD
	public BasicShip(int i, Image im, double hp, double pts, BasicArmor arm, BasicEngine eng, BasicGun gun, double gunPt, double engPt, Circle col){
		ID = i; 
		setImg(im);
		health = hp;
		points = pts;
		armor = arm;
		engine = eng;
		weapon = gun;
		gunPtLength = gunPt;
		engPtLength = engPt;
		setCollider(col);
		theta = Math.PI/2;
	}

	//methods
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
		double gx = (gunPtLength * Math.cos(Math.toRadians(getImg().getRotation())+theta))+x; 
		double gy = (gunPtLength * Math.sin(Math.toRadians(getImg().getRotation())+theta))+y; 
		
		setWepOffX(gx);
		setWepOffY(gy);
		weapon.setX(gx);
		weapon.setY(gy);
		weapon.setAngle(getImg().getRotation());
		//update engine
		gx = (engPtLength * Math.cos(Math.toRadians(getImg().getRotation())+theta))+x; 
		gy = (engPtLength * Math.sin(Math.toRadians(getImg().getRotation())+theta))+y; 
		
		setEngOffX(gx);
		setEngOffY(gy);
	}
	
	public void rotateLeft(int delta){
    	float rot = (-getEngine().getTurnrate())*delta;
		getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	public void rotateRight(int delta){
    	float rot = getEngine().getTurnrate()*delta;
    	getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	public void moveForward(int delta){
		float hip = getEngine().getTurnrate() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx += hip * Math.sin(Math.toRadians(rotation));
        dy -= hip * Math.cos(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
	}
	public void moveBackward(int delta){
        float hip = getEngine().getTurnrate() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx -= hip * Math.sin(Math.toRadians(rotation));
        dy += hip * Math.cos(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
	}
	
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
	public double length(double x, double y){ //How far this point is away from the origin. -EMR
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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
}
