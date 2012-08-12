package level.triggers;

import level.TriggerTypes;

public class CountTrigger extends BasicTrigger {

	private int total;
	private int count;
	
	public CountTrigger(TriggerTypes trig, int tot, String name) {
		super(trig);
		this.setX(0);
		this.setY(0);
		this.setCollider(null);
		this.setName(name);
		this.total = tot;
	}
	
	public void add(){
		count++;
		System.out.println(count);
	}
	
	public int getTotal(){
		return total;
	}
	
	public int getCount(){
		return count;
	}
}
