import java.util.Random;


public class ProcessGenerator {
	private static int counter = 1;
	private static Random random = new Random();
	
	public Process generateProcess(){
		int instructions = 10 + random.nextInt(41);
		Process newProcess = new Process(counter, instructions);
		counter++;
		return newProcess;
	}
}