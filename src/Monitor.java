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


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Sets appropriate number of chopsticks based on the # of philosophers
		//Since each philosopher is sharing the chopsticks, there will only be one on the right 
		int piNumberOfChopsticks = piNumberOfPhilosophers;
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
	 * Wait ?-
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
