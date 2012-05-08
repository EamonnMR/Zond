package core;

import ents.BasicShip;

/**
 * the client class for the player, both house-keeping and gameplay related
 * @author Roohr
 * @version 1.0
 */
public class PlayerClient {

	
	//vars
	private BasicShip playerShip;
//	private BasicArmor[] armorCol;
//	private BasicEngine[] engineCol;
//	private BasicGun[] gunCol;
	private boolean isAlive;
	private int[] keys;
	
	//constructor
	public PlayerClient(int clientId){
//		armorCol = new BasicArmor[0];		//noooooooooooooot yet
//		engineCol = new BasicEngine[0];		//noooooooooooooot yet
//		gunCol = new BasicGun[0];			//noooooooooooooot yet
		keys = new int[7];					//mappable keys...that are kinda fail
		iniKeys();
	}
	//methods
	
	/**
	 * puts all keycodes into the keys array. Ideally
	 * this will be configurable outside of hardcode.
	 */
	public void iniKeys(){
		keys[0] = 200;
		keys[1] = 208;
		keys[2] = 203;
		keys[3] = 205;
		keys[4] = 30;
		keys[5] = 31;	
		keys[6] = 57;	//spacebar
	}
	
	/**
	 * set a key
	 * @param i pointer
	 * @param val key value
	 */
	public void setKey(int i, int val){
		keys[i] = val;
	}
	
	/**
	 * get a key value
	 * @param i pointer
	 * @return key value
	 */
	public int getKey(int i){
		return keys[i];
	}
	
	/**
	 * set client's ship 
	 * @param ship
	 */
	public void setShip(BasicShip ship){
		playerShip = ship;
	}
	/**
	 * get client's ship
	 * @return BasicShip
	 */
	public BasicShip getShip(){
		return playerShip;
	}
	
	/**
	 * set client's alive state
	 * @param value 
	 */
	public void setAlive(boolean value){
		isAlive = value;
	}
	/**
	 * get client's alive state
	 * @return
	 */
	public boolean getAlive(){
		return isAlive;
	}
}
