package com.foo.redshift.ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public class BaseEnt {

	//vars
	private double x,y,vx,vy;
	private String id;
	private Image img;
	private float turnrate;
	private Shape collider;

	//const
	public BaseEnt(){
		
	}
	
	public BaseEnt(Image i, Shape col){
		collider = col;
		img = i;
	}
	
	//methods
	public void render(){
		getImg().drawCentered((float)getX(),(float) getY());
	}
	
	public void update(int delta){
		
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

	public void setY(double y) {
		this.y = y;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public float getTurnrate() {
		return turnrate;
	}
	
	public void setTurnrate(float turnrate) {
		this.turnrate = turnrate;
	}

	public Shape getCollider() {
		return collider;
	}

	public void setCollider(Shape collider) {
		this.collider = collider;
	}
}
