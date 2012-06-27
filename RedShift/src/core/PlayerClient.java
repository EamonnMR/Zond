package core;

import java.util.HashMap;


import org.newdawn.slick.geom.Rectangle;

import ents.BasicArmor;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;

/**
 * the client class for the player, both house-keeping and gameplay related
 * @author Roohr
 * @version 1.0
 */
public class PlayerClient {

	
	//vars
	private BasicShip currentShip;
	private HashMap<String, BasicShip> clientShips;
	private HashMap<String, BasicArmor>clientArmors;
	private HashMap<String, BasicEngine> clientEngines;
	private HashMap<String, BasicGun> clientGuns;
	private boolean clientIsAlive;
	private HashMap<String, Integer> clientKeys;
	private Rectangle clientCameraBounds;
	
	//constructor
	public PlayerClient(int clientId){
		
	}
	//methods
	
	/**
	 * puts all keycodes into the keys array. Ideally
	 * this will be configurable outside of hardcode.
	 */
//	public void iniKeys(){
//	}
	
	/**
	 * set a key
	 * @param i pointer
	 * @param val key value
	 */
//	public void setKey(int i, int val){
//	}
	
	/**
	 * get a key value
	 * @param i pointer
	 * @return key value
	 */
//	public int getKey(int i){
//		return keys[i];
//	}
	
	/**
	 * set client's ship 
	 * @param ship
	 */
	public void setPlayShip(BasicShip ship){
		currentShip = ship;
	}
	/**
	 * get client's ship
	 * @return BasicShip
	 */
	public BasicShip getPlayShip(){
		return currentShip;
	}
	
	/**
	 * set client's alive state
	 * @param value 
	 */
	public void setAlive(boolean value){
		clientIsAlive = value;
	}
	/**
	 * get client's alive state
	 * @return
	 */
	public boolean getAlive(){
		return clientIsAlive;
	}

	public Rectangle getCameraBounds() {
		return clientCameraBounds;
	}

	public void setCameraBounds(Rectangle cameraBounds) {
		clientCameraBounds = cameraBounds;
	}

	//Handle Client's ship they own
	public HashMap<String, BasicShip> getClientShips() {
		return clientShips;
	}

	public void setClientShips(HashMap<String, BasicShip> clientShips) {
		this.clientShips = clientShips;
	}
	
	public BasicShip retrieveShip(String pointer){
		return clientShips.get(pointer);
	}
	
	//Handle Client's armor they own
	public HashMap<String, BasicArmor> getClientArmors() {
		return clientArmors;
	}

	public void setClientArmors(HashMap<String, BasicArmor> clientArmors) {
		this.clientArmors = clientArmors;
	}

	public BasicArmor retrieveArmor(String pointer){
		return clientArmors.get(pointer);
	}
	
	//Handle Client's armor they own
	public HashMap<String, BasicEngine> getClientEngines() {
		return clientEngines;
	}

	public void setClientEngines(HashMap<String, BasicEngine> clientEngines) {
		this.clientEngines = clientEngines;
	}

	public BasicEngine retrieveEngine(int pointer){
		return clientEngines.get(pointer);
	}
	
	//Handle Client's armor they own
	public HashMap<String, BasicGun> getClientGuns() {
		return clientGuns;
	}

	public void setClientGuns(HashMap<String, BasicGun> clientGuns) {
		this.clientGuns = clientGuns;
	}

	public BasicGun retrieveGun(String pointer){
		return clientGuns.get(pointer);
	}
	
	//Handle Client Keybindings
	public HashMap<String, Integer> getKeys() {
		return clientKeys;
	}

	public void setKeys(HashMap<String, Integer> keys) {
		this.clientKeys = keys;
	}
	
	public void assignKey(String key, int value){
		clientKeys.put(key, value);
	}

	//Is Client still alive?
	public boolean isAlive() {
		return clientIsAlive;
	}

	public boolean tryShot() {
		return currentShip.tryShot();
		
	}

	public void updateCamera(ClientGameplayState c) {
		c.setCamX((int)currentShip.getX());
		c.setCamY((int)currentShip.getY());
	}
}
