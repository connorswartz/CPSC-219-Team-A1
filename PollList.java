/**
 * 
 * @author Mohammad Aaraiz
 * 
 * Collection of polls. Uses Party and Poll class. 
 */
public class PollList {
	//Instance variables
	private Poll[] polls;
	private int numOfSeats;
	int MAX_STARS_FOR_VISUALIZATION = 18;

	/**
	 * PollList constructor takes the number of polls and number of seats available 
	 * in the election. Number of polls and seats should be at least 1. 
	 * @param numOfPolls number of polls in PollList
	 * @param numOfSeats number of seats for PollList
	 */
	public PollList(int numOfPolls, int numOfSeats) {
		//If numOfPolls less than one, set polls size to 5
		if (numOfPolls < 1) {
			polls = new Poll[5];

			//and if numOfSeats is less than one, set to 10
			if (numOfSeats < 1) {
				this.numOfSeats = 10;
			}
		}
		
		//otherwise, set arguments provided
		else {
			polls = new Poll[numOfPolls];
			this.numOfSeats = numOfSeats;
		}
	}
	
	/**
	 * Getter methods: toArray returns polls instance
	 * getNumOfSeats returns numOfSeats instance
	 */
	public Poll[] toArray() {
		return polls;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}
	
	/**
	 * Method creates new Party object with name that is passed as parameter
	 * Party object will contain average number of seats and average expected 
	 * percentage of votes.
	 * @param partyName Name of party
	 * @return instance of Party
	 */
	public Party getAveragePartyData(String partyName) {
		//New party object named partyName provided as argument
		Party newParty = new Party(partyName);
		float averageSeats = 0;
		float averageVote = 0;
		int totalCount = 0;
		float sumSeat = 0;
		float sumVote = 0;

		for (int i = 0; i < polls.length; i++) {
			if (polls[i] != null) {
				//Getting party and storing in partyInPoll party object.
				Party partyInPoll = polls[i].getParty(partyName);
				if (partyInPoll != null) {
					totalCount++;
					sumSeat += partyInPoll.getProjectedNumberOfSeats();
					sumVote += partyInPoll.getProjectedPercentageOfVotes();
				}
			}
		}
		
		//To avoid dividing by zero or negative. 
		if (totalCount > 0) {
			averageSeats = sumSeat / totalCount;
			averageVote = sumVote / totalCount;
			
			//Setting averages to newParty object
			newParty.setProjectedPercentageOfVotes(averageVote);
			newParty.setProjectedNumberOfSeats(averageSeats);
		}
		
		return newParty;
	}

	/**
	 * Creates aggregate poll of all polls provided and creates new poll object called Aggregate
	 * @param partyNames Name of party
	 * @return Poll representing aggregate data of all polls in polls array
	 */
	public Poll getAggregatePoll(String[] partyNames) {
		Poll Aggregate = new Poll("Aggregate", partyNames.length);
		
		//Gets average party data of parties and adds to new Poll object
		for (int i = 0; i < partyNames.length; i++) {
			Aggregate.addParty(getAveragePartyData(partyNames[i]));
		}
		
		return Aggregate;
	}

	/**
	 * Adds Poll object provided in the argument and adds it to polls array.
	 * Does not return anything
	 * @param pollToAdd The poll to add into polls array
	 */
	public void addPoll(Poll pollToAdd) {
		boolean doneLoop = false;
		
		if (pollToAdd == null) {
			System.out.println("Error: Null as argument");
		} 
		
		//loop through polls and add argument to polls index
		else {
			for (int i = 0; i < polls.length && doneLoop == false; i++) {
				if (polls[i] == null) {
					polls[i] = pollToAdd;
					//set doneLoop to true to only add argument once where null
					doneLoop = true;	
				} 

				else if (i == polls.length - 1 && polls[i] != null) {
					System.out.println("PollList is full");
				}
			}
		}
	}

	/**
	 * Gets visualization of each poll in the list.
	 * Calculates number of stars per seat.
	 * @return Visualization of poll by seats
	 */
	public String textVisualizationBySeats() {
		float temp = (float) numOfSeats / MAX_STARS_FOR_VISUALIZATION;
		int numOfSeatsPerStar = (int) Math.ceil(temp);
		String textResult = "";
		
		for (int i = 0; i < polls.length; i++) {
			if(polls[i] != null) {
				textResult += polls[i].textVisualizationBySeats(MAX_STARS_FOR_VISUALIZATION, numOfSeatsPerStar);
				textResult += "\n";
			}
		}
		
		return textResult;
	}

	/**
	 * Gets visualization of each poll in the list.
	 * Calculates percentage of votes per star. 
	 * @return Visualization of poll by votes
	 */
	public String textVisualizationByVotes() {
		String textResult = "";
		int percentOfVotesStar = (int) Math.ceil(100.0 / MAX_STARS_FOR_VISUALIZATION);
		
		for (int i = 0; i < polls.length; i++) {
			if(polls[i] != null) {
				textResult += polls[i].textVisualizationByVotes(MAX_STARS_FOR_VISUALIZATION, percentOfVotesStar);
				textResult += "\n";
			}
		}
		
		return textResult;
	}

	/**
	 * Creates string representation with information of number of seats and text visualization by seats 
	 * @return String visualization
	 */
	public String toString() {
		return "Number of seats: " + numOfSeats + "\n" + textVisualizationBySeats();
	}
}