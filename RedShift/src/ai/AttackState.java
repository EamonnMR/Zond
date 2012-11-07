package ai;

public class AttackState extends AIState{

	public void move(){
		System.out.println("Attack + move = Move");
	}
	public void pivot(){
		System.out.println("Attack + pivot = Pivot");
	}
	public void attack(){
		System.out.println("Attack + attack = Attack");
	}

}
