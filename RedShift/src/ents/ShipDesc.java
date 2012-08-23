package ents;

public class ShipDesc {
	public String kind, gun, engine;
	public double x, y;
	
	public ShipDesc(String kind, String gun, String engine, double[] pt) {
		this.kind = kind;
		this.gun = gun;
		this.engine = engine;
		this.x = pt[0];
		this.y = pt[1];
	}

}