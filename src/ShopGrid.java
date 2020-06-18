package socialDistanceShopSampleSolution;
import java.util.concurrent.Semaphore;

/**
 * Class to represent the shop, as a grid of gridblocks
 **/
public class ShopGrid {
	private GridBlock [][] Blocks;
	private final int x;
	private final int y;
	public final int checkout_y;
	private final static int minX =5;//minimum x dimension
	private final static int minY =5;//minimum y dimension
   private Semaphore mutex;
   private Semaphore mutexA;

       
   /** 
    * Empty constructor 
    **/
	ShopGrid() throws InterruptedException {
      mutex = new Semaphore(1);
      mutexA = new Semaphore(1);

		this.x=20;
		this.y=20;
		this.checkout_y=y-3;
		Blocks = new GridBlock[x][y];
		int [] [] dfltExit= {{10,10}};
		this.initGrid(dfltExit);
	}
	
   /**
    * Constructor
    * @param x Length of the shop grid
    * @param y Height of the shop grid
    * @param exitBlocks coordinates of the exit block in the shop
    * @param maxPeople maximum amount of people allowed in the shop
    **/
	ShopGrid(int x, int y, int [][] exitBlocks,int maxPeople) throws InterruptedException {
      mutex = new Semaphore(1);
      mutexA = new Semaphore(1);

      if (x<minX) x=minX; //minimum x
		if (y<minY) y=minY; //minimum y
		this.x=x;
		this.y=y;
		this.checkout_y=y-3;
		Blocks = new GridBlock[x][y];
		this.initGrid(exitBlocks);
	}
	
   /** 
    * Initializes the shop grid
    * @param exitBlocks coordinates of the exit block in the shop
    **/
	private  void initGrid(int [][] exitBlocks) throws InterruptedException {
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				boolean exit=false;
				boolean checkout=false;
				for (int e=0;e<exitBlocks.length;e++)
						if ((i==exitBlocks[e][0])&&(j==exitBlocks[e][1])) 
							exit=true;
				if (j==(y-3)) {
					checkout=true; 
				}//checkout is hardcoded two rows before  the end of the shop
				Blocks[i][j]=new GridBlock(i,j,exit,checkout);
			}
		}
	}
	
	/**
    * Get max X for grid
    * @return maximum length of grid
    **/
	public  int getMaxX() {
		return x;
	}
	
	/** 
    * Get max y  for grid
    * @return maximum height of grid
    **/ 
	public int getMaxY() {
		return y;
	}

	/** 
    * Get max y  for grid
    * @return maximum height of grid
    **/ 
	public GridBlock whereEntrance() { //hard coded entrance
		return Blocks[getMaxX()/2][0];
	}

	/**
    * is a position a valid grid position?
    **/
	public  boolean inGrid(int i, int j) {
		if ((i>=x) || (j>=y) ||(i<0) || (j<0)) 
			return false;
		return true;
	}
	
	/**
    * called by customer when entering shop
    **/
	public GridBlock enterShop() throws InterruptedException  {
         mutexA.acquire();
         while (!whereEntrance().get()){/*wait*/}
		   GridBlock entrance = whereEntrance();
         mutexA.release();
		return entrance;
	}
		
	/**
    * called when customer wants to move to a location in the shop
    **/
	public GridBlock move(GridBlock currentBlock,int step_x, int step_y) throws InterruptedException {  
		//try to move in 
      
		int c_x= currentBlock.getX();
		int c_y= currentBlock.getY();
		
		int new_x = c_x+step_x; //new block x coordinates
		int new_y = c_y+step_y; // new block y  coordinates
		
		//restrict i an j to grid
		if (!inGrid(new_x,new_y)) {
			//Invalid move to outside shop - ignore
			return currentBlock;
			
		}

		if ((new_x==currentBlock.getX())&&(new_y==currentBlock.getY())) //not actually moving
			return currentBlock;
	   
      

		GridBlock newBlock = Blocks[new_x][new_y];
      mutex.acquire();
			if (newBlock.get())  {  //get successful because block not occupied 
				currentBlock.release(); //must release current block
		   }
			else {
				newBlock=currentBlock;
				///Block occupied - giving up
			}
      mutex.release();
      
		return newBlock;
	} 
	
	/**
    * called by customer to exit the shop
    */
	public void leaveShop(GridBlock currentBlock)   {
		currentBlock.release();
	}

}
