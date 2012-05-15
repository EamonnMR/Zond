package ents;

import org.newdawn.slick.Image;

/**
 * The bare bones Engine class, this is necessary for any ship to move.
 * @author proohr
 * @version 1.0
 */
public class BasicEngine {

	//vars
	private int ID;			//game Id
	private int weight;		//in-game weight of engine
	private int cost;		//in-game cost of engine
	private int PID;		//particle Id - not impl'ed yet
	private Image inGameImg, icon, sparkleImg;	//images
	private float thrustX, thrustY, turnrate, unThrustX, unThrustY;
	private double thrustPtX, thrustPtY;	//where to show particles

	//constructors
	public BasicEngine(){
	}
	
	/**
	 * now defunct constructor, replaced by constructor that uses thrustX, and th
	 * @deprecated
	 */
	public BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks, float turn){
		this.ID = i;
		this.weight = wt;
		this.cost = cst;
		this.PID = pid;
		this.inGameImg = ingame;
		this.icon = gui;
		this.sparkleImg = sparks;
		this.turnrate = turn;
	}
	
	/**
	 * 
	 * @param id
	 * @param weight
	 * @param cost
	 * @param particle_Id
	 * @param ingame_graphic
	 * @param gui_icon
	 * @param sparks_particle
	 * @param turn_rate
	 * @param forward_thrust
	 * @param reverse_thrust
	 */
	public BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks, float turn, float thrsX, float thrsY){
		this.ID = i;
		this.weight = wt;
		this.cost = cst;
		this.PID = pid;
		this.inGameImg = ingame;
		this.icon = gui;
		this.sparkleImg = sparks;
		this.turnrate = turn;
		this.thrustX = thrsX;
		this.thrustY = thrsY;
	}
	//methods
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
	public Image getSparkleImg() {
		return sparkleImg;
	}
	public void setSparkleImg(Image sparkleImg) {
		this.sparkleImg = sparkleImg;
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
	public float getUnThrustX() {
		return unThrustX;
	}
	public void setUnThrustX(float unThrustX) {
		this.unThrustX = unThrustX;
	}
	public float getUnThrustY() {
		return unThrustY;
	}
	public void setUnThrustY(float unThrustY) {
		this.unThrustY = unThrustY;
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

	
}
