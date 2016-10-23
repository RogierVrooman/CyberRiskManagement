import java.util.Random;


public class main {
	
	static Random random = new Random();
	
	static int steps = 100;
	
	public static void main(String[] args) {
		Network network = new Network(1000, 5, 40d, 10d);
		StringBuilder sb = new StringBuilder();
		network.toJSON(sb);
		System.out.println(sb.toString());
		
		doTest(network); 
		
	}

	public static void doTest(Network network) {
		for(int step = 0; step < steps; step++) {
			network.doStep();
		}
	}
	
	public static double getNormallyDistributed(double average, double deviation) {
		return random.nextGaussian() * deviation + average;
	}
	
}
