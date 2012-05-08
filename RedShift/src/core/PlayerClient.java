package core;

import ents.BasicShip;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class PlayerClient {

	
	//vars
	private BasicShip playerShip;
// XXX use member variables.
//	private BasicArmor[] armorCol;
//	private BasicEngine[] engineCol;
//	private BasicGun[] gunCol;
	private boolean isAlive;
	private int[] keys;
	
	//const
	public PlayerClient(int clientId){
//		armorCol = new BasicArmor[0];
//		engineCol = new BasicEngine[0];
//		gunCol = new BasicGun[0];
		keys = new int[7];
		iniKeys();
	}
	//methods	
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