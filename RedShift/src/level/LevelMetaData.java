package level;

import java.util.HashMap;

/**
 * level meta data class holds the information of a level for the ui
 * @author proohr
 * @version 1.0
 */
public class LevelMetaData {

	private String name, uiname, tltip, descrip, levelType;
	private int faction;
	private String[] client_Ships, client_Guns, client_Engines;
	private HashMap<String, LevelObjective> objectives;
	
	public LevelMetaData(){}

	public String getUiname() {
		return uiname;
	}

	public void setUiname(String uiname) {
		this.uiname = uiname;
	}

	public String getTltip() {
		return tltip;
	}

	public void setTltip(String tltip) {
		this.tltip = tltip;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public String[] getClient_Ships() {
		return client_Ships;
	}

	public void setClient_Ships(String[] client_Ships) {
		this.client_Ships = client_Ships;
	}

	public String[] getClient_Guns() {
		return client_Guns;
	}

	public void setClient_Guns(String[] client_Guns) {
		this.client_Guns = client_Guns;
	}

	public String[] getClient_Engines() {
		return client_Engines;
	}

	public void setClient_Engines(String[] client_Engines) {
		this.client_Engines = client_Engines;
	}

	public HashMap<String, LevelObjective>getObjectives() {
		return objectives;
	}

	public void setObjectives(HashMap<String, LevelObjective> objectives) {
		this.objectives = objectives;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
