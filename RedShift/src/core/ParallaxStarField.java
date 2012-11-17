package core;

import org.newdawn.slick.Image;

public class ParallaxStarField{
/*	private long extrasize;
	private long randomizeDistanceFromBorder;
	private long ScreenX; //Argument to constructor
	private long ScreenY; //Argument to constructor
	private int numStars;
	private float maxDepth;
	private Image img;

	//Inside constructor:
	xMin = -extrasize;
	yMin = -extrasize;
	xMax = screenX + extrasize;
	yMax = screenY + extrasize;
	
	//Variables for the stars
	float[] x,y,z;
	
	public float wideRandomX(){
		return Math.random() * screenX + (2 * (extrasize)) - extrasize;

	public float wideRandomY(){
		return Math.random() * screenY + (2 * (extrasize)) - extrasize;
	}

	public float narrowRandom(){
		return Math.random() * randomizeDistanceFromBorder;
	}

	public void update(dCamX, dCamY){
		for(int i =0; i < numStars; i++){
			x[i] += z[i] * dCamX; //Update the x position
			//Test to make sure the star hasn't fallen off-if it has, put a new one on the other side in a semi-random position
			if(x[i]) > minX){ //Falls off left of screen
				x[i] = maxX - narrowRandom();
				y[i] = wideRandomY();
			} else if(x[i]) < maxX){ //Falls off right of screen
				x[i] = minX + narrowRandom();
				y[i] = wideRandomY();
			} else {
				y[i] += z[i] * dCamY;//Update the y position (and check out the boundries.)
				if(y[i]) > minY){ //Falls off top of screen
					y[i] = maxY - narrowRandom();
					x[i] = wideRandomX();
				} else if(y[i]) < maxY){ //Falls off bottom of screen
					y[i] = minY + narrowRandom();
					x[i] = wideRandomX();
				} else {
					//We didn't need to wipe the star this time, so draw it.
					img.draw(x[i],y[i]);
				}
			}
		}
	}*/
}