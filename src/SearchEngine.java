import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.OutputType.BYTES;

/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 12/28/12
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */

class SearchEngine {

	private WebDriver driver = null;

	public SearchEngine(Config config) throws Exception {
		init();
	}

	private void init() throws Exception
	{
		String os = System.getProperty("os.name");		// get OS type
		System.out.println("Used OS:		" + os);					// and print it to console
		os = os.toLowerCase();
		String arch = System.getProperty("os.arch");		// get OS type
		System.out.printf("Used OS architecture:\t%s\n%n", arch);					// and print it to console

		driver = WebDriverFactory.createWebDriver();
	}

	public WebDriver getWebDriver() { return driver; }

	/**
	 * Safely click the given button.
	 * This method will not fail over non-deterministic
	 * page load and rendering.
	 */
	public void safeClick( String locator) throws Exception {
		driver.findElement(By.xpath(locator)).click();
	}

	public void select( String locator , String option ) throws Exception {
		Select select = new Select(driver.findElement(By.xpath(locator)));
		try {
			select.deselectAll();
		} catch(java.lang.UnsupportedOperationException e) {
			System.out.println("You may toggle an element that is either a checkbox or an option in a select");
		}

		select.selectByVisibleText(option);
	}

	public void createScreenShot(String toFile) throws Exception {
		byte[] bytes = ((TakesScreenshot)driver).getScreenshotAs(BYTES);

		Date dateNow = new Date ();
		SimpleDateFormat dateformatkkmmssMMDDYYYY = new SimpleDateFormat("kkmmssMMddyyyy");
		StringBuilder nowkkmmssMMDDYYYY = new StringBuilder( dateformatkkmmssMMDDYYYY.format( dateNow ) );

		FileOutputStream fos = new FileOutputStream(toFile+nowkkmmssMMDDYYYY+".png");
		fos.write(bytes);
		fos.close();
	}
}
