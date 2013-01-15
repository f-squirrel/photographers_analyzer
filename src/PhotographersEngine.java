/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 12/28/12
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */

//package automated_framework;

import java.util.ArrayList;
import org.openqa.selenium.*;

class PhotographersEngine {
	private     SearchEngine testBase = null;
	private     ArrayList<String> urlList;

	public PhotographersEngine(String pathToConfig) throws Exception {
		testBase = new SearchEngine(new Config());

	}

	public void run() throws Exception {
		System.out.println("Engine started!\n\n\n");
		FileReporter filereporter = new FileReporter("result.txt");
		VoterCollectorTask voterCollector = new VoterCollectorTask(testBase, "http://photographers.com.ua/pictures/user/23463", filereporter);
		voterCollector.run();
		testBase.getWebDriver().quit();
	}
}
