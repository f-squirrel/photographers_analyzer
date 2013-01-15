import com.opera.core.systems.OperaDesktopDriver;
import com.opera.core.systems.OperaDriver;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static java.lang.String.*;

/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/11/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebDriverFactory {

	private static final String BROWSER_PROPERTY = "browser";

	static class SupportedBrowsers {
		public static final String IE = "ie";
		public static final String CHROME = "chrome";
		public static final String FIREFOX = "firefox";
		public static final String OPERA = "opera";
		public static final String HTMLUNIT = "htmlunit";
	}

	public static WebDriver createWebDriver() throws Exception {

		String browserType = Config.getProperty(BROWSER_PROPERTY);
		if (browserType.equals(SupportedBrowsers.IE)) {
			return new InternetExplorerDriver();
		}
		if (browserType.equals(SupportedBrowsers.CHROME)) {
			return new ChromeDriver();
		}
		if (browserType.equals(SupportedBrowsers.FIREFOX)) {
			return new FirefoxDriver();
		}
		if (browserType.equals(SupportedBrowsers.OPERA)) {
			return  new OperaDriver();
		}
		if (browserType.equals(SupportedBrowsers.HTMLUNIT)) {
			return new HtmlUnitDriver(true); // enable Javascript
		}
		throw new IllegalArgumentException(format("Unknown type of browser: %s", browserType));
	}

}
