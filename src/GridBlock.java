package socialDistanceShopSampleSolution;
import java.util.concurrent.atomic.*;
import java.util.concurrent.Semaphore; 

/**
 * GridBlock class to represent a block in the shop.
 * 
 * @author Zukiswa Lobola
 * @since 2020-06-22
 **/
public class GridBlock {
	private AtomicBoolean isOccupied;                  
	private final boolean isExit; 
	private final boolean isCheckoutCounter;
	private int [] coords; // the coordinate of the block.
	private int ID;
	
	public static int classCounter=0;
   private Semaphore mutex;                                       /* lock for mutual exclusion */
	
   /** 
    * Constructor
    * @param exitBlock
    * @param checkoutBlock
    **/
	GridBlock(boolean exitBlock, boolean checkoutBlock) throws InterruptedException {
      mutex = new Semaphore(1);                             
		isExit=exitBlock;
		isCheckoutCounter=checkoutBlock;
      isOccupied = new AtomicBoolean(false);                      /*changed to AtomicBoolean */
		mutex.acquire();                                            /* Acquire the lock to update class counter and thread ID */
      ID=classCounter;
		classCounter++;
      mutex.release();                                            /* Release the lock */
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
    * @return Returns x coordinate of the block in the shop
    **/
	public  int getX() {return coords[0];}  
	
	/**
    * Getter: Returns y coordinate of customer location in shop
    * @return Returns y coordinate of the block in the shop
    **/
	public  int getY() {return coords[1];}
	
	/**
    * for customer to move to a block
    **/
	public boolean get() throws InterruptedException {
      if(!occupied()) {
         isOccupied.set(true);
         return true;
      }
		return false;
	}
		
	/** 
    * for customer to leave a block
    **/
	public  void release() {
      isOccupied.set(false);
                              
	}
	
	/**
    * Getter: Verifies if block is occupied by another customer
    * @return boolean Returns true if the block is occupied. 
    **/
	public boolean occupied() {                    
		return isOccupied.get();
	}
	
	/**
    * Getter: Verifies if block is exit
    * @return boolean Returns true if the block is the shop exit.
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
    * @param int Returns unique customer ID.
    **/
	public int getID() {
      return this.ID;
   }
}
