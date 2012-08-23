package effects;

import core.ClientGameplayState;

public class Stack {
	Frame top;
	
	public void push(Effect e){
		top = new Frame(e, top);
	}
	
	public void unwind(ClientGameplayState c){
		while(top != null){
			//Pop that stack till the top is null
			top.fx.affect(c);
			top = top.next;
		}
	}
	
	private class Frame{
		public Frame(Effect fx, Frame next) {
			this.fx = fx;
			this.next = next;
		}
		public Effect fx;
		public Frame next;
	}
}
