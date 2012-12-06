package ai;

import java.util.Random;

import ents.BasicShip;

/**
 * the ai interface for the game, outlines basic thinking methods
 * @author proohr
 * @version: 1.0
 */
public interface Actor {

	//update
	public void update();
	
	//checkRadar
	public void checkRadar();
	
	//targetObject
	public void targetObject();
	
	//move in a direction
	public void moveForward();
	
	public void moveBackward();
	
	public void moveLeft();
	
	public void moveRight();
	
	//attacks
	public void attack();
	


}
