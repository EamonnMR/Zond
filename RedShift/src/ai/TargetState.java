package ai;

public class TargetState extends AIState {

	public void move(){
		System.out.println("Target + move = Move");
	}
	public void pivot(){
		System.out.println("Target + pivot = Pivot");
	}
	public void attack(){
		System.out.println("Target + attack = Attack");
	}
	public void target(){
		System.out.println("Target + target = Target");
	}
}
