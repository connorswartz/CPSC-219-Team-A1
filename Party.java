import java.awt.Color;

/**
 * 
 * @author Muaawia Jannat
 *
 * This class represents a single party containing the name, color, projected number of seats, and the 
 * projected percentage of votes for the party.
 */
public class Party {
	//Instance variables
	private String name;
	private float projectedNumberOfSeats;
	private float projectedPercentageOfVotes;
	private Color partyColour;

	/**
	 * Create a party instance with a specific name
	 * @param name The name of this party instance
	 */
	public Party(String name) {
		this.name = name;
	}

	/**
	 * Create a party instance with a specific name for the party,
	 * the number of seats the party is expected to win,
	 * the percentage of votes the party is expected to win.
	 * @param name The name of this party instance
	 * @param projectedNumberOfSeats The number of seats this party is expected to win
	 * @param projectedPercentageOfVotes The percentage of the votes this party is expected to win
	 */
	public Party(String name, float projectedNumberOfSeats, float projectedPercentageOfVotes) {
		this.setName(name);
		this.setProjectedNumberOfSeats(projectedNumberOfSeats);
		this.setProjectedPercentageOfVotes(projectedPercentageOfVotes);
	}

	/**
	 * Set the name of this party instance
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the name of this party
	 * @return The name of this party instance
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the number of seats this party is expected to win
	 * @param projectedNumberOfSeats The number of seats this party
	 * is expected to win.
	 */
	public void setProjectedNumberOfSeats(float projectedNumberOfSeats) {
		if (projectedNumberOfSeats==0) {
			this.projectedNumberOfSeats = 0;
		}

		if(projectedNumberOfSeats<0) {
			System.out.println("Error: setProjectedNumberOfSeats was called with an invalid argument: less than 0");
		}

		else {
			this.projectedNumberOfSeats = projectedNumberOfSeats;
		}
	}

	/**
	 * Get the number of seats this party is expected to win
	 * @return the number of seats this party is expected to win
	 */
	public float getProjectedNumberOfSeats() {
		return projectedNumberOfSeats;
	}

	/**
	 * Set the percentage of the votes this party is expected to win.
	 * @param projectedPercentageOfVotes A float value between 0 and 1 representing the
	 * percent of votes the party is expected to win.
	 */
	public void setProjectedPercentageOfVotes(float projectedPercentageOfVotes) {
		if (projectedPercentageOfVotes<0 || projectedPercentageOfVotes>1) {
			System.out.println("Error: setProjectedPercentageOfVotes was called with an invalid argument: value must be between 0 and 1");
		}

		else {
			this.projectedPercentageOfVotes = projectedPercentageOfVotes;
		}
	}

	/**
	 * Get the percentage of the votes this party is expected to win
	 * @return The percentage of votes this party is expected to win
	 */
	public float getProjectedPercentageOfVotes() {
		return projectedPercentageOfVotes;
	}

	/**
	 * Set the color assigned to this party.
	 * @param partyColour: The color assigned to this party.
	 */
	public void setColour(Color partyColour) {
		this.partyColour = partyColour;
	}

	/**
	 * Get the color assigned to this party.
	 * @return Color: The color assigned to this party.
	 */
	public Color getColour() {
		return partyColour;
	}

	/**
	 * Uses the specified totalSeats to calculate the projected percentage(between 0 and 1) of
	 * seats this party is expected to win.
	 * @param totalSeats that are available in the election
	 * @return double that represents the projected percentage of seats for this party instance.
	 */
	public double projectedPercentOfSeats(int totalSeats) {
        if (totalSeats<0) {
            System.out.println("Error: projectedPercentOfSeats was called with an invalid argument: value was less than 0");
            return 0;
        }

        if(totalSeats==0) {
        System.out.println("Error: projectedPercentOfSeats was called with an invalid argument: Can't divide by zero");
        	return 0;
        }

        else {
            return (double) (this.projectedNumberOfSeats/ totalSeats);
        }
    }

	/**
	 * Formats relevant party information in the following format:
	 * "Name of this party (<[RGB color values of this party's partyColour property]>,
	 *  <projected percentage of votes of votes>, <projected percentage of seats>)
	 *
	 * @return string containing the relevant information in the format described above.
	 */
	public String toString() {
		String ans = "name ([colour value], <projected %>% of votes, <projected seats> seats)";

		ans = ans.replace("name", this.name);

		//	What if the color is undefined
		if (this.partyColour == null) {
			ans = ans.replace("[colour value], ", "");
		}
		
		else {
			String red = String.valueOf(this.partyColour.getRed());
			String blue = String.valueOf(this.partyColour.getBlue());
			String green = String.valueOf(this.partyColour.getGreen());
			String color = red + "," + blue + "," + green;
			ans = ans.replace("colour value", color);
		}

		ans = ans.replace("<projected %>", String.valueOf((int)(this.projectedPercentageOfVotes*100)));
		ans = ans.replace("<projected seats>", String.valueOf(this.projectedNumberOfSeats));
		return ans;
	}

	/**
	 * Helper method that calculates the number of stars won by this party
	 * @param seatsOrVotesPerStar The number of seats/votes represented by one star.
	 * @param useSeats Boolean indicating whether to calculate stars won using seats
	 *                 or votes.
	 * @return int that represents the number of stars won for this party instance.
	 */
	public int getStarsWon(double seatsOrVotesPerStar, boolean useSeats) {
		int starsWon;
		if (useSeats) {
			double var = (float) (this.projectedNumberOfSeats / seatsOrVotesPerStar);
			starsWon = (int) Math.floor(var);
		}
		
		else {
			double var = (int)  Math.floor(this.projectedPercentageOfVotes * 100 / seatsOrVotesPerStar);
			starsWon = (int) var;
		}

		return starsWon;
	}

	/**
	 * Helper method that checks if the party won majority of votes/seats in the election.
	 * @param maxStars The total number of stars that can be won.
	 * @param starsWon The number of stars the party won.
	 * @return boolean that is true when the party won the majority or false otherwise
	 */
	public boolean wonMajority(int maxStars,  int starsWon) {
		int starsRequiredToWin = (maxStars/2);
		return (starsWon > starsRequiredToWin);
	}

	/**
	 * Helper method for creating the String returned by the
	 * textVisualizationBySeats and textVisualizationByVotes methods.
	 *
	 * @param maxStars The total number of stars that can be won.
	 * @param useSeats A boolean when true ensures the method generates a visualization
	 *                 using seats, otherwise generates a visualization using votes.
	 * @param valuePerStar The number of seats/votes represented by one star.
	 *
	 * @return String that represents the results of the election for this party
	 *  using the following characters:
	 *
	 *	'*'	=>  This character represents a single star won by the party.
	 *
	 *	'|'	=>  This character represents the point where the number
	 *  		of stars won is enough to ensure the party has secured a
	 *  		majority in the election.
	 *
	 *	' '	=>  This character(empty space) indicates the number of
	 *  		stars NOT won by the party.
	 */
	public String getVisualization(int maxStars, double valuePerStar, boolean useSeats) {

		char[] formatHelper = new char[maxStars+1];
		int starsRequiredToWin = (int) Math.floor(maxStars/2);

		//Helper method getStarsWon is called to get the number of stars won by this party
		int starsWon = this.getStarsWon(valuePerStar, useSeats);

		//Helper method wonMajority is called to check if the party won majority of votes/seats in the election.
		boolean wonMajority = this.wonMajority(maxStars,
				starsWon);

		boolean tinyMarginOfVictory = (wonMajority && (starsWon-starsRequiredToWin > 1) && !useSeats);
		int indexForBarPlacement = (!wonMajority || tinyMarginOfVictory)? starsRequiredToWin: starsRequiredToWin+1;

		for (int i = 0; i<starsWon; i++) {
			formatHelper[i] = '*';
		}
		
		for (int i = starsWon; i<=maxStars; i++) {
			formatHelper[i] = ' ';
		}

		char replaceThis = formatHelper[indexForBarPlacement];
		formatHelper[starsWon] = replaceThis;
		formatHelper[indexForBarPlacement] = '|';

		return String.valueOf(formatHelper) + " " + toString();
	}

	/**
	 * Creates a text visualization by seats for the results of the election
	 * @param maxStars The total number of stars that can be won.
	 * @param numOfSeatsPerStar The number of votes represented by one star.
	 *
	 * @return String that represents the results of the election
	 *  using the following characters:
	 *
	 *	'*'	=>  This character represents a single star won by the party.
	 *
	 *	'|'	=>  This character represents the point where the number
	 *  		of stars won is enough to ensure the party has secured a
	 *  		majority in the election.
	 *
	 *	' '	=>  This character(empty space) indicates the number of
	 *  		stars NOT won by the party.
	 */
	public String textVisualizationBySeats(int maxStars, double numOfSeatsPerStar) {
		return getVisualization(maxStars,numOfSeatsPerStar, true);
	}

	/**
	 * Creates a text visualization by votes for the results of the election
	 * @param maxStars The total number of stars that can be won.
	 * @param percentOfVotesPerStar The number of votes represented
	 * by one star.
	 *
	 * @return String that represents the results of the election
	 *  using the following characters:
	 *
	 *	'*'	=>  This character represents a single star won by the party.
	 *
	 *	'|'	=>  This character represents the point where the number
	 *  		of stars won is enough to ensure the party has secured a
	 *  		majority in the election.
	 *	' '	=>  This character(empty space) indicates the number of
	 *  		stars NOT won by the party.
	 */
	public String textVisualizationByVotes(int maxStars, double percentOfVotesPerStar) {
		return getVisualization(maxStars, percentOfVotesPerStar, false);
	}
}