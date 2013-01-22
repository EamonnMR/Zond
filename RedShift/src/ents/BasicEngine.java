package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

/**
 * The bare bones Engine class, this is necessary for any ship to move.
 * @author proohr
 * @version 1.0
 */
public class BasicEngine {

	//vars
	private String uiName;
	private String toolTip;						 //UI tooltip string
	private int weight;						//in-game weight of engine
	private int cost;						//in-game cost of engine
	private Image inGameImg, wireFrame;			//images
	private float forwardThrust, reverseThrust, turnRate,strafeRate;
	private double thrustPtX, thrustPtY;	//where to show particles
	private String name;
	private Sound primeThrust;
	private Sound sideThrust;
	private ConfigurableEmitter thrstPrtcl;
	
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
	public Image getWireFrame() {
		return wireFrame;
	}
	public void setWireFrame(Image inGuiImg) {
		this.wireFrame = inGuiImg;
	}
	public float getThrustX() {
		return forwardThrust;
	}
	public void setThrustX(float thrustX) {
		this.forwardThrust = thrustX;
	}
	public float getThrustY() {
		return reverseThrust;
	}
	public void setThrustY(float thrustY) {
		this.reverseThrust = thrustY;
	}
	public float getTurnrate() {
		return turnRate;
	}
	public void setTurnrate(float turnrate) {
		this.turnRate = turnrate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrimeThrust(Sound primeThrust) {
		this.primeThrust = primeThrust;
	}

	public Sound getPrimeThrust() {
		return primeThrust;
	}

	public void setSideThrust(Sound sideThrust) {
		this.sideThrust = sideThrust;
	}

	public Sound getSideThrust() {
		return sideThrust;
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

	public ConfigurableEmitter getThrstPrtcl() {
		return thrstPrtcl;
	}

	public void setThrstPrtcl(ConfigurableEmitter thrstPrtcl) {
		this.thrstPrtcl = thrstPrtcl;
	}
}
