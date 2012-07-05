package ents;
/**
 * @author Eamonn
 * Member object that handles physics within an entity or any other object.
 * Users should implement PhysMod.Target.
 */
public class PhysMod{

	public PhysMod(Target owner, double speedX, double speedY, double mass, double maxSpeed) {
		this.owner = owner;
		this.speedX = speedX;
		this.speedY = speedY;
		this.mass = mass;
		this.maxSpeed = maxSpeed;
	}

	private Target owner;
	private double speedX, speedY;
	private double mass;
	private double maxSpeed;
	
	
	/**
	 * Mass currently does nothing-we might remove this later, we'll see.
	 * @param mass
	 */
	
	public void update(int delta) {
		owner.addX(speedX * delta);
		owner.addY(speedY * delta);
	}
	
	/**
	 * Ensure that the object's speed does not exceed the maximum allowed
	 */
	public void limitSpeed(){
		if (getSpeed() > maxSpeed){
			double dir = getDir(); //Stored because it would change after the next line
			speedX = maxSpeed * Math.sin(dir);
			speedY = maxSpeed * Math.cos(dir);
		}
	}
	
	/**
	 * Get the speed at which the object is moving.
	 */
	public double getSpeed(){
		return Math.sqrt((speedX * speedX) + (speedY * speedY)); //Pythagorean theorem
	}
	
	/**
	 * Get the direction that the object is moving in.
	 */
	public double getDir(){
		return Math.atan2(speedY, speedX);
	}
	
	//The "push" methods add an exact number of newton/kilograms.
	/**
	 * Add an exact amount of velocity on the X line.
	 */
	public void pushX(double dSpeedX){
		speedX += dSpeedX;
		limitSpeed();
	}
	/**
	 * Add an exact amount of velocity on the Y line.
	 */
	public void pushY(double dSpeedY){
		speedY += dSpeedY;
		limitSpeed();
	}
	/**
	 * Add an exact amount of velocity.
	 */
	public void push(double dSpeedX, double dSpeedY){
		speedX += dSpeedX; speedY += dSpeedY;
		limitSpeed();
	}
	/**
	 * Add an exact amount of speed in a direction.
	 */
	public void pushDir(double dir, double speed){
		speedX += speed * Math.cos(dir);
		speedY += speed * Math.sin(dir);
		limitSpeed();
	}
	
	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	//Owner should inherit from this.
	public interface Target{
		public void addX(double ddx);
		public void addY(double ddy);
	}
}
