import java.util.LinkedList;
import java.util.Queue;
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
	private enum status {EATING, HUNGRY, THINKING, TALKING};
	private status state[];
	private int piNumberOfChopsticks;
	
//	private Object eatingLock;
	private boolean talkingCondition;
	
//	Queue<Integer> busyEating = new LinkedList<Integer>();
	Queue<Integer> busyTalking = new LinkedList<Integer>();

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Sets appropriate number of chopsticks based on the # of philosophers
		//Since each philosopher is sharing the chopsticks, there will only be one on the right 
		int piNumberOfChopsticks = piNumberOfPhilosophers;
		
		talkingCondition = false;
		
		//Sets the size of the state array
		state = new status[piNumberOfPhilosophers];
		//Initializes the state of each philosopher to thinking
		for (int i=0; i<piNumberOfPhilosophers; ++i)
			state[i]= status.THINKING;
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
		while(state[piTID] != status.EATING)
			{
				//TODO figure out how to access Philosopher object to get it to wait!
				//self.yield();
				//wait(piTID); 
				continue;
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
		 state[piTID].equals(status.THINKING); //TODO not sure whether the philosopher should be set back to thinking or simply notify
		 test((piTID-1)%piNumberOfChopsticks); 
		 test((piTID+1)%piNumberOfChopsticks);
		
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
		 test(piTID);
		 if(!state[(piTID)%piNumberOfChopsticks].equals(status.EATING) && state[piTID].equals(status.HUNGRY))
			 {
			  	state[piTID] = status.EATING;
			 }
		 //self[i].signal() //allows signals to neighbors
		
		
	}
	
	
	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk(final int piTID)
	{
		//check whether she can talk
		//continue if possible
		//wait if busy by adding to queue
		while (!busyTalking.isEmpty()){}
		busyTalking.add(piTID);
		
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
		
	}
	
	public synchronized void Signal()
	{
		while(!busyTalking.isEmpty())
		{
					
		}
		
//		notify();
	}
	
	/**
	
	 */
	public synchronized void Wait(final int piTID)
	{
		busyTalking.add(piTID);
	}
	
}

// EOF
