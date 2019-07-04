import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class WebDriverFactory {

    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);


    public static WebDriver create(DriverType driverType, MutableCapabilities options) {
        switch (driverType) {
            case CHROME: {
                if (options instanceof ChromeOptions || options == null) {
                    WebDriverManager.chromedriver().setup();
                    if (options == null) {
                        return new ChromeDriver();
                    } else {
                        return new ChromeDriver((ChromeOptions) options);
                    }
                }
                break;
            }
            case FIREFOX: {
                if (options instanceof FirefoxOptions || options == null) {
                    WebDriverManager.firefoxdriver().setup();
                    if (options == null) {
                        return new FirefoxDriver();
                    } else {
                        return new FirefoxDriver((FirefoxOptions) options);
                    }
                }
                break;
            }
            case IE: {
                if (options instanceof InternetExplorerOptions || options == null) {
                    WebDriverManager.iedriver().setup();
                    if (options == null) {
                        return new InternetExplorerDriver();
                    } else {
                        return new InternetExplorerDriver((InternetExplorerOptions) options);
                    }
                }
                break;
            }
            case EDGE: {
                if (options instanceof EdgeOptions || options == null) {
                    WebDriverManager.edgedriver().setup();
                    if (options == null) {
                        return new EdgeDriver();

                    } else {
                        return new EdgeDriver((EdgeOptions) options);
                    }
                }
                break;
            }
        }
//        logger.error("Driver is not set correctly or not supported!");
        logger.error("Driver is not set correctly or not supported! DriverType: {}, options {}", driverType, options);
        return null;
    }



    public static WebDriver create(DriverType driverType) {
        return create(driverType, null);
    }



    public enum DriverType {
        CHROME,
        FIREFOX,
        IE,
        EDGE
    }
}
