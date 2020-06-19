package socialDistanceShopSampleSolution;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.atomic.*;

/**
 * Class that keeps track and controls the customers location in the shop grid
 * Protected with Atomic variables or with synchronized
 * (this is a separate class so don't have to access thread)
 * 
 * @author Zukiswa Lobola
 * @since 2020-06-22 
 **/
public class CustomerLocation  { 
	
	private final int ID; //total customers created
	private Color myColor;
	private AtomicBoolean inRoom;
	private AtomicInteger x;
	private AtomicInteger y;
	
	/**
    * Constructor
    * @param ID Unique customer ID
    **/   
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
    * @param x X coordinate of block
    **/
	public void  setX(int x) { 
      this.x.set(x);
   }	
		
	/**
    * Setter: Sets y coordinate of customer location in shop
    * @param y Y coordinate of block
    **/
	public void  setY(int y) {	  
      this.y.set(y);	
   }
	
	/**
    * Setter: Sets customers location to be inside the shop
    * @param in 
    **/
	public  void setInRoom(boolean in) {
		this.inRoom.set(in);
   }

	/**
    * Getter: Gets x coordinate of customer location in shop
    * @return int Returns x coordinate of block
    **/
	public int getX() { 
      return x.get();
   }	
	
	/**
    * Getter: Gets y coordinate of customer location in shop
    * @return int Returns y coordinate of block
    **/
	public int getY() {	
      return y.get();
   }
	
	/**
    * Getter: Gets customer ID
    * @return int Returns unique customer ID
    **/
	public int getID() {	
      return ID;	
   }

	/**
    * Getter: Verification of customer is inside the shop or not
    * @return boolean Returns true if customer is inside the shop room
    **/
	public  boolean inRoom() {
			return inRoom.get();
	}
      
	/**
    * Getter: unique colour of customer
    * @return Color Returns customers colour
    **/
	public synchronized  Color getColor() { 
      return myColor; 
   }	
}
