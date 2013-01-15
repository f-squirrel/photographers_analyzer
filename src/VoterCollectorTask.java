import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.*;


/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/7/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoterCollectorTask implements Task {
	private String userAddress = null;
	private SearchEngine searchEngine = null;
	private HashMap<Long, Photographer> votes = new HashMap<Long, Photographer>();
	Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
	Reporter reporter = null;

	public VoterCollectorTask( SearchEngine searchEngine, String userAddress, Reporter reporter ) {
		this.searchEngine = searchEngine;
		this.userAddress = userAddress;
		if (reporter != null)

		logger.info("Started");
		logger.debug(format("userAddress: %s", userAddress));
	}

	public void run() throws Exception {
		goToUserPage();
		try {
			setEnglishLanguage();
			goOverUserPagesByLinks();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if ( !votes.isEmpty() ) {
				reporter.setOutputData(votes);
				reporter.report();
			}
		}
	}

	private void setEnglishLanguage() throws  Exception {
		searchEngine.safeClick("/html/body/div[1]/div/div[1]/table/tbody/tr/td[4]/ul/noindex/li/a[2]");
	}

	private void goOverUserPagesByLinks() throws Exception {
		String page = format("%s/?page=", userAddress);
		for(int i = 0; ; ++i) {
			searchEngine.getWebDriver().get( page + i);
			if ( searchEngine.getWebDriver().findElements(By.xpath("//*[@id=\"success\"]")).size() > 0) {
				logger.debug("Reached last page");
				break;
			}
			logger.debug(format("Parsing page: %s%d", page, i));
			goOverPhotosAtPage();
		}
	}
	private void goOverUserPagesByClicks() throws  Exception {
		int i = 0;
		for(;; ++i) {
			goOverPhotosAtPage();
			List nextElements = searchEngine.getWebDriver().findElements(By.xpath("//*[@id=\"paginator_next\"]/a"));
			if (nextElements.size() == 0)
				break;
			WebElement nextElement = (WebElement)nextElements.get(0);
			nextElement.click();
		}
		logger.debug(format("Pages parsed: %d", i));
	}

	private void goOverPhotosAtPage() throws Exception {
		//WebElement table = searchEngine.getWebDriver().findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr/td[1]/div/div[2]"));//searchEngine.getWebDriver().findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr/td[1]/div/div[2]"));
		//java.util.List rows = table.findElements(By.tagName("tr"));

		List photos = searchEngine.getWebDriver().findElements(By.xpath("//*[@class=\"photo\"]"));
		ArrayList<String> urls = new ArrayList<String>();
		for(Object obj : photos) {
			WebElement photo = (WebElement) obj;
			System.out.println( photo.getText() );
			List photoURLs = photo.findElements(By.xpath("*//a[@href]"));
			// after return all web elements become invalid so we collect them before navigation
			for( Object urlObj : photoURLs) {
				WebElement webPhotoUrl = (WebElement) urlObj;
				urls.add(webPhotoUrl.getAttribute("href"));
			}
		}
		int i = 0;
		for (String url : urls) {
			getVotesForPhoto(url);
			++i;
		}
		logger.debug("Photos parsed: %d%n", i);
	}

	private void goToUserPage() {
		System.out.println(userAddress);
		searchEngine.getWebDriver().navigate().to(userAddress);
	}

	public void getVotesForPhoto(String photoAddress) throws Exception {
		logger.debug("started parsing: " + photoAddress);
		searchEngine.getWebDriver().get(photoAddress);

		searchEngine.safeClick("//*[@id=\"votes_all_view\"]");
		Thread.sleep(1000); // we need to wait until table is loaded

		WebElement table = searchEngine.getWebDriver().findElement(By.xpath("//*[@id=\"votes_all\"]/td/div/table"));
		java.util.List rows = table.findElements(By.tagName("tr"));
		for (Object obj : rows) {
			WebElement we = (WebElement) obj;

			double vote = Double.parseDouble(we.findElement(By.className("vote") ).getText());
			String userAddress = we.findElement(By.xpath("*//a[@style]")).getAttribute("href");
			long userId = Utils.getUserId(userAddress);
			Photographer photographer = votes.get(new Long(userId));
			if ( photographer != null ) {
				photographer.addVote(vote);
			}
			else {
				String userName = we.findElement( By.className("comment-title")).getText();
				Date likeDate =  Utils.parseDate(we.findElement(By.className("date")).getText());
				photographer = new Photographer(userId, userName, userAddress, vote, Photographer.Status.pro, likeDate);
				votes.put(userId, photographer);
			}
			logger.debug(photographer.toString());
		}
	}
}
