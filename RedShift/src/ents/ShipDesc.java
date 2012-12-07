package ents;

public class ShipDesc {
	public String kind, gun, engine, trigtarg;
	public double x, y;
	public effects.Effect fx;
	public boolean isAi;
	
	public ShipDesc(String kind, String gun, String engine, double[] pt, boolean isAi, String trig) {
		this.kind = kind;
		this.gun = gun;
		this.engine = engine;
		this.x = pt[0];
		this.y = pt[1];
		this.isAi = isAi;
		this.trigtarg = trig;
	}

	@Override
	public String toString() {
		return "SHIPDESC: ''"+ kind + "'' gun: ''" + gun + "'' engine: ''" +
				engine + "'' location: " + Double.toString(x) + "," +
				Double.toString(y) + " fx: " + fx.toString();
	}

	
}