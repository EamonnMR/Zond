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
	
	public CountTrigger(){
		
	}
	
	public void add(){
		count++;
		System.out.println(count);
	}

	//TODO: look into putting count trigger's logic inside it's go() method
//	@Override
//	public void go(GameplayState gs){
//		if(count<total){
//			count++;
//			this.trigger(false);
//		}else if(count==total){
//			this.trigger(true);
//		}
//	}
	
	public void setTotal(int i){
		total = i;
	}
	
	public int getTotal(){
		return total;
	}
	
	public int getCount(){
		return count;
	}
}
