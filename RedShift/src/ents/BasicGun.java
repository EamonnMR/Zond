package ents;

import org.newdawn.slick.Image;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class BasicGun {

	//vars
	private int ID;				//for when an item system is in play
	private int weight;
	private int cost;	
	private Image img;	//img is for in-game/on-ship, shot is for makeShot
	private double x,y;
	private double angle;
	private int coolDown;
	private BasicShot proj;

	//constructor
	public BasicGun(){}
	
	//methods
	/**
	 * creates a shot at the gunpoint and moves in direction of gun at shot time
	 * @return BasicShot
	 */
	public BasicShot makeShot(){
			BasicShot shot = new BasicShot();
			shot.setImg(proj.getImg().copy());
			shot.getImg().setRotation(img.getRotation());
			shot.setSpeed(proj.getSpeed());
			shot.setInterval(proj.getInterval());
			shot.setDamage(proj.getDamage());
			shot.setCollider(proj.getCollider());
			shot.setX(getX());
			shot.setY(getY());
			return shot;
	}
	
	public int getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	public BasicShot getProj() {
		return proj;
	}

	public void setProj(BasicShot proj) {
		this.proj = proj;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
		
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getRof() {
		return coolDown;
	}

	public void setRof(int rof) {
		this.coolDown = rof;
	}

}
