package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

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
	private Image img;	//img is for in-game/on-ship, shot is for makeShot
	private double x,y;
	private double angle;
	private int coolDown;
	private BasicShot proj;

	//constructor
	public BasicGun(){

	}
	
	public BasicGun(int i, int wt, int cst, int pid, int dmg, Image im, int rate){
		ID = i;
		weight = wt;
		cost = cst;
		PID = pid;
		img = im;
		coolDown = rate;
	}
	//methods
	
	/**
	 * creates a shot at the gunpoint and moves in direction of gun at shot time
	 * @return BasicShot
	 */
	public BasicShot makeShot(){
			BasicShot pew = proj;
			Image sht = pew.getImg().copy();
			sht.rotate(img.getRotation());
			pew = new BasicShot(sht, 0.0f, 500, 5, this.getX(), this.getY(), new Circle((float)this.getX(),(float)this.getY(),2,8));
			return pew;

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

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
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
