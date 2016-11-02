import java.util.Random;


public class main {
	
	static Random random = new Random();
	
	static int steps = 500;
	static int campaign_start_step = 150;
	static double campaign_effectiveness = 200;
	static double campaign_deviation = 50;
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		int userCount = 1000;
		float maliciousUserCount = 0.05f; // percentage of evil users
		double averageFollowerCount = 60d;
		double standardDeviation = 30d;
		
		
		for(int i = 0; i < 10; i++) {
			Network network = new Network(userCount, maliciousUserCount, averageFollowerCount, standardDeviation);
			
			
//			StringBuilder sb = new StringBuilder();
//			network.toJSON(sb);
//			System.out.println(sb.toString());
			
			doTest(network); 
		}

		
		//int infectedCount = network.getInfectedCount();
		
		//System.out.println("Infected usercount: " + infectedCount);
		
	}

	public static void doTest(Network network) {
		for(int step = 0; step < steps; step++) {
			//System.out.println("Step " + step);
			if(step == campaign_start_step) {
				network.doCampaign(campaign_effectiveness, campaign_deviation);
			}
			network.doStep();
			System.out.print( ((float)network.getInfectedCount() / (float)network.userCount) + ",");
		}
		System.out.println();
	}
	
	public static double getNormallyDistributed(double average, double deviation) {
		return random.nextGaussian() * deviation + average;
	}
	
}
