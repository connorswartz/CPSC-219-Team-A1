import java.util.Scanner;

/**
 * 
 * @author Connor Swartz
 * @author Mohammad Aaraiz
 * @author Muaawia Jannat
 * 
 * Combines Factory, Party, Poll, and PollList classes. 
 * This class interacts with the user through the console. The user is prompted for information regarding parties and polls and
 * that information is then displayed accordingly.
 */
public class TextApplication {

    //TextApplication class instance variables
    private PollList polls;

    /**
     * Runs the entire application. Prompts the user for information and then displays the polls created accordingly.
     * Does not return anything.
     */
    public void run() {
        System.out.println("Welcome to Poll Tracker");
        Scanner input = new Scanner(System.in);

        //Input number of seats
        System.out.println("How many seats are available in the election? ");
        int nSeats = Integer.parseInt(input.nextLine());

        //Input party names
        System.out.println("Which parties are in the election (provide names, comma seperated)? ");
        String party = input.nextLine();
        String[] partyNames = party.split(",");

        //Input number of polls
        System.out.println("How many polls do you want to track? ");
        int nPolls = input.nextInt();

        //Input "yes" or "no" when prompted for random set of polls
        System.out.println("Would you like me to create a random set of polls? ");
        String answer = input.next();

        boolean shouldUseRandomSeats = (answer.equalsIgnoreCase("yes")) ? true : false;
        if (shouldUseRandomSeats) {
            generateRandomPolls(nPolls, nSeats, partyNames);
        }

        //Input "votes" or "seats" when prompted for preferred visualization
        System.out.println("Would you prefer a visualization by Votes or Seats?");
        String votesOrSeats = input.next();
        boolean useSeats = (votesOrSeats.equalsIgnoreCase("seats")) ? true : false;

        handleOptions(useSeats, shouldUseRandomSeats, nPolls, nSeats, partyNames);
    }

    /**
     * This helper method helps check for user input regarding whether they want to enter their own information or have a random poll created.
     * Also handles user input regarding choice between "all", "aggregate", and "quit" then calls appropriate methods to print. 
     * @param useSeats Boolean indicating whether to print the aggregate poll visualization by seats or votes
     * @param shouldUseRandomSeats Boolean indicating whether to use random seats or allow user to input their own information
     * @param nPolls The number of polls being tracked that need to be printed out
     * @param nSeats The number of seats available in the election to split among the different parties
     * @param partyNames Array containing each party to be displayed in the election.
     */
    private void handleOptions(boolean useSeats, boolean shouldUseRandomSeats, int nPolls, int nSeats, String[] partyNames) {
    	Scanner input = new Scanner(System.in);
        boolean finishedLoopOnce = false;
        boolean shouldQuit = false;

        while (!shouldQuit) {

            System.out.println("Options: all (show result of all polls) aggregate (show aggregate result), quit (end application) Choose an option: ");
            String choice = input.next();

            shouldQuit = (choice.equalsIgnoreCase("quit")) ? true : false;

            if (shouldQuit) {
            	System.out.println("Exiting application...");
                System.exit(0);
            }
            
            boolean aggregate = (choice.equalsIgnoreCase("aggregate")) ? true : false;

            //Checking if user wants to enter their own data and if so, checking if user has already entered their own data
            if(!shouldUseRandomSeats && !finishedLoopOnce) {
                promptForPollList(nPolls, nSeats, partyNames);
                finishedLoopOnce = true;
            }

            if (aggregate) {
                visualizeAggregatePoll(partyNames, useSeats);
            }

            else {
                visualizePolls(useSeats);
            }
        }
    }

    /**
     * This method returns a visualization of text visualization of polls(all)
     * by votes and by seats depending on the value of useSeats
     * @param useSeats useSeats Boolean indicating whether to print the aggregate poll visualization
     * by seats or votes
     */
    public void visualizePolls(boolean useSeats) {
    	if (useSeats) {
            System.out.println(
                    polls.textVisualizationBySeats());
        }
        else {
            System.out.println(
                    polls.textVisualizationByVotes()
            );
        }
    }

    /**
     * Helper method that prints the textVisualizationBySeats of the aggregate poll.
     * @param partyNames A String[] that contains the names of the parties in the election
     * @param useSeats Boolean indicating whether to print the aggregate poll visualization
     * by seats or votes
     */
    public void visualizeAggregatePoll(String[] partyNames, boolean useSeats) {
    	Poll aggregatePoll = polls.getAggregatePoll(partyNames);

        // Now we can use the useSeats boolean to get the appropriate visualization
        if (useSeats) {
            displayPollDataBySeat(aggregatePoll);
        } else {
            displayPollDataByVotes(aggregatePoll);
        }
    }

    /**
     * This helper method is called when the user wants to add specific parties
     * and polls (When the user chooses non-random seats).
     * It takes a specific poll as an argument along with a list of party names.
     * It then loops over the partyNames list and prompts the user for expectedNumberOfSeats
     * and expectedPercentOfVotes for that party.
     * The expectedNumberOfSeats and expectedPercentOfVotes are used to initialize
     * a party instance, and then add this party to the poll.
     * This poll (aPoll) is then added to the polls instance variable.
     * @param aPoll A specific instance of the Poll class
     * @param partyNames A String[] that contains the names of the parties in the election
     */
    private void updatePolls(Poll aPoll, String[] partyNames) {
    	Scanner input = new Scanner(System.in);

        for (String partyName: partyNames) {

            System.out.println("How many seats is the " + partyName + " party expected to win?");

            float expectedNumOfSeats = input.nextFloat() ;

            System.out.println("What percentage of votes is the " + partyName + " party expected to win?");

            // User can enter a value between 0 and 100 for expected percent of votes
            // but the party class uses a float between 0 and 1, so we must divide
            // the user's input by 100.
            float expectedPercentOfVotes = input.nextFloat() / 100;

            Party aParty = new Party(partyName, expectedNumOfSeats, expectedPercentOfVotes);

            aPoll.addParty(aParty);
        }

        polls.addPoll(aPoll);
    }

    /**
     * This method prompts the user to input specific data for the polls and parties in the election.
     * @param nPolls The number of polls the user wants to track
     * @param nSeats The number of seats available in the election
     * @param partyNames The names of the parties in the election
     */
    public void promptForPollList(int nPolls, int nSeats, String[] partyNames) {
        Scanner input = new Scanner(System.in);

        polls = new PollList(nPolls, nSeats);

        int nParties = partyNames.length;

        for (int i = 0; i < nPolls; i++) {
            System.out.println("Enter a name for the poll: ");
            String pollName = input.nextLine();

            Poll aPoll = new Poll(pollName, nParties);
            updatePolls(aPoll, partyNames);
        }
    }

    /**
     * This method handles the case where the user wants to create a random set of polls.
     * The method doesn't return anything.
     * It uses the factory class to create a random pollList. This random pollList
     * is used to initialize the instance variable polls.
     * @param nPolls The number of polls the user wants to track.
     * @param nSeats The number of seats available in the election.
     * @param partyNames The names of the parties in the election.
     */
    private void generateRandomPolls(int nPolls, int nSeats, String[] partyNames) {
        polls = new PollList(nPolls, nSeats);
        Factory factory = new Factory(nSeats);
        factory.setPartyNames(partyNames);
        polls = factory.createRandomPollList(nPolls);
    }

    /**
     * This method displays the text visualization of the polls instance toDisplay.
     * It uses the maxStars of the polls variable along with the number of seats
     * available in the election to call the textVisualizationBySeats method of
     * the Poll Class created by Connor.
     * @param toDisplay A specific Poll instance
     */
    public void displayPollDataBySeat(Poll toDisplay) {
    	int numOfSeats = polls.getNumOfSeats();
        int maxStars = polls.MAX_STARS_FOR_VISUALIZATION;

        // Code used from Mohammad's PollList class
        float temp = (float) numOfSeats / maxStars;
        int numOfSeatsPerStar = (int) Math.ceil(temp);

        System.out.println(
                toDisplay.textVisualizationBySeats(maxStars, numOfSeatsPerStar)
        );
    }

    /**
     * This method takes a String[] containing the names of the parties participating in the election.
     * This list is used to call the getAggregatePoll method of the polls instance.
     * @param toDisplay A list of party names.
     */
    public void displayPollsBySeat(String[] toDisplay) {
    	//We can use a helper method to print the non-aggregate visualization.
        visualizePolls(true);

        //And can use the other helper method to print the non-aggregate visualization.
        visualizeAggregatePoll(toDisplay, true);
    }

    /**
     * This method displays the text visualization of the polls instance toDisplay.
     * It uses the maxStars of the polls variable along with the number of seats
     * available in the election to call the textVisualizationBySeats method of
     * the Poll Class created by Connor.
     * @param toDisplay A specific Poll instance
     */
    public void displayPollDataByVotes(Poll toDisplay) {
    	int maxStars = polls.MAX_STARS_FOR_VISUALIZATION;

        // Code used from Mohammad's PollList class
        double percentPerStar = (int) Math.ceil(100.0 / maxStars);

        System.out.println(
                toDisplay.textVisualizationByVotes(maxStars, percentPerStar)
        );
    }

    /**
     * This method takes a list of strings as an argument. This list is used to call the
     * getAggregatePoll method of the polls instance.
     * @param toDisplay A list of party names.
     */
    public void displayPollsByVotes(String[] toDisplay) {
    	//We can use a helper method to print the non-aggregate visualization.
        visualizePolls(false);

        //And can use the other helper method to print the aggregate visualization.
        visualizeAggregatePoll(toDisplay, false);
    }

    public static void main(String[] args) {
        TextApplication test = new TextApplication();
        test.run();
    }
}