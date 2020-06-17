//package socialDistanceShopSampleSolution;
import java.util.concurrent.atomic.*;
import java.util.concurrent.Semaphore; 

/**
 * GridBlock class to represent a block in the shop.
 **/
public class GridBlock {
	private boolean isOccupied;                           /*changed isOccupied to atomic boolean*/
	private final boolean isExit; 
	private final boolean isCheckoutCounter;
	private int [] coords; // the coordinate of the block.
	private int ID;
	
	public static int classCounter=0;
   private Semaphore mutex;
	
   /** 
    * Constructor
    * @param exitBlock
    * @param checkoutBlock
    **/
	GridBlock(boolean exitBlock, boolean checkoutBlock) throws InterruptedException {
      mutex = new Semaphore(1);                                 /*added */
		isExit=exitBlock;
		isCheckoutCounter=checkoutBlock;
		isOccupied =false;                                     /*original */
		ID=classCounter;
      
		classCounter++;
	}
	
   /** 
    * Constructor
    * @param x X Coordinate of entrance block
    * @param y Y Coordinate of entrance block
    * @param exitBlock Verifies if current block is the exit block
    * @param refreshBlock Verifies if current block is the refresh block
    **/
	GridBlock(int x, int y, boolean exitBlock, boolean refreshBlock) throws InterruptedException {
		this(exitBlock,refreshBlock);
		coords = new int [] {x,y};
	}
	
	/**
    * Getter: Returns x coordinate of customer location in shop
    **/
	public  int getX() {return coords[0];}  
	
	/**
    * Getter: Returns y coordinate of customer location in shop
    **/
	public  int getY() {return coords[1];}
	
	/**
    * for customer to move to a block
    **/
	public boolean get() throws InterruptedException {
      if(!occupied()) {
         //mutex.acquire();
         isOccupied = true;
         //mutex.release();
         return true;
      }
		return false;
	}
		
	/** 
    * for customer to leave a block
    **/
	public  void release() {
		isOccupied =false;                                
	}
	
	/**
    * Getter: Verifies if block is occupied by another customer
    **/
	public boolean occupied() {                         
		return isOccupied;
	}
	
	/**
    * Getter: Verifies if block is exit
    **/
	public  boolean isExit() {
		return isExit;	
	}

	/**
    * Getter: Verifies if block is checkout
    **/
	public boolean isCheckoutCounter() {
		return isCheckoutCounter;
	}
	
	/**
    * Getter: Gets customer ID
    **/
	public int getID() {return this.ID;}
}
