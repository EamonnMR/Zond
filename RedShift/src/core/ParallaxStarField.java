package core;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public class ParallaxStarField{


	Vector2f tempCamVector; //Only use this variable during update!
	Image img;
	Rectangle screen, offScreenStars; //Screen = the screen, OffScreenStars = stars not on the screen that
	//Still "exist" so that they don't seem to dissapear when they go off screen for an instant.
	//This is also the space where new stars appear-so make sure it's large enough that there's a good chance
	//That any given location in OffScreenStars is *not* on screen (see Star.Update for the sad truth about
	//Why.)
	Star[] stars;
	int xWidth, yWidth;
	float zWidth, minZ;
	int extrasize;
	
	public ParallaxStarField(int extrasize,int screenX, int screenY, int numStars, Image img, float minZ,
		float maxZ) {
		this.extrasize = extrasize;
		screen = new Rectangle(0,0, screenX, screenY);
		offScreenStars = new Rectangle(-extrasize, -extrasize, screenX + extrasize, screenY + extrasize);
			
		this.minZ = minZ;
		this.zWidth = maxZ - minZ;
		xWidth = screenX + 2 * extrasize;
		yWidth = screenX + 2 * extrasize;
			
		stars = new Star[numStars];
		for(int i = 0; i < numStars; i++){
			stars[i] = randomStar();
		}
	}
		
	
	
	public void update(int dCamX, int dCamY, int camX, int camY){
		tempCamVector = new Vector2f(dCamX, dCamY);
		
		
		for(Star i : stars){
			i.update();
		}
	}
	
	private Star randomStar(){
		Star toSender = new Star();
		toSender.randomize();
		return toSender;
	}
	
	public void draw(Graphics g){
		for(Star i : stars){
			i.starDraw(g);
		}
	}
	private class Star{
		Vector2f position;
		float z;
		
		protected Star(){
			position = new Vector2f();
		}
		
		public void randomize(){
		    //Create a random star in the correct range
			position.x = (float) (offScreenStars.getX() + Math.random() * offScreenStars.getWidth());
			position.y = (float) (offScreenStars.getY() + Math.random() * offScreenStars.getHeight());
			z          = (float) ((float) minZ + Math.random() * zWidth);
		}
		
		public void update(){
			position = position.add(tempCamVector.scale(z));
			if(!offScreenStars.contains(position.x, position.y)){
				randomize();
				while(screen.contains(position.x, position.y)){
					//That's right, it makes a star, THEN makes sure it's
					//Not on screen. That's why it's quick and dirty, you see.
					randomize();
				}
			}
		}
		
		public void starDraw(Graphics g){
			g.drawImage(img, position.x, position.y);
		}
	}
	public void FUCKsetImg(Image img){
		//HURR CAN'T PUT SET IMG IN THE INITIALIZER BECAUSE IT WONT WORK
		if (this.img == null){
			this.img = img;
		}
		//FIXME: Come on, there must be a way to *not* set this every frame.
	}
}