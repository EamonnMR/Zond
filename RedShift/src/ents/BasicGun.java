package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

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
	private int PID;			//projectile id- possibly deprecated
	private int damage;			//record keeping - possibly deprecated
	private Image img, shot;	//img is for in-game/on-ship, shot is for makeShot
	private double x,y;
	private double angle;
	private int coolDown;

	//constructor
	public BasicGun(int i){
		ID = i;
	}
	
	public BasicGun(int i, int wt, int cst, int pid, int dmg, Image im, Image shit, int rate){
		ID = i;
		weight = wt;
		cost = cst;
		PID = pid;
		damage = dmg;
		shot = shit;
		img = im;
		coolDown = rate;
	}
	//methods
	
	/**
	 * creates a shot at the gunpoint and moves in direction of gun at shot time
	 * @return BasicShot
	 */
	public BasicShot makeShot(){
			BasicShot pew;
			Image sht = shot.copy();
			sht.rotate(img.getRotation());
			pew = new BasicShot(sht, 0.4f, 500, 5, getX(), getY(), new Rectangle(0,0,6,8));
			return pew;

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

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
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
	
	public Image getShot() {
		return shot;
	}

	public void setShot(Image shot) {
		this.shot = shot;
	}

	public int getRof() {
		return coolDown;
	}

	public void setRof(int rof) {
		this.coolDown = rof;
	}

}
