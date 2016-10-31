import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class User {
	
	private static int nextId = 0;
	
	private int id;
	
	private static final double QUALITY_DEVIATION = 15d; 
	private static final float INFECT_CHANCE = 0.01f; //0.01f == 1%
	
	//whether the user publishes tweets containing phishing links or not
	private boolean evil;
	
	//scale 1-100: sending tweets 'frequency'
	private int tweetFrequency;

	
	//scale 1-100: how educated is the user of cyber attacks etc. Increases quality of evil tweets
	//Reduces likelyhood of getting infected by evil tweet
	private int education;
	
	//whether the user has been infected by the malware
	private boolean infected;
	
	//users who will receive tweets send by this user
	private ArrayList<User> followers;
	
	//tweets received by the suer
	private Set<Tweet> receivedTweets;
	
	//tweets which already have been red
	private Set<Tweet> readTweets;
	
	//constructor
	public User(boolean evil, int tweetFrequency, int education) {
		id = nextId;
		nextId++;
		
		this.evil = evil;
		this.tweetFrequency = tweetFrequency;
		this.education = education;
		followers = new ArrayList<User>();
		receivedTweets = new HashSet<Tweet>();
		readTweets = new HashSet<Tweet>();
		
		infected = false;
	}
	
	public void addFollower(User follower) {
		followers.add(follower);
	}
	
	public void addTweet(Tweet tweet) {
		if(receivedTweets.contains(tweet) == false && readTweets.contains(tweet) == false)
			receivedTweets.add(tweet);
	}
	
	//Read tweets and possible retweet the message
	public void doReadTweets() {
		for(Iterator<Tweet> iterator = receivedTweets.iterator(); iterator.hasNext();) {
			Tweet tweet = iterator.next();
			if(tweet.isPhishingTweet()) {
				//Do we get infected?
				int defendDice = main.random.nextInt(100) + 1;
				int attackDice = main.random.nextInt(100) + 1;
				if(defendDice * education <= attackDice * tweet.getQuality()
						&& main.random.nextFloat() <= INFECT_CHANCE) {
					infected = true;
					
					//Retweet
					//TODO: Maybe this should happen with a certain probability
					for(User follower: followers) {
						follower.addTweet(tweet);
					}
				}
				
			} else if(evil == false) {
				//Do we get more educated?
				int learnScore = (int) (main.random.nextInt(5) * ((float) tweet.getQuality() / 100f));
				education += learnScore;
				if(education > 100)
					education = 100;
			}
			readTweets.add(tweet);
			iterator.remove();
		}
	}
	
	public void doSendTweets() {
		int tweetFrequencyDice = main.random.nextInt(100) + 1;
		if(tweetFrequencyDice <= tweetFrequency) {
			int quality = (int) Math.round(main.getNormallyDistributed(education, QUALITY_DEVIATION));
			if(quality > 100)
				quality = 100;
			if(quality < 1)
				quality = 1;
			
			Tweet tweet;
			
			if(evil) {
				tweet = new Tweet(true, quality);
			} else if (infected) {
				float r = main.random.nextFloat();
				if(r <= 0.95) {
					tweet = new Tweet(false, quality);
				} else {
					tweet = new Tweet(true, quality);
				}
			} else {
				tweet = new Tweet(false, quality);
			}
			
			for(User follower: followers) {
				follower.addTweet(tweet);
			}
		}
	}
	
	//returns if the user is evil
	public boolean isEvil() {
		return evil;
	}
	
	//returns true if the user has been infected
	public boolean isInfected() {
		return infected;
	}
	
	//returns the tweeting frequency of the user
	public int getFrequency() {
		return tweetFrequency;
	}
	
	//returns the education of the user
	public int getEducation() {
		return education;
	}
	
	public StringBuilder toJSON(StringBuilder sb) {
		sb.append("\"User").append(id)
		.append("\": {\"evil\": ").append(evil)
		.append(", \"frequency\": ").append(tweetFrequency)
		.append(", \"education\": ").append(education)
		.append(", \"followerCount\": ").append(followers.size())
		.append("}");
		return sb;
	}
	
}
