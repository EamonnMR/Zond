package ents;

/**
 * the big bad, this class is for making any type of ship for RedShift and beyond!
 * @author Roohr
 * @version 1.0
 */
public class BasicShip extends BaseEnt implements PhysMod.Target
{

	private int totalWeight;		             //maximum equipment
	private double points;				         //points to award to killer
	private double health;			             //base health of the ship
	private BasicArmor armor;		             //not implemented
	private BasicGun gun;			             //the current weapon on the ship
	private double gunOffsetX, gunOffsetY;       //the offset for the weapon - does not handle more tha one weapon
	private BasicEngine engine;			         //the engine mounted to the ship
	private double engineOffsetX, engineOffsetY; //the offset for the engine image
	private double engineOffsetDistance;		 //the offset for where to draw the engine
	private double gunOffsetDistance;			 //the offset for where to draw the weapon
	private PhysMod physAnchor;                  //Physics Module to keep it flying with physics.

	
	
	//constructor
	public BasicShip(){
		physAnchor = new PhysMod(this, 0, 0, 0); //Other classes such as shot might wanna give it an initial velocity...
	}	                                         //But not the ship!  At least not yet.

	//Just in case we decide to remove addX and addY from basic Entity - EMR
	@Override
	public void addX(double dx) {
		// TODO Auto-generated method stub
		super.addX(dx);
	}

	@Override
	public void addY(double dy) {
		// TODO Auto-generated method stub
		super.addY(dy);
	}

	//methods
	public void ini(double x, double y, float rotation){
		setX(x);
		setY(y);
		gun.setX(x);
		gun.setY(y);
		getImg().setRotation(rotation);
		//theta = Math.PI/2;
	}
	
	public void render(){
		//draw the engine
		if(gun!=null){
			gun.getImg().drawCentered((float)getWepOffX(),(float) getWepOffY());
		}
		//draw the gun
		if(engine!=null){
			engine.getInGameImg().drawCentered((float)getEngOffX(),(float)getEngOffY());
		}
		//draw the ship
		getImg().setCenterOfRotation((getImg().getWidth()/2),(getImg().getHeight()/2));
		getImg().drawCentered((float)getX(), (float)getY());	
	}

	public void update(int delta){
		//update ship
			physAnchor.update(delta);
			
			getCollider().setCenterX((float)getX());
			getCollider().setCenterY((float)getY());
			double angle = (Math.toRadians(getImg().getRotation()));
		
			//update gun
			double wx = getX();
			double wy = getY();
			
			wx += (gunOffsetDistance * Math.cos(angle)/*+theta*/);
			wy += (gunOffsetDistance * Math.sin(angle)/*+theta*/);
		
			setWepOffX(wx);		//where to draw gun on ship
			setWepOffY(wy);
			
			gun.setX(wx);	//pushes location down to basic shot
			gun.setY(wy);
			gun.setAngle(getImg().getRotation());
		//update engine			
			setEngOffX(getX() + engineOffsetDistance *Math.cos(angle));		//where to draw engine on ship
			setEngOffY(getY() + engineOffsetDistance *Math.sin(angle));
	}
	
	/**
	 * rotate the ship to the left
	 * @param delta
	 */
	public void rotateLeft(int delta){
    	float rot = (-getEngine().getTurnrate())*delta;
		getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	/**
	 * rotate the ship to the right
	 * @param delta
	 */
	public void rotateRight(int delta){
    	float rot = getEngine().getTurnrate()*delta;
    	getImg().rotate(rot);
    	getWeapon().getImg().rotate(rot);
    	getEngine().getInGameImg().rotate(rot);
	}
	/**
	 * move the ship forward
	 * @param delta
	 */
	public void moveForward(int delta){
		float hip = getEngine().getThrustX() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx += hip * Math.cos(Math.toRadians(rotation));
        dy += hip * Math.sin(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
        
	}
	/**
	 * move the ship backwards
	 * @param delta
	 */
	public void moveBackward(int delta){
		float hip = getEngine().getThrustY() * delta;
        double rotation = getImg().getRotation(); 
        double dx  = getX();
        double dy = getY();
        dx -= hip * Math.cos(Math.toRadians(rotation));
        dy -= hip * Math.sin(Math.toRadians(rotation));
        setX(dx);
        setY(dy);
	}
	
	/**
	 * this will strafe the ship left
	 * @param delta
	 */
	public void strafeLeft(int delta){
	   float hip = getEngine().getStrafeRate() * delta;
	   double rotation = getImg().getRotation(); 
	   setX( getX() + (hip * Math.cos(Math.toRadians(rotation - 90))));
	   setY( getY() + (hip * Math.sin(Math.toRadians(rotation - 90))));
	}
	/**
	 * this will strafe the ship right
	 * @param delta
	 */
	public void strafeRight(int delta){
      float hip = getEngine().getStrafeRate() * delta;
      double rotation = getImg().getRotation(); 
      setX( getX() + (hip * Math.cos(Math.toRadians(rotation+90))));
      setY( getY() + (hip * Math.sin(Math.toRadians(rotation+90))));
	}
	
	/**
	 * basic length calculating method
	 * @param x
	 * @param y
	 * @return
	 */
	public double length(double x, double y){ //How far this point is away from the origin. -EMR
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int wt) {
		totalWeight = wt;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public BasicArmor getArmor() {
		return armor;
	}

	public void setArmor(BasicArmor armor) {
		this.armor = armor;
	}

	public BasicGun getWeapon() {
		return gun;
	}

	public void setWeapon(BasicGun weapon) {
		this.gun = weapon;
	}

	public BasicEngine getEngine() {
		return engine;
	}

	public void setEngine(BasicEngine engine) {
		this.engine = engine;
	}
	
	public double getWepOffX() {
		return gunOffsetX;
	}

	public void setWepOffX(double wepOffX) {
		this.gunOffsetX = wepOffX;
	}

	public double getWepOffY() {
		return gunOffsetY;
	}

	public void setWepOffY(double wepOffY) {
		this.gunOffsetY = wepOffY;
	}

	public double getEngOffX() {
		return engineOffsetX;
	}

	public void setEngOffX(double engOffX) {
		this.engineOffsetX = engOffX;
	}

	public double getEngOffY() {
		return engineOffsetY;
	}

	public void setEngOffY(double engOffY) {
		this.engineOffsetY = engOffY;
	}
	public double getEngPtLength() {
		return engineOffsetDistance;
	}

	public void setEngPtLength(double engPtLength) {
		this.engineOffsetDistance = engPtLength;
	}

	public double getGunPtLength() {
		return gunOffsetDistance;
	}

	public void setGunPtLength(double gunPtLength) {
		this.gunOffsetDistance = gunPtLength;
	}


}
