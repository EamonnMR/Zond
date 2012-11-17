package level;

import ents.BasicShip;
import level.triggers.CompleteObjective;
import level.triggers.CountTrigger;
import level.triggers.DeathTrigger;
import level.triggers.ManyShipSpawner;
import level.triggers.MultiTrigger;
import level.triggers.SpawnShip;
import level.triggers.ToggleNavPoint;

/**
 * Trigger Factory, per EMR's revelation we should have a factory much like the entFac
 * that allows us to build triggers on the fly!
 * @author Roohr
 *
 */
public class TriggerFactory {

	
	public TriggerFactory(){
		
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
	public CountTrigger buildCountTrigger(TriggerTypes trig, int tot, String name){
		CountTrigger cot = new CountTrigger(trig, tot, name);
		return cot;
	}
	
	/**
	 * builds a new Death Trigger
	 * @param trig
	 * @param name
	 * @return
	 */
	public DeathTrigger buildDeathTrigger(TriggerTypes trig, String name){
		DeathTrigger det = new DeathTrigger(trig, name);
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
	public SpawnShip buildSpawnShipTrigger(TriggerTypes trig, BasicShip s){
		SpawnShip sp = new SpawnShip(trig, s);
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
