package level;

import level.triggers.BasicTrigger;
import level.triggers.CompleteObjective;
import level.triggers.CountTrigger;
import level.triggers.ManyShipSpawner;
import level.triggers.MultiTrigger;
import level.triggers.SpawnShip;
import level.triggers.ToggleNavPoint;

import org.newdawn.slick.geom.Shape;

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
			return trigger;
		}else if(classType.equals("count")){
			trigger = new CountTrigger();
			doBasicSetup(cldr, args, trigger);
			buildCountTrigger((CountTrigger)trigger, args);
			return trigger;
		}else if(classType.equals("multrig")){
			trigger = new MultiTrigger();
			doBasicSetup(cldr, args, trigger);
			buildMultiTrigger((MultiTrigger)trigger, args);
		}
		return null;
	}

	private void doBasicSetup(Shape cldr, String[] args, BasicTrigger t) {
		t.setTriggerType(convertTrigType(args[0]));		//arg0 = enumerated trigger type
		t.setName(args[1]);								//arg1 = name
		t.setX(Integer.valueOf(args[2]));				//arg2 = trig's x
		t.setY(Integer.valueOf(args[3]));				//arg3 = trig's y
		t.setTargetName(args[4]);						//arg4 = trigs targetname
		t.trigger(Boolean.valueOf(args[5]));			//arg5 = trig's starting boolean
		t.setCollider(cldr);
	}

	/**
	 * parses a string to get the enum equivalent, trigType is essentially masking 
	 * @param trigType
	 * @return
	 */
	private TriggerTypes convertTrigType(String trigType) {
		if(trigType.equals(TriggerTypes.SHIP.toString())){
			return TriggerTypes.SHIP;
		}else if(trigType.equals(TriggerTypes.DOODAD.toString())){
			return TriggerTypes.DOODAD;
		}else if(trigType.equals(TriggerTypes.SHOT.toString())){
			return TriggerTypes.SHOT;
		}else if(trigType.equals(TriggerTypes.TRIGGER.toString())){
			return TriggerTypes.TRIGGER;
		}else if(trigType.equals("null")){
			return null;
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
	public CountTrigger buildCountTrigger(CountTrigger c, String...args){
		c.setTotal(Integer.valueOf(args[6]));
		return c;
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
	public MultiTrigger buildMultiTrigger(MultiTrigger t, String...args){
		String[] train = new String[args.length-6];
		System.arraycopy(args, 6, train, 0, train.length);
		t.setTargetNames(train);
		return t;
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
		boolean boolVal = Boolean.valueOf(args[7]);
		t.setNavPointer(args[6]);
		t.setNavState(boolVal);
	}

	public EntityFactory getEntFac() {
		return entFac;
	}
}
