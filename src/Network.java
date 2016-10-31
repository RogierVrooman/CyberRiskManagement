import java.util.ArrayList;
import java.util.Random;


public class Network {
	ArrayList<User> users;
	
	int userCount;
	int evilCount;
	double averageFollowerCount;
	double standardDeviation;
	
	//Creates a network with userCount users
	//The first evilCount users will be evil.
	//averageFollowerCount: average amount of followers each user has. This is a probability, not a definite amount
	public Network(int userCount, float evilCount, double averageFollowerCount, double standardDeviation) {
		this.userCount = userCount;
		this.evilCount = (int) Math.round(((float) this.userCount * evilCount));
		this.averageFollowerCount = averageFollowerCount;
		this.standardDeviation = standardDeviation;
		
		Random r = main.random;
		
		users = new ArrayList<User>();
		for(int i = 0; i < userCount; i++) {
			boolean evil;
			int tweetFrequency;
			if(i < evilCount) {
				evil = true;
				tweetFrequency = 100;
			}
			else {
				evil = false;
				tweetFrequency = r.nextInt(100) + 1;
			}			
			int education = r.nextInt(100) + 1;
			User user = new User(evil, tweetFrequency, education);
			users.add(user);
		}
		
		//Add followers to users
		for(User user: users) {
			//Calculate amount of followers
			int followerCount = (int) Math.round(main.getNormallyDistributed(averageFollowerCount, standardDeviation));
			if(followerCount < 0)
				followerCount = 0;
			if(followerCount > userCount)
				followerCount = userCount;
			
			@SuppressWarnings("unchecked")
			ArrayList<User> nonFollowers = (ArrayList<User>) users.clone();
			for(int j = 0; j < followerCount; j++) {
				//Get a random user who isn't following us yet
				int followerIndex = r.nextInt(nonFollowers.size());
				user.addFollower(nonFollowers.remove(followerIndex));
			}
		}
	}
	
	public void doStep() {
		//All users read their tweets from the previous step
		//Note that retweets get send here
		for(User user: users) {
			user.doReadTweets();
		}
		//All users send (non-retweet) tweets
		for(User user: users) {
			user.doSendTweets();
		}
	}
	
	public int getInfectedCount() {
		int sum = 0;
		for(User user: users) {
			if(user.isInfected())
				sum++;
		}
		return sum;
	}
	
	public StringBuilder toJSON(StringBuilder sb) {
		sb.append("{\"Network\": {")
		.append("\"userCount\": ").append(userCount)
		.append(", \"evilCount\": ").append(evilCount)
		.append(", \"averageFollowerCount\": ").append(averageFollowerCount)
		.append(", \"standardDeviation\": ").append(standardDeviation)
		.append(", \"users\": {");
		for(int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			user.toJSON(sb);
			if(i < users.size() -1)
				sb.append(",");
		}
		sb.append("}}}");
		return sb;
	}
	
}
