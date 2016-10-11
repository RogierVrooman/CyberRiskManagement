
public class Tweet {
	
	//tweet contains a phishing link or not
	private boolean phishingTweet;
	
	//scale 1-100: indicator of the 'quality' of the phishing link (if present)
	//if the tweet is not a phishingTweet, the value should be -1
	private int quality;
	
	
	//constructor
	public Tweet(boolean phishingTweet, int quality) {	
		this.phishingTweet = phishingTweet;
		this.quality = quality;
	}
	
	//returns whether or not the tweet is contains a phishing link
	public boolean isPhishingTweet() {
		return phishingTweet;
	}
	
	//returns the quality of the phishing link
	public int getQuality() {
		return quality;
	}
	
}
