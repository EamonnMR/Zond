package ents;
//Basic Entity for objects that move with inertia
//Entities inheriting from it should call super within update methods.
public abstract class PhysEnt extends BaseEnt {

	private double speedX, speedY;
	private double mass;
	
	public void setMass(double mass){
		this.mass = mass;
	}
	
	@Override
	public void update(int delta) {
		addX(speedX);
		addY(speedY);
	}
	
	public void pushX(double dSpeedX){
		speedX += dSpeedX;
	}
	
	public void pushY(double dSpeedY){
		speedY += dSpeedY;
	}
	
	public void push(double dSpeedX, double dSpeedY){
		speedX += dSpeedX; speedY += dSpeedY;
	}
	
	public void pushDir(double dir, double speed){
		speedX += speed * Math.cos(dir);
		speedY += speed * Math.sin(dir);
	}
}
