package com.foo.redshift.ents;

import org.newdawn.slick.Image;

public class BasicEngine {

	//vars
	private int ID;
	private int weight;
	private int cost;
	private int PID;
	private Image inGameImg, inGuiImg, sparkleImg;
	private float thrustX, thrustY, turnrate, unThrustX, unThrustY;
	private double thrustPtX, thrustPtY;

	//const
	public BasicEngine(int i){
		ID = i;
	}
	
	public BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks, float turn){
		ID = i;
		weight = wt;
		cost = cst;
		PID = pid;
		inGameImg = ingame;
		inGuiImg = gui;
		sparkleImg = sparks;
		turnrate = turn;
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
		return inGuiImg;
	}
	public void setInGuiImg(Image inGuiImg) {
		this.inGuiImg = inGuiImg;
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
