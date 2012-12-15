package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import core.GameplayState;

/**
 * the big bad, this class is for making any type of ship for RedShift and beyond!
 * @author Roohr
 * @version 1.0
 */
public class BasicShip extends BaseEnt implements PhysMod.Target
{
	
	private static double SCLSPD = 0.0005;       //Global speed scaling-until we can get sane values for mass & engine impulse
	private static double HALFPI = 1.57079633;   //90 degrees in radians 
	
	private String name;						 //no idea why this wasn't done sooner
	private String toolTip;						 //UI tooltip string
	private Image wireframe;					 //UI wireframe image
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
	private Circle radar;					     //new functionality! radar! 
	private int faction;						 // which allegiance is this ship? 0 RUS 1 NAS
	private boolean radarState;					//i've got the derp, for some reason radar was in player client...<facepalm>
	//(to replace death trigs)
	
	
	//constructor
	public BasicShip(){
		physAnchor = new PhysMod(this, 0, 0, 0, 1000); //Other classes such as shot might wanna give it an initial velocity...
	}	                                         //But not the ship!  At least not yet.

	//Just in case we decide to remove addX and addY from basic Entity - EMR
	@Override
	public void addX(double dx) {
		super.addX(dx);
	}

	@Override
	public void addY(double dy) {
		super.addY(dy);
	}

	/**
	 * Gets the rotation of the ship in radians.
	 */
	public double getRot(){
		return Math.toRadians(getImg().getRotation());
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
	
	public void render(int xOffset, int yOffset){
		//draw the engine
		if(gun!=null){
			gun.getImg().drawCentered(xOffset + (float)getWepOffX(),yOffset + (float) getWepOffY());
		}
		//draw the gun
		if(engine!=null){
			engine.getInGameImg().drawCentered(xOffset + (float)getEngOffX(), yOffset + (float)getEngOffY());
		}
		//draw the ship
		getImg().setCenterOfRotation((getImg().getWidth()/2),(getImg().getHeight()/2));
		getImg().drawCentered(xOffset + (float)getX(), yOffset + (float)getY());	
	}

	public void update(int delta, GameplayState gs){
		//update ship
			physAnchor.update(delta);
			
			getCollider().setCenterX((float)getX());
			getCollider().setCenterY((float)getY());
			getRadarRadius().setCenterX((float)getX());
			getRadarRadius().setCenterY((float)getY());
			double angle = (Math.toRadians(getImg().getRotation()));
		if(getWeapon()!=null){
			updateGun(angle, delta);
		}
		if(getEngine()!=null){
			//update engine			
			setEngOffX(getX() + engineOffsetDistance *Math.cos(angle));		//where to draw engine on ship
			setEngOffY(getY() + engineOffsetDistance *Math.sin(angle));
		}

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
		if(getEngine().getSideThrust().playing()){
//			getEngine().getSideThrust().stop();
		}
//    	getEngine().getSideThrust().playAt(1.0f, 0.5f, (float)getX(), (float)getY(), 1.0f);
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
		if(getEngine().getSideThrust().playing()){
//			getEngine().getSideThrust().stop();
		}
//    	getEngine().getSideThrust().playAt(1.0f, 0.5f, (float)getX(), (float)getY(), 1.0f);
	}
	/**
	 * move the ship forward
	 * @param delta
	 */
	public void moveForward(int delta){
		physAnchor.pushDir(getRot(), getEngine().getThrustX() * delta * SCLSPD);
		if(getEngine().getPrimeThrust().playing()){
//			getEngine().getPrimeThrust().stop();
		}
//		getEngine().getPrimeThrust().playAsSoundEffect(0.5f, 1.0f, false, (float)getX(), (float)getY(), 0.0f);
		
//		if(getEngine().getPt().playing()){
//			getEngine().getPt().stop();
//		}
//		getEngine().getPt().play(1.0f, 1.0f);
	}
	/**
	 * move the ship backwards
	 * @param delta
	 */
	public void moveBackward(int delta){
		physAnchor.pushDir(getRot(), - getEngine().getThrustY() * delta * SCLSPD);
		if(getEngine().getPrimeThrust().playing()){
			getEngine().getPrimeThrust().stop();
		}
//		getEngine().getPrimeThrust().playAsSoundEffect(0.5f, 1.0f, false, (float)getX(), (float)getY(), 0.0f);

//		getEngine().getPt().play(1.0f, 1.0f);
//		getEngine().getPt().playAt(0.5f, 0.75f, (float)getX(), (float)getY(), 0.0f);
	}
	
	/**
	 * this will strafe the ship left
	 * @param delta
	 */
	public void strafeLeft(int delta){
		physAnchor.pushDir(getRot() - HALFPI, getEngine().getStrafeRate() * delta * SCLSPD);
		if(getEngine().getSideThrust().playing()){
			getEngine().getSideThrust().stop();
		}
//    	getEngine().getSideThrust().playAt(1.0f, 0.5f, (float)getX(), (float)getY(), 1.0f);
	}
	/**
	 * this will strafe the ship right
	 * @param delta
	 */
	public void strafeRight(int delta){
		physAnchor.pushDir(getRot() + HALFPI, getEngine().getStrafeRate() * delta * SCLSPD);
		if(getEngine().getSideThrust().playing()){
			getEngine().getSideThrust().stop();
		}
//    	getEngine().getSideThrust().playAt(1.0f, 0.5f, (float)getX(), (float)getY(), 1.0f);
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

	
	private void updateGun(double angle, int delta){
		double wx = getX() + (gunOffsetDistance * Math.cos(angle));
		double wy = getY() + (gunOffsetDistance * Math.sin(angle));
		
		setWepOffX(wx);		//where to draw gun on ship
		setWepOffY(wy);
		
		gun.setX(wx);	//pushes location down to basic shot
		gun.setY(wy);
		gun.setSpeed(physAnchor.getSpeedX(),physAnchor.getSpeedY());
		gun.setAngle(getImg().getRotation());
		gun.tickTimer(delta);
	}
	
	
	/*Begin massive list of getters & setters.
	 * Yuck.
	 */
	
	public boolean isDead(){
		return health <= 0;
	}
	
	public void onDie(GameplayState c){
		//Play sound
		getDeathSnd().playAt(0.6f, 1.0f, (float)getX(), (float)getY(), 0.0f);

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean tryShot() {
		return gun.canIshoot();
	}

	public Circle getRadarRadius() {
		return radar;
	}

	public void setRadarRadius(Circle radarRadius) {
		this.radar = radarRadius;
	}
	
	public void setFaction(int i){
		this.faction = i;
	}
	
	public int getFaction(){
		return this.faction;
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

	public boolean isRadarState() {
		return radarState;
	}

	public void setRadarState(boolean radarState) {
		this.radarState = radarState;
	}
}
