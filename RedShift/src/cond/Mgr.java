package cond;



//Note that this only manages conditions that need to be updated.
//Others exi
public class Mgr {
	Condition[] conditions;
	
	public void update(core.GameplayState c, int delta){
		for(Condition d : conditions){
			d.update(c, delta);
		}
	}
}
