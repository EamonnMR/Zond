package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class BasicGun {

	//vars
	private String uiName;	//Definitely don't know why this wasnt done sooner
	private String toolTip;						 //UI tooltip string
	private double timer;
	private int weight;
	private int cost;	
	private Image img;	//img is for in-game/on-ship, shot is for makeShot
	private Image wireframe;
	private double x,y, mx, my;
	private double speedX, speedY;
	private double angle;
	private int coolDown;
	private String name;
	private BasicShot proj;
	private Sound fireSnd;
	private Image mzlPrtcl;

	//constructor
	public BasicGun(){}
	
	public void tickTimer(int delta){ //Count down the gun timer
		if (timer != 0){
			timer -= delta;
			if (timer < 0) {
				timer = 0;
			}
		}
	}
	
	//methods

	public boolean canIshoot() {
		if(timer == 0){
			timer = getRof();
			return true;
		}
		return false;
	}
	/**
	 * creates a shot at the gunpoint and moves in direction of gun at shot time
	 * @return BasicShot
	 */
	public BasicShot makeShot(Float vol){
			BasicShot shot = new BasicShot();
			shot.setImg(proj.getImg().copy());
			shot.getImg().setRotation(img.getRotation());  //+0.001f
			
			//Setting up the speed is complicated, yes, but now it's also sane
			float spd = proj.getSpeed(); //The muzzle velocity of the projectile...
			//speedx and speedy are the current speed of the gun and therefore bullet
			//it uses the cos / sin algorithm plus the initial speeds to get the projectile's speed.
			shot.setSpeeds( (spd * Math.cos(Math.toRadians(angle)) ) + speedX, (spd * Math.sin(Math.toRadians(angle)) ) + speedY);
			
			shot.setInterval(proj.getInterval());
			shot.setDamage(proj.getDamage());
			Circle temp = new Circle(0,0,0);
			temp.setRadius( proj.getCollider().getBoundingCircleRadius());
			shot.setCollider(temp);
			shot.setX(getX());
			shot.setY(getY()-(getImg().getTextureHeight()));
			shot.setSnd(proj.getSnd());
//			getFireSnd().playAt(0.6f, vol, (float)shot.getX(), (float)shot.getY(), 0.0f);
			getFireSnd().playAt(1.0f, vol, (float)shot.getX(), (float)shot.getY(), 0.0f);
			return shot;
	}
	
	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setSpeed(double speedX, double speedY){
		this.speedX = speedX;
		this.speedY = speedY;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sound getFireSnd() {
		return fireSnd;
	}

	public void setFireSnd(Sound fireSnd) {
		this.fireSnd = fireSnd;
	}

	public String getUiName() {
		return uiName;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public Image getWireframe() {
		return wireframe;
	}

	public void setWireframe(Image wireframe) {
		this.wireframe = wireframe;
	}

	public Image getMzlImg() {
		return mzlPrtcl;
	}

	public void setMzlImg(Image mzlPrtcl) {
		this.mzlPrtcl = mzlPrtcl;
	}

	public double getMx() {
		return mx;
	}

	public void setMx(double mx) {
		this.mx = mx;
	}

	public double getMy() {
		return my;
	}

	public void setMy(double my) {
		this.my = my;
	}
}
