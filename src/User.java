import java.util.ArrayList;

public class User {
	
	private static int nextId = 0;
	
	private int id;
	
	private static final double QUALITY_DEVIATION = 15d; 
	
	//whether the user publishes tweets containing phishing links or not
	private boolean evil;
	
	//scale 1-100: sending tweets 'frequency'
	private int tweetFrequency;

	
	//scale 1-100: how educated is the user of cyber attacks etc. Increases quality of evil tweets
	//Reduces likelyhood of getting infected by evil tweet
	private int education;
	
	//users who will receive tweets send by this user
	private ArrayList<User> followers;
	
	//tweets received by the suer
	private ArrayList<Tweet> receivedTweets;
	
	//constructor
	public User(boolean evil, int tweetFrequency, int education) {
		id = nextId;
		nextId++;
		
		this.evil = evil;
		this.tweetFrequency = tweetFrequency;
		this.education = education;
		followers = new ArrayList<User>();
		receivedTweets = new ArrayList<Tweet>();
	}
	
	public void addFollower(User follower) {
		followers.add(follower);
	}
	
	public void addTweet(Tweet tweet) {
		receivedTweets.add(tweet);
	}
	
	//Read tweets and possible retweet the message
	public void doReadTweets() {
		for(Tweet tweet: receivedTweets) {
			if(tweet.isPhishingTweet()) {
				int defendDice = main.random.nextInt(100) + 1;
				if(defendDice <= tweet.getQuality()) {
					//TODO: GET INFECTED AND RETWEET
				}
			}
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
			Tweet tweet = new Tweet(evil, quality);
			for(User follower: followers) {
				follower.addTweet(tweet);
			}
		}
	}
	
	//returns if the user is evil
	public boolean isEvil() {
		return evil;
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
