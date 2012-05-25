package ents;
/**
 * @author Eamonn
 * Member object that handles physics within an entity or any other object.
 * Users should implement PhysMod.Target.
 */
public class PhysMod{

	public PhysMod(Target owner, double speedX, double speedY, double mass) {
		this.owner = owner;
		this.speedX = speedX;
		this.speedY = speedY;
		this.mass = mass;
	}

	private Target owner;
	private double speedX, speedY;
	private double mass;
	
	/**
	 * Mass currently does nothing-we might remove this later, we'll see.
	 * @param mass
	 */
	
	public void update(int delta) {
		owner.addX(speedX * delta);
		owner.addY(speedY * delta);
	}
	

	//The "push" methods add an exact number of newton/kilograms.
	
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
	
	public interface Target{
		public void addX(double ddx);
		public void addY(double ddy);
	}
}
