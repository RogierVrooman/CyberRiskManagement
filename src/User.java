public class User {
	
	//whether the user publishes tweets containing phishing links or not
	private boolean evil;
	
	//scale 1-100: sending tweets 'frequency'
	private int frequency;

	//scale 1-100: how good is the user with computers etc? Indicator 
	//of the likelihood if the user would click on a wrong link
	private int capability;
	
	//scale 1-100: how aware is the user of cyber attacks etc
	private int aware;
	
	//constructor
	public User(boolean evil, int frequency, int capability, int aware) {
		this.evil = evil;
		this.frequency = frequency;
		this.capability = capability;
		this.aware = aware;
	}
	
	//returns if the user is evil
	public boolean isEvil() {
		return evil;
	}
	
	//returns the tweeting frequency of the user
	public int getFrequency() {
		return frequency;
	}
	
	//returns the capability of the user
	public int getCapability() {
		return capability;
	}
	
	//returns the awereness of the user
	public int getAwereness() {
		return aware;
	}
	
}
