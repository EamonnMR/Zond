package level;

import level.triggers.BasicTrigger;
import level.triggers.CompleteObjective;
import level.triggers.CountTrigger;
import level.triggers.DeathTrigger;
import level.triggers.ManyShipSpawner;
import level.triggers.MultiTrigger;
import level.triggers.SpawnShip;
import level.triggers.ToggleNavPoint;

import org.newdawn.slick.geom.Shape;

import ents.BasicShip;
import ents.EntityFactory;
import ents.ShipDesc;

/**
 * Trigger Factory, per EMR's revelation we should have a factory much like the entFac
 * that allows us to build triggers on the fly!
 * 
 * note 11/26/12: this is using nothing but string...args, so be warned it assumes a certain order.
 * *note: needs to only take primitives :\
 * @author Roohr
 *
 */
public class TriggerFactory {

	private EntityFactory entFac;
	
	public TriggerFactory(){}
	
	public void setEntFac(EntityFactory ef){
		entFac = ef;
	}
	
	/**
	 * builds any trigger, not comfortable with var args but we'll see how this goes.
	 * right now I'm just going to test the triggers we're using. 
	 * @param args
	 * @return
	 */
	public BasicTrigger buildTrigger(Shape cldr, ShipDesc d, String classType,String... args){
		BasicTrigger trigger;
		if(classType.equals("ini")){
			trigger = new BasicTrigger();
			doBasicSetup(cldr, args, trigger);
			return trigger;
		}else if (classType.equals("spawn")){
			trigger = new SpawnShip();
			doBasicSetup(cldr, args,trigger);
			buildSpawnShip((SpawnShip)trigger, d);		//mmmmm casting (:/)
			return trigger;
		}else if(classType.equals("togglenav")){
			trigger = new ToggleNavPoint();
			doBasicSetup(cldr, args, trigger);
			buildToggleNavPoint((ToggleNavPoint)trigger, args);
		}
		return null;
	}

	private void doBasicSetup(Shape cldr, String[] args, BasicTrigger t) {
		t.setName(args[0]);								//arg0 = name
		t.setTriggerType(convertTrigType(args[1]));		//arg1 = enumerated trigger type
		t.setX(Integer.valueOf(args[2]));				//arg2 = trig's x
		t.setY(Integer.valueOf(args[3]));				//arg3 = trig's y
		t.setTargetName(args[4]);						//arg4 = trigs targetname
		t.setCollider(cldr);
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
	public void buildSpawnShip(SpawnShip t,ShipDesc d){
		t.setShip(entFac.shipFromDesc(d));
	}
	
	/**
	 * builds a new ToggleNavPoint Trigger
	 * @param trig
	 * @param p
	 * @param st
	 * @return
	 */
	public void buildToggleNavPoint(ToggleNavPoint t, String...args){
		boolean boolVal = Boolean.valueOf(args[8]);
		NavPoint p = new NavPoint(Float.valueOf(args[5]),Float.valueOf(args[6]),args[7], boolVal);
		t.setNavPoint(p);
		t.trigger(boolVal);
	}

	public EntityFactory getEntFac() {
		// TODO Auto-generated method stub
		return null;
	}
}
