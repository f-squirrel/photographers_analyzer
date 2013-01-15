
public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("Hello world!");
			String configPath = null;
			if(args.length >= 1 ) {
				configPath = args[0];
			}
			else {
				configPath = "d:\\projects\\java\\photographers_analyzer\\config\\config.properties";
			}
			PhotographersEngine photoEngine = new PhotographersEngine(configPath);
			photoEngine.run();
		}
		catch (Exception e) {
			System.out.println("Exception:" + e.getMessage() + "\n" + e.getStackTrace().toString());
		}
	}
}
