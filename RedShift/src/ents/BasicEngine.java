package ents;

import org.newdawn.slick.Image;

/**
 * The bare bones Engine class, this is necessary for any ship to move.
 * @author proohr
 * @version 1.0
 */
public class BasicEngine {

	//vars
	private int weight;						//in-game weight of engine
	private int cost;						//in-game cost of engine
	private Image inGameImg, icon;			//images
	private float thrustX, thrustY, turnrate,strafeRate;
	private double thrustPtX, thrustPtY;	//where to show particles

	//constructors
	public BasicEngine(){}
	
	//methods
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
	public Image getInGameImg() {
		return inGameImg;
	}
	public void setInGameImg(Image inGameImg) {
		this.inGameImg = inGameImg;
	}
	public Image getInGuiImg() {
		return icon;
	}
	public void setInGuiImg(Image inGuiImg) {
		this.icon = inGuiImg;
	}
	public float getThrustX() {
		return thrustX;
	}
	public void setThrustX(float thrustX) {
		this.thrustX = thrustX;
	}
	public float getThrustY() {
		return thrustY;
	}
	public void setThrustY(float thrustY) {
		this.thrustY = thrustY;
	}
	public float getTurnrate() {
		return turnrate;
	}
	public void setTurnrate(float turnrate) {
		this.turnrate = turnrate;
	}
	public double getThrustPtX() {
		return thrustPtX;
	}
	public void setThrustPtX(double thrustPtX) {
		this.thrustPtX = thrustPtX;
	}
	public double getThrustPtY() {
		return thrustPtY;
	}
	public void setThrustPtY(double thrustPtY) {
		this.thrustPtY = thrustPtY;
	}
	public float getStrafeRate() {
		return strafeRate;
	}
	public void setStrafeRate(float strafeRate) {
		this.strafeRate = strafeRate;
	}

	
}
