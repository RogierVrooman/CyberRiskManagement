import java.util.Random;


public class main {
	
	static Random random = new Random();
	
	static int steps = 500;
	
	public static void main(String[] args) {
		int userCount = 250;
		float maliciousUserCount = 0.05f; // percentage of evil users
		double averageFollowerCount = 60d;
		double standardDeviation = 30d;
		Network network = new Network(userCount, maliciousUserCount, averageFollowerCount, standardDeviation);
		StringBuilder sb = new StringBuilder();
		network.toJSON(sb);
		
		System.out.println(sb.toString());
		
		doTest(network); 
		System.out.println();

		
		int infectedCount = network.getInfectedCount();
		
		System.out.println("Infected usercount: " + infectedCount);
		
	}

	public static void doTest(Network network) {
		for(int step = 0; step < steps; step++) {
			//System.out.println("Step " + step);
			network.doStep();
			System.out.print( ((float)network.getInfectedCount() / (float)network.userCount) + ",");
		}
	}
	
	public static double getNormallyDistributed(double average, double deviation) {
		return random.nextGaussian() * deviation + average;
	}
	
}
