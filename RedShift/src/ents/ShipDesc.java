package ents;

public class ShipDesc {
	public String kind, gun, engine;
	public double x, y;
	public effects.Effect fx;
	
	public ShipDesc(String kind, String gun, String engine, double[] pt, effects.Effect fx) {
		this.kind = kind;
		this.gun = gun;
		this.engine = engine;
		this.x = pt[0];
		this.y = pt[1];
		this.fx = fx;
	}

}