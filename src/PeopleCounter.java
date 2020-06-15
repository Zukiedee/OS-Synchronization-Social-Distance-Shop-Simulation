package socialDistanceShopSampleSolution;

import java.util.concurrent.Semaphore; 


/** 
 * Class to keep track of people that are inside, outside and have left the shop
 **/
public class PeopleCounter {
	private int peopleOutSide; //counter for people arrived but not yet in the building
	private int peopleInside; //people inside the shop
	private int peopleLeft; //people left the shop
	private int maxPeople; //maximum for lockdown rules
   
   private Semaphore mutex;			                     /* keeps mutual exclusion -- only one thread accesses the critical section i.e. the shop */
   private Semaphore customers_inside;		               /* keeps track of dishes on the rack */
	private Semaphore space_available;	                  /* keeps track of empty slots in the rack */
	   	
   
	PeopleCounter(int max) {
   	this.mutex = new Semaphore(1);                     /* mutex lock to access critical sections in the shop */
		this.customers_inside = new Semaphore(0); 			/* initially the shop is empty (no customers in side) */
	   this.space_available = new Semaphore(max);	      /* initially all the space in the shop is available */

   	peopleOutSide = 0;
		peopleInside = 0;
		peopleLeft = 0;
		maxPeople = max;                                   /* set maxPeople = to max input parameter */
	}
		
	/** 
    * Getter Method: The amount of people waiting outside of the shop
    * @return People outside the shop
    **/
	public int getWaiting() {
		return peopleOutSide;
	}

	/** 
    * Getter Method: The amount of people inside the shop
    * @return People inside the shop
    **/
	public int getInside() {
		return peopleInside;
	}
	
	/** 
    * Getter Method: The total amount of people associated to the shop
    * @return People Wating outside + people inside + people that have exited the shop
    **/
	public int getTotal() {
		return (peopleOutSide+peopleInside+peopleLeft);
	}

	/** 
    * Getter Method: The amount of people that have done shopping and left the shop
    * @return People that have exited the shop
    **/
	public int getLeft() {
		return peopleLeft;
	}
	
	/** 
    * Getter Method: Maximum lockdown restriction amount in the shop
    * @return Maximum amount of people allowed in shop
    **/
	public int getMax() {
		return maxPeople;
	}
	
   /** 
    * Updates the counters for a person arriving at the shop and waiting outside
    **/
	public void personArrived() throws InterruptedException {
      mutex.acquire();                                   /* Acquire the lock to access the shop entrance */
      
      /* critical section */
      peopleOutSide++;
      
      mutex.release();                                   /* Releases the lock */

      space_available.acquire();			                  /* waits until there is space left in the shop and then decrements space available by 1 */
      customers_inside.release();				            /* Increments number of customers inside the shop */
	}
	
	/**
    * Update counters for a person entering the shop
    **/
	public void personEntered() throws InterruptedException {
   	mutex.acquire();					                     /* Acquire the lock to access the shop entrance */

      /* critical section */		
      peopleOutSide--;
		peopleInside++;
      
      mutex.release();					                  /* Release the lock */
	}

	/**
    * Update counters for a person exiting the shop
    **/
	public void personLeft() throws InterruptedException {
		mutex.acquire();					                  /* Acquire the lock to leave the shop */

      space_available.release();
      customers_inside.acquire();
      
		/* critical section */
		peopleInside--;
		peopleLeft++;

		mutex.release();					                  /* Release the lock */
	}

	/**
    * Reset - not really used
    **/
	synchronized public void resetScore() {
		peopleInside = 0;
		peopleOutSide = 0;
		peopleLeft = 0;
	}
}
