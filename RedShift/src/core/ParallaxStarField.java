//package core;
//
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Image;
//
////public class ParallaxStarField{
//	private int extrasize;
//	private long randomizeDistanceFromBorder;
//	private int wideRangeX,wideRangeY,wideRangeZ;
//	private Image img;
//	private int minX, maxX, minY, maxY, minZ;
//	float[] x,y,z;
//	boolean[] drawMe;
//	private int numStars;
//	
//	//XXX:oi! clean up this language, you fuck nugget
//	public void FUCKsetImg(Image img){
//		//HURR CAN'T PUT SET IMG IN THE INITIALIZER BECAUSE IT WONT WORK
//		if (this.img == null){
//			this.img = img;
//		}
//		//FIXME: Come on, there must be a way to *not* set this every frame.
//	}
//
//	public ParallaxStarField(int extrasize, long randomizeDistanceFromBorder,
//			int screenX, int screenY, int numStars, Image img, int minZ,
//			int maxZ) {
//		//Get up & set variables:
//		this.extrasize = extrasize;
//		minX = -extrasize;
//		minY = -extrasize;
//		maxX = screenX + extrasize;
//		maxY = screenY + extrasize;
//		this.randomizeDistanceFromBorder = randomizeDistanceFromBorder;
//		this.numStars = numStars;
//		//stars = new Star[maxStars];
//		drawMe = new boolean[numStars];
//		this.img = img;
//		this.minZ = minZ;
//		wideRangeX = screenX + (2 * extrasize);
//		wideRangeY = screenY + (2 * extrasize);
//		wideRangeZ = maxZ - minZ;
//		
//		//Fill the Xs with random stars, the Ys with randomness, and the Z with 1...
//		for(int i =0; i < numStars; i++){
//			x[i] = wideRandomX();
//			y[i] = wideRandomY();
//			z[i] = randomZ();
//		}
//	}
//	
//	public float wideRandomX(){
//		return (float)(Math.random() * wideRangeX) - extrasize;
//	}
//
//	public float wideRandomY(){
//		return (float)(Math.random() * wideRangeY) - extrasize;
//	}
//	
//	public float narrowRandom(){
//		return (float) (Math.random() * randomizeDistanceFromBorder);
//	}
//	
//	public float randomZ(){
//		//return (float) ((Math.random() * wideRangeZ) + minZ);
//		return 1; //Testing!
//	}
//
//	public void draw(Graphics g){
//		for(int i =0; i < numStars; i++){
//			//if(drawMe[i]){ //The calculations are all glitched, so this is commented out to display the
//			                 //misplaced stars
//				g.drawImage(img, x[i], y[i]);
//		   	//   }
//		  }
//	 }
//	
////	private class Star{
////		float x, y, z;
////		private Star(float x, float y, float z){
////		//Yes, it's gonna be one of *those* constructors
////			this.x = x;
////			this.y = y;
////			this.z = z;
////		}
////		
////		//private update(int dCamX, int dCamY, int camX, int camY, int leftBound, int rightBound, int topBound, int bottomBound){
////		
////		}
////		
////	}
//	
//	
//	public void update(int dCamX, int dCamY, int camX, int camY){
//		
//		int leftBound, rightBound, topBound, bottomBound;
//		leftBound = minX + camX;
//		rightBound = maxX + camX;
//		topBound = maxY + camY;
//		bottomBound = maxY + camY;
//		
//		for(int i = 0; i < numStars; i++){
//			x[i] += z[i] * dCamX; //Update the x position
//			//Test to make sure the star hasn't fallen off-if it has, put a new one on the other side in a semi-random position
//			if(x[i] < leftBound){ //Falls off left of screen
//				x[i] = maxX - (narrowRandom() - camX);
//				y[i] = wideRandomY() + camY;
//				z[i] = randomZ();
//				drawMe[i] = false;
//			} else if(x[i] > rightBound){ //Falls off right of screen
//				x[i] = minX + narrowRandom() + camX;
//				y[i] = wideRandomY() + camY;
//				z[i] = randomZ();
//				drawMe[i] = false;
//			} else {
//				y[i] += z[i] * dCamY;//Update the y position (and check out the boundries.)
//				if(y[i] < bottomBound){ //Falls off top of screen
//					x[i] = wideRandomX() + camX;
//					y[i] = maxY - (narrowRandom() - camY);
//					z[i] = randomZ();
//					drawMe[i] = false;
//				} else if(y[i] > topBound){ //Falls off bottom of screen
//					x[i] = wideRandomX() + camX;
//					y[i] = minY + narrowRandom() + camY;
//					z[i] = randomZ();
//					drawMe[i] = false;
//				} else {
//					//We didn't need to wipe the star this time, so it's inside the screen
//					//(in the future, we should probably have inner checks to see if it's really on screen
//					//before we draw it, but that's for later.)
//					drawMe[i] = true;
//				}
//			}
//		}
//	}
//}