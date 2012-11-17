package level;

import core.PlayerClient;

/**
 * scenario is an instance of BasicLevel, load these things from txt files
 * @author proohr
 *
 */
public class Scenario extends LevelDataModel{

	private PlayerClient equipment;	//creates a preset list of ships and equipment for player
	private int faction;			//which faction listing does this fall under?
	private String desc;			//ui description of scenario
	
	public Scenario(String name) {
		super(name);

	}

	public PlayerClient getEquipment() {
		return equipment;
	}

	public void setEquipment(PlayerClient equipment) {
		this.equipment = equipment;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
