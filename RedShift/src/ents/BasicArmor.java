package ents;

import org.newdawn.slick.Image;

/**
 * BasicArmor class; a data class to represent Armor on a BasicShip 
 * @author proohr
 * @version 1.0
 */
public class BasicArmor {

	//vars
	private int id;			//game Id
	private int value;		//point value
	private Image icon;		//gui-related icon
	private int cost;		//cost to buy
	
	
	//constructor
	public BasicArmor(int i){
		id= i;
	}
	
	//FULL BUILD
	public BasicArmor(int i, int val, Image ico, int cst){
		id = i;
		value = val;
		icon = ico;
		cost = cst;
	}

	//methods

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
