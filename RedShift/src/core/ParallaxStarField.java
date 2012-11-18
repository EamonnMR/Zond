package core;

import org.newdawn.slick.Image;

public class ParallaxStarField{
	private int extrasize;
	private long randomizeDistanceFromBorder;
	private int wideRangeX,wideRangeY,wideRangeZ;
	private int numStars;
	private Image img;
	private int minX, maxX, minY, maxY, minZ;
	float[] x,y,z;

	public ParallaxStarField(int extrasize, long randomizeDistanceFromBorder,
			int screenX, int screenY, int numStars, Image img, int minZ,
			int maxZ) {
		//Get up & set variables:
		super();
		this.extrasize = extrasize;
		minX = -extrasize;
		minY = -extrasize;
		maxX = screenX + extrasize;
		maxY = screenY + extrasize;
		this.randomizeDistanceFromBorder = randomizeDistanceFromBorder;
		this.numStars = numStars;
		this.img = img;
		this.minZ = minZ;
		wideRangeX = screenX + (2 * extrasize);
		wideRangeY = screenY + (2 * extrasize);
		wideRangeZ = maxZ - minZ;		
	}
	
	public float wideRandomX(){
		return (float)(Math.random() * wideRangeX) - extrasize;
	}

	public float wideRandomY(){
		return (float)(Math.random() * wideRangeY) - extrasize;
	}
	
	public float narrowRandom(){
		return (float) (Math.random() * randomizeDistanceFromBorder);
	}
	
	public float randomZ(){
		return (float) ((Math.random() * wideRangeZ) + minZ);
	}

	public void update(int dCamX, int dCamY){
		for(int i =0; i < numStars; i++){
			x[i] += z[i] * dCamX; //Update the x position
			//Test to make sure the star hasn't fallen off-if it has, put a new one on the other side in a semi-random position
			if(x[i] > minX){ //Falls off left of screen
				x[i] = maxX - narrowRandom();
				y[i] = wideRandomY();
				z[i] = randomZ();
			} else if(x[i] < maxX){ //Falls off right of screen
				x[i] = minX + narrowRandom();
				y[i] = wideRandomY();
				z[i] = randomZ();
			} else {
				y[i] += z[i] * dCamY;//Update the y position (and check out the boundries.)
				if(y[i] > minY){ //Falls off top of screen
					x[i] = wideRandomX();
					y[i] = maxY - narrowRandom();
					z[i] = randomZ();
				} else if(y[i] < maxY){ //Falls off bottom of screen
					x[i] = wideRandomX();
					y[i] = minY + narrowRandom();
					z[i] = randomZ();
				} else {
					//We didn't need to wipe the star this time, so draw it.
					img.draw(x[i],y[i]);
				}
			}
		}
	}
}