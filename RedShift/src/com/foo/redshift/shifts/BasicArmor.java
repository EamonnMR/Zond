package com.foo.redshift.shifts;

import org.newdawn.slick.Image;

public class BasicArmor {

    private int id;
    private int value;
    private Image icon;
    private int cost;

    public BasicArmor(int i) {
	id = i;
    }

    // FULL BUILD
    public BasicArmor(int i, int val, Image ico, int cst) {
	id = i;
	value = val;
	icon = ico;
	cost = cst;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getValue() {
	return value;
    }

    public void setValue(int value) {
	this.value = value;
    }

    public Image getIcon() {
	return icon;
    }

    public void setIcon(Image icon) {
	this.icon = icon;
    }

    public int getCost() {
	return cost;
    }

    public void setCost(int cost) {
	this.cost = cost;
    }

}
