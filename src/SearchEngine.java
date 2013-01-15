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

	public void  quitDriver() throws Exception
	{
		driver.quit();
		Date currentDate = new Date();
		System.out.println("Test finished = " + currentDate);
	}

	public void waitRef(String locator)
	{
		driver.findElement(By.partialLinkText(locator)).click();
		long end = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < end) {
			// Browsers which render content (such as Firefox and IE) return "RenderedWebElements"
			WebElement resultsDiv = (WebElement) driver.findElement(By.partialLinkText(locator));

			// If results have been returned, the results are displayed in a drop down.
			if (resultsDiv.isDisplayed()) {
				break;
			}
		}
	}

	protected boolean switchToWindowTitle(String title) {
		String currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (driver.switchTo().window(windowId).getTitle().equals(title)) {
					return true;
				} else {
					driver.switchTo().window(currentWindow);
				}
			}
		}
		return false;
	}

	public boolean checkTextValue(String locator, String text){
		return  driver.findElement(By.xpath(locator)).getAttribute("value").equals(text);
	}

	public void isEmpty()
	{
		assert StringUtils.isBlank(null);
		assert StringUtils.isBlank("");
	}

	/**
	 * Safely click the given button.
	 * This method will not fail over non-deterministic
	 * page load and rendering.
	 */
	public void safeClick( String locator) throws Exception {
		driver.findElement(By.xpath(locator)).click();
	}
	public void safeClickCSS( String locator) throws Exception {
		driver.findElement(By.cssSelector(locator)).click();
	}

	/**
	 * Safely click the given button and wait amount of seconds given in delay prperty.
	 * This method will not fail over non-deterministic
	 * page load and rendering.
	 */
	public void safeClickAndWait(String locator) throws Exception  {
		safeClick(locator);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void clickByName( String locator ) throws Exception {
		driver.findElement(By.name(locator)).click();
	}

	public void safeClickByName( String locator ) throws Exception {
		driver.findElement(By.name(locator)).click();
	}
	public void safeClickByRef( String locator ) throws Exception {
		driver.findElement(By.partialLinkText(locator)).click();
	}
	public void safeClickByRefAndWait( String locator ) throws Exception {
		safeClickByRef(locator);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void safeClickByNameAndWait( String locator ) throws Exception {
		safeClickByName(locator);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void clickById( String locator ) throws Exception {
		driver.findElement(By.id(locator)).click();
	}

	public void safeClickById( String locator ) throws Exception {
		driver.findElement(By.id(locator)).click();
	}

	public void safeClickByIdAndWait( String locator ) throws Exception {
		safeClickById(locator);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
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

	public void safeSelect( String locator , String option ) throws Exception {

		select(locator , option);
	}
	public void safeSelectById( String locator , String option ) throws Exception {
		selectById(locator , option);
	}

	public void safeSelectAndWait( String locator , String option ) throws Exception {
		safeSelect(locator, option);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void safeSelectByIdAndWait( String locator , String option ) throws Exception {
		safeSelectById(locator, option);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void selectById( String locator , String option ) throws Exception {
		Select select = new Select(driver.findElement(By.id(locator)));
		try {
			select.deselectAll();
		} catch(java.lang.UnsupportedOperationException e) {
			System.out.println("You may toggle an element that is either a checkbox or an option in a select");
		}

		select.selectByVisibleText(option);
	}

	protected void check(String location) {
		driver.findElement(By.xpath(location)).isSelected();
	}

	/**
	 * Safely check the given checkbox.
	 * This method will not fail over non-deterministic
	 * page load and rendering.
	 *
	 * @param locator xpath locator
	 */
	protected void safeCheck(String location) {
		driver.findElement(By.xpath(location)).isSelected();
	}

	protected void safeCheckRef(String location) {
		driver.findElement(By.partialLinkText(location)).isSelected();
	}



	/**
	 * Safely check the given checkbox.
	 * This method will not fail over non-deterministic
	 * page load and rendering.
	 *
	 * @param locator xpath locator
	 *
	 * @throws Exception
	 */
	protected void safeCheckAndWait(String location) throws Exception {
		safeCheck(location);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}


	public void submit(String locator, String query) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (query != null)
			element.sendKeys(query);
		element.submit();
	}

	public void safeSubmit(String locator, String query) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (query != null)
			element.sendKeys(query);
		element.submit();
	}

	public void safeSubmitAndWait(String locator, String query) throws Exception {
		safeSubmit(locator, query);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty( "delay" )), TimeUnit.SECONDS);
	}

	public void submitByName(String locator, String query) {
		WebElement element = driver.findElement(By.name(locator));
		if (query != null)
			element.sendKeys(query);
		element.submit();
	}

	public void submitById(String locator, String query) {
		WebElement element = driver.findElement(By.id(locator));
		if (query != null)
			element.sendKeys(query);
		element.submit();
	}

	public void sendKeys(String locator, String text) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(text);
	}

	public void sendKeysByName(String locator, String text) {
		WebElement element = driver.findElement(By.name(locator));
		element.sendKeys(text);
	}

	public void sendKeysById(String locator, String text) {
		WebElement element = driver.findElement(By.id(locator));
		element.sendKeys(text);
	}

	public void safeSendKeys(String locator, String text) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(text);
	}

	public void clear(String locator)
	{
		driver.findElement(By.xpath(locator)).clear();
	}

	public void goForward() {
		driver.navigate().forward();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public void getText(String locator,String text){
		driver.findElement(By.xpath(locator)).getText().contains(text);
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;  // Success!
		} catch (org.openqa.selenium.NoSuchElementException ignored) {
			return false;
		}
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
