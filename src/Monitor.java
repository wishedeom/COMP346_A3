import java.util.concurrent.locks.Condition;


/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	//Constant status types available to the philosopher
	private enum status {EATING, HUNGRY, THINKING, TALKING}

	private static final boolean VERBOSE = false;
	
	private status state[];
	private int piNumberOfChopsticks;
	
	private int talkCounter;
//	private boolean busyTalking;
//	private boolean busyEating;
	
	private Condition okToEat[];
	private Condition okToTalk;
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Sets appropriate number of chopsticks based on the # of philosophers
		//Since each philosopher is sharing the chopsticks, there will only be one on the right 
		piNumberOfChopsticks = (piNumberOfPhilosophers); //-1 to account for the fact that we are including the value 0
		
		//busyTalking = false;
		talkCounter = 0;
		//okToTalk = new Condition;
		okToEat = new Condition[piNumberOfPhilosophers];
		
		//Sets the size of the state array
		state = new status[piNumberOfPhilosophers];
		//Initializes the state of each philosopher to thinking
		for (int i=0; i<piNumberOfPhilosophers; ++i)
		{
			state[i]= status.THINKING;
			System.out.println(state[i]);
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		/*
		 * Pseudocode
		 * 
		 *
		 * state[piTID] == 'hungry'; //Assume an enum state with eating, thinking and hungry as options
		 * test(piTID);
		 * if(state[piTID] != 'eating'
		 * {
		 * 		self.wait();
		 * }
		 * <<eat>> //happens after return
		 */
	
		state[piTID] = status.HUNGRY; //Assume an enum state with eating, thinking and hungry as options
		test(piTID);
		if(state[piTID] != status.EATING)
			{
				try {
					okToEat[piTID].wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 
				//Handles initial cases when okToEat[piTID] is null
				catch (NullPointerException e) {
					//e.printStackTrace();
				}
			}
		//piTID eat set in Philosopher object
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		/*
		 * Pseudocode
		 * 
		 * state[piTID] == 'thinking';
		 * test((piTID-1)%piNumberOfChopsticks); // circular array
		 * test((piTID+1)%piNumberOfChopsticks);
		 * 
		 */
		 state[piTID].equals(status.THINKING); 
		 test(((piTID-1)%piNumberOfChopsticks+piNumberOfChopsticks)%piNumberOfChopsticks); 
		 test(((piTID+1)%piNumberOfChopsticks+piNumberOfChopsticks)%piNumberOfChopsticks);
	}

	/**
	 * Checks that chopsticks are available from adjacent philosophers
	 */
	public synchronized void test(final int piTID)
	{
		/*
		 * Pseudocode
		 * 
		 *
		 * state[piTID] == 'hungry'; //Assume an enum state with eating, thinking and hungry as options
		 * test(piTID);
		 * if(state[(piTID-1)%piNumberOfChopsticks)] != 'eating' && state[piTID] == 'hungry')
		 * {
		 * 		state[piTID] = 'eating'
		 * }
		 * self[i].signal //allows signals to neighbors
		 */
		 state[piTID].equals(status.HUNGRY); //Assume an enum state with eating, thinking and hungry as options
		 
		 if(VERBOSE)
		 {
			 System.out.println("Mod value " + (piNumberOfChopsticks));
			 System.out.println("piTID " + piTID);
			 System.out.println("Index of left philosopher" + (((piTID-1)%(piNumberOfChopsticks)+piNumberOfChopsticks)%piNumberOfChopsticks));
			 System.out.println("Index of right philosopher" + (((piTID+1)%(piNumberOfChopsticks)+piNumberOfChopsticks)%piNumberOfChopsticks));
		 }
			 
		 if(!(state[(((piTID-1)%(piNumberOfChopsticks)+piNumberOfChopsticks)%piNumberOfChopsticks)]).equals(status.EATING)
				 && !(state[(((piTID+1)%(piNumberOfChopsticks)+piNumberOfChopsticks)%piNumberOfChopsticks)]).equals(status.EATING)
				 && state[piTID].equals(status.HUNGRY))
			 {
			 	//busyEating = true; //TODO check placement
			    state[piTID] = status.EATING;
			 }
		 try
		 {
		    okToEat[piTID].signal();
		 }
	    //Handles the case when there is no okToEat[piTID] to signal
	    catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating). 
	 */
	public synchronized void requestTalk() 
	{
		//check whether she can talk
		//continue if possible
		//wait if busy by adding to queue
		if(talkCounter>0)
		{
			try 
			{
				okToTalk.wait();
			}
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				//talkCounter++; //ensures that the talk counter is incremented to prevent other threads from 
				e.printStackTrace();
			}
		}
		talkCounter++;
		
	}

	/**
	 * Signal all- 
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		//after talking, set 
		//continue if possible
		//wait if busy by adding to queue
		talkCounter--;
		try{
			okToTalk.signal();
		}
		catch(NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}

// EOF
