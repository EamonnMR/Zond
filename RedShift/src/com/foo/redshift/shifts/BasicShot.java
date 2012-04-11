package com.foo.redshift.shifts;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class BasicShot extends BaseShift {

    // vars
    private int damage;
    private Shape collider;
    private double speed;
    private int lifetime;
    private double theta;

    // const
    public BasicShot(Image img, double spd, int life, int dmg, double sx,
	    double sy, Shape col) {
	setImg(img);
	speed = spd;
	lifetime = life;
	damage = dmg;
	setX(sx);
	setY(sy);
	collider = col;
	theta = Math.PI / 2;
    }

    // methods
    public void update(int delta) {
	lifetime -= delta;

	collider.setCenterX((float) getX());
	collider.setCenterY((float) getY());

	double gx = (-Math.cos(Math.toRadians(getImg().getRotation()) + theta))
		+ getX() + speed;
	double gy = (-Math.sin(Math.toRadians(getImg().getRotation()) + theta))
		+ getY() + speed;

	setX(gx);
	setY(gy);
    }

    public void render() {
	getImg().drawCentered((float) getX(), (float) getY());
    }

    public int getDamage() {
	return damage;
    }

    public void setDamage(int damage) {
	this.damage = damage;
    }

    public Shape getCollider() {
	return collider;
    }

    public void setCollider(Shape collider) {
	this.collider = collider;
    }

    public double getSpeed() {
	return speed;
    }

    public void setSpeed(double speed) {
	this.speed = speed;
    }

    public int getLifetime() {
	return lifetime;
    }

    public void setLifetime(int lifetime) {
	this.lifetime = lifetime;
    }
}
