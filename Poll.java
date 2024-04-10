/**
 * 
 * @author Connor Swartz
 *
 * This class represents a single Poll containing a name and an array consisting of parties.
 */
public class Poll {
	//Poll class instance variables
	private String name;
	private Party[] parties;
	private int partiesInPoll;
	
	/**
	 * Constructor for Poll class.
	 * @param pollName For the name of the Poll
	 * @param maxParties The maximum number of parties that can go in Poll's party array. Should not be less than one.
	 */
	public Poll(String pollName, int maxParties) {
		name = pollName;
		
		if (maxParties < 1) {
			maxParties = 10;
		}
		
		parties = new Party[maxParties];
	}
	
	/**
	 * Getter methods for pollName, parties, and numberOfParties.
	 */
	public String getPollName() {
		return name;
	}
	
	public Party[] getParties() {
		return parties;
	}

	public int getNumberOfParties() {
		return partiesInPoll;
	}
	
	/**
	 * Takes no Arguments. Gives String representation of Poll and each party in Poll.
	 * @return Multiple lines, the first being Poll name, each following line containing the name of a party. 
	 */
	public String toString() {
		String whole = name + "\n";
		for (Party party : parties) {
			if (party != null) {
				whole += party.getName() + "\n";
			}
		}
		return whole;
	}
	
	/**
	 * Adds a new party to the parties array. Party being added should not be null, should not add a party if array is
	 * full, or if a party with the same name is already in the array, replace it with the new party. Returns nothing.
	 * @param toAdd The party you want to add to the parties array.
	 */
	public void addParty(Party toAdd) {
		if (toAdd == null) {
			System.out.println("ERROR: Null party cannot be added");
			return;
		}
		
		//Check to see if party with same name as party given as argument already in array.
		//If party with same name in array, replaces it with party given as argument.
		int index = 0;
		boolean check = false;
		while (index < parties.length && parties[index] != null && check == false) {
			if (parties[index].getName().equalsIgnoreCase(toAdd.getName()) == true) {
				parties[index] = toAdd;
				check = true;
			}
			
			index++;
		}
		
		//If party with same name not in array, checks if array is full.
		//Adds party if array not full, gives error message if full.
		if (check == false) {
			if (index < parties.length) {
				parties[index] = toAdd;
				partiesInPoll += 1;
			}
			
			else {
				System.out.println("ERROR: Poll is full");
			}
		}
	}
	
	/**
	 * Takes the name of a party and finds if a party with the same name is in parties array and returns that party.
	 * @param toGet The name of a party you want to find in parties array.
	 * @return A party with a name equal to the parameter or null if no party with such name exists in parties array. 
	 */
	public Party getParty(String toGet) {
		int toReturn = 0;
		for (int index = 0; index < parties.length; index++) {
			if (parties[index] != null && parties[index].getName().equalsIgnoreCase(toGet)) {
				toReturn = index;
			}
		}
		
		if (parties[toReturn].getName().equalsIgnoreCase(toGet)) {
			return parties[toReturn];
		}
		
		else {
			return null;
		}
	}
	
	/**
	 * Gives visual representation of data in Poll, including name of Poll, maximum number of stars, and the number of
	 * seats each star represents.
	 * @param maxStars The maximum number of stars that should be displayed on a single line.
	 * @param numOfSeatsPerStar The number of seats represented by a single star.
	 * @return Multiple lines, the first being the name of Poll, each following line containing information about each
	 * party. 
	 */
	public String textVisualizationBySeats(int maxStars, double numOfSeatsPerStar) {
		String whole = name + "\n";
		for (Party party : parties) {
			if (party != null) {
				whole += party.textVisualizationBySeats(maxStars, numOfSeatsPerStar) + "\n";				
			}
		}
		return whole;
	}
		
	/**
	 * Gives visual representation of data in Poll, including name of Poll, maximum number of stars, and the percent of
	 * votes each star represents.
	 * @param maxStars The maximum number of stars that should be displayed on a single line.
	 * @param percentOfVotesPerStar The percentage of votes represented by a single star.
	 * @return Multiple lines, the first being the name of Poll, each following line containing information about each
	 * party. 
	 */
	public String textVisualizationByVotes(int maxStars, double percentOfVotesPerStar) {
		String whole = name + "\n";
		for (Party party : parties) {
			if (party != null) {
				whole += party.textVisualizationByVotes(maxStars, percentOfVotesPerStar) + "\n";
			}
		}
		return whole;
	}
}