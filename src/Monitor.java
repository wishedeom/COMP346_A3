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
	private enum status {EATING, HUNGRY, THINKING}
	
	private status state[];
	private int piNumberOfChopsticks;
	
	private boolean busyTalking;
	
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Sets appropriate number of chopsticks based on the # of philosophers
		//Since each philosopher is sharing the chopsticks, there will only be one on the right 
		piNumberOfChopsticks = piNumberOfPhilosophers; //-1 to account for the fact that we are including the value 0
		
		//busyTalking = false;
		busyTalking = false;
		
		//Sets the size of the state array
		state = new status[piNumberOfPhilosophers];
		//Initializes the state of each philosopher to thinking
		for (int i=0; i<piNumberOfPhilosophers; ++i)
		{
			state[i] = status.THINKING;
			System.out.println("Philosopher #" + i + " begins by " + state[i]);
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
		// The philosopher is hungry.
		state[piTID] = status.HUNGRY;
		
		// Keep checking if neighbours are eating: If they are, wait. If they are not, and I am hungry, start eating!
		while(true)
		{
			if(testNeighbours(piTID))
			{
				state[piTID] = status.EATING;
				break;
			}
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
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
		 state[piTID] = status.THINKING;
		 notifyAll();		 
	}

	/**
	 * Checks that chopsticks are available from adjacent philosophers and that the given philosopher is hungry.
	 */
	public synchronized boolean testNeighbours(final int piTID)
	{
		return state[Math.floorMod(piTID - 1, piNumberOfChopsticks)] != status.EATING
				&& state[Math.floorMod(piTID + 1, piNumberOfChopsticks)] != status.EATING
				&& state[piTID] == status.HUNGRY;
		
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
		while (true)
		{
			if(!busyTalking && state[piTID] != status.EATING)
			{
				busyTalking = true;
				break;
			}
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
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
		busyTalking = false;
		notifyAll();
	}
}

// EOF
