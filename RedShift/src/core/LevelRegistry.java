package core;

import java.util.HashMap;

import level.LevelDataModel;

/**
 * two thoughts on this thing. One it could be the sole
 * master list of levels in the game.
 * OR
 * its a list of levels that can be instantiate as a modularized
 * campaign system.
 * @author proohr
 *
 */
public class LevelRegistry {

	private HashMap<String, LevelDataModel> register;
	
	/**
	 * straightforward, just give the Registry a hashmap of levels, 
	 * couples fine with gamedatabase, just give gDB a levelregistry builder
	 * @param levels
	 */
	public LevelRegistry(HashMap<String, LevelDataModel> levels){
		this.register = levels;
	}
	
	/**
	 * add a level to the registry, addition is sequential
	 * @param ldm LevelDataModel
	 */
	public void addLevelToRegistry(LevelDataModel ldm){
		this.register.put(ldm.getName(), ldm);
	}
	
	/**
	 * replaces a specific level with a new one
	 * @param lvl name of existing level
	 * @param ldm LevelDataModel replacement
	 */
	public void replaceLevelInRegistry(String lvl, LevelDataModel ldm){
		this.register.put(lvl, ldm);
	}
	
	/**
	 * pretty simple, get an LDM from the registry
	 * @param name name of level
	 * @return LevelDataModel 
	 */
	public LevelDataModel getLevelFromRegistry(String name){
		return this.register.get(name);
	}
	
	//obligatories
	public void setRegister(HashMap<String, LevelDataModel> incoming){
		this.register = incoming;
	}
	
	public HashMap<String, LevelDataModel> getRegister(){
		return this.register;
	}
}
