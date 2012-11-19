package level;

import level.triggers.CompleteObjective;
import level.triggers.CountTrigger;
import level.triggers.DeathTrigger;
import level.triggers.ManyShipSpawner;
import level.triggers.MultiTrigger;
import level.triggers.SpawnShip;
import level.triggers.ToggleNavPoint;
import ents.BasicShip;
import ents.EntityFactory;

/**
 * Trigger Factory, per EMR's revelation we should have a factory much like the entFac
 * that allows us to build triggers on the fly!
 * 
 * *note: needs to only take primitives :\
 * @author Roohr
 *
 */
public class TriggerFactory {

	private EntityFactory entFac;
	
	public TriggerFactory(EntityFactory ef){
		entFac = ef;
	}
	
	
	private TriggerTypes convertTrigType(String trigType) {
		if(trigType.equals(TriggerTypes.SHIP.toString())){
			return TriggerTypes.SHIP;
		}else if(trigType.equals(TriggerTypes.DOODAD.toString())){
			return TriggerTypes.DOODAD;
		}else if(trigType.equals(TriggerTypes.SHOT.toString())){
			return TriggerTypes.SHOT;
		}else if(trigType.equals(TriggerTypes.TRIGGER.toString())){
			return TriggerTypes.TRIGGER;
		}
		return null;
	}
	
	
	/**
	 * builds a new CompleteObjective Trigger
	 * @param trig
	 * @param o
	 * @param name
	 * @return
	 */
	public CompleteObjective buildCompleteObjective(TriggerTypes trig, BasicObjective o, String name){
		CompleteObjective cob = new CompleteObjective(trig, o, name);
		return cob;
	}
	
	/**
	 * builds a new Count Trigger
	 * @param trig
	 * @param tot
	 * @param name
	 * @return
	 */
	public CountTrigger buildCountTrigger(String trigType, int tot, String name){
		CountTrigger cot = new CountTrigger(convertTrigType(trigType), tot, name);
		return cot;
	}

	/**
	 * builds a new Death Trigger
	 * @param trig
	 * @param name
	 * @return
	 */
	public DeathTrigger buildDeathTrigger(String trigType, String name){
		DeathTrigger det = new DeathTrigger(convertTrigType(trigType), name);
		return det;
	}
	
	/**
	 * builds a new ManyShipSpawn Trigger
	 * @param trig 
	 * @return
	 */
	public ManyShipSpawner buildMultiShipSpawnTrigger(TriggerTypes trig){
		ManyShipSpawner msp = new ManyShipSpawner(trig);
		return msp;
	}
	
	/**
	 * builds a new Multi Trigger
	 * @param trig
	 * @param targets
	 * @return
	 */
	public MultiTrigger buildMultiTrigger(TriggerTypes trig, String[] targets){
		MultiTrigger mul = new MultiTrigger(trig, targets);
		return mul;
	}
	
	/**
	 * builds a new SpawnShip Trigger
	 * @param trig
	 * @param s
	 * @return
	 */
	public SpawnShip buildSpawnShipTrigger(String trigType, String shipPointer, String gunPointer, String engPointer){
		SpawnShip sp = new SpawnShip(convertTrigType(trigType), entFac.buildShip(shipPointer, gunPointer, engPointer));
		return sp;
	}
	
	/**
	 * builds a new ToggleNavPoint Trigger
	 * @param trig
	 * @param p
	 * @param st
	 * @return
	 */
	public ToggleNavPoint buildToggleNavPoint(TriggerTypes trig, NavPoint p, boolean st){
		ToggleNavPoint tog = new ToggleNavPoint(trig, p, st);
		return tog;
	}
}
