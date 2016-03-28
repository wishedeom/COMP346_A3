import java.util.LinkedList;
import java.util.Queue;

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
	Queue<Integer> busyEating = new LinkedList<Integer>();
	Queue<Integer> busyThinking = new LinkedList<Integer>();

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Sets appropriate number of chopsticks based on the # of philosophers
		//Since each philosopher is sharing the chopsticks, there will only be one on the right 
		int piNumberOfChopsticks = piNumberOfPhilosophers;
		
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
		 * <<eat>>
		 */
		
		state[piTID-1] = status.HUNGRY; //Assume an enum state with eating, thinking and hungry as options
		test(piTID-1);
		if(state[piTID-1] != status.EATING)
			{
				//TODO how to access Philosopher object
				//self.wait(); //Make Philosopher object with piTID wait 
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
	}
	
	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * Signal - 
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		
	}
}

// EOF
