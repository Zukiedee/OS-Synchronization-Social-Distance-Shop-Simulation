package socialDistanceShopSampleSolution;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.atomic.*;

/**
 * Class that keeps track and controls the customers location in the shop grid
 * (this is a separate class so don't have to access thread)
 **/
public class CustomerLocation  { 
	
//can protect with Atomic variables or with synchronized	
	private final int ID; //total customers created
	private Color myColor;
	private AtomicBoolean inRoom;
	private AtomicInteger x;
	private AtomicInteger y;
	
	CustomerLocation(int ID ) {
		Random rand = new Random();
		float c = rand.nextFloat();
		myColor = new Color(c, rand.nextFloat(), c);	//only set at beginning	by thread
		inRoom = new AtomicBoolean(false);
		this.ID=ID;
		this.x = new AtomicInteger(0);
		this.y = new AtomicInteger(0);
	}

	
	/**
    * Setter: Sets x coordinate of customer location in shop
    **/
	public void  setX(int x) { 
      this.x.set(x);
   }	
		
	/**
    * Setter: Sets y coordinate of customer location in shop
    **/
	public void  setY(int y) {	  
      this.y.set(y);	
   }
	
	/**
    * Setter: Verifies if customer is inside the shop
    **/
	public  void setInRoom(boolean in) {
		this.inRoom.set(in);
   }

	/**
    * Getter: Gets x coordinate of customer location in shop
    **/
	public int getX() { 
      return x.get();
   }	
	
	/**
    * Getter: Gets y coordinate of customer location in shop
    **/
	public int getY() {	
      return y.get();
   }
	
	/**
    * Getter: Gets customer ID
    **/
	public int getID() {	
      return ID;	
   }

	/**
    * Getter: Verification of customer is inside the shop or not
    **/
	public  boolean inRoom() {
			return inRoom.get();
	}
      
	/**
    * Getter: color of customer
    **/
	public synchronized  Color getColor() { 
      return myColor; 
   }	
}
