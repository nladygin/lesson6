import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestYandexMarket {

    private static WebDriver driver;
    private Logger logger = LogManager.getLogger(TestYandexMarket.class);





    @Test
    public void test(){

        WebDriverWait wait = new WebDriverWait(driver,30L);

        driver.get("https://market.yandex.ru/");

        /* menu */
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(), 'Все категории')]")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(), 'Мобильные телефоны')]")))).click();
        logger.info("mobile phones category selected");

        /* show all manufacturer filter */
        String locatorManufacturerFilter = "//input[@name='Поле поиска']";
        driver.findElement(By.xpath("//legend[contains(text(), 'Производитель')]/..//following::a[contains(text(), 'Показать всё')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorManufacturerFilter)));
        logger.info("manufacturer list expanded");

        /* filter samsung */
        driver.findElement(By.xpath(locatorManufacturerFilter)).clear();
        driver.findElement(By.xpath(locatorManufacturerFilter)).sendKeys("samsung");
        driver.findElement(By.cssSelector("label[for='7893318_153061']")).sendKeys(Keys.SPACE);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.n-snippet-cell2"),0));
        logger.info("samsung selected");

        /* filter xiaomi */
        driver.findElement(By.xpath(locatorManufacturerFilter)).clear();
        driver.findElement(By.xpath(locatorManufacturerFilter)).sendKeys("xiaomi");
        driver.findElement(By.cssSelector("label[for='7893318_7701962']")).sendKeys(Keys.SPACE);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.n-snippet-cell2"),0));
        logger.info("xiaomi selected");

        /* sorting */
        driver.findElement(By.xpath("//a[contains(text(), 'по цене')]")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='i-bem n-pager-more n-pager-more_js_inited'][not(@style='display: none;')]")));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath("//div[@class='n-filter-applied-results__content preloadable i-bem preloadable_js_inited']")),"style", "height: auto;"));
        logger.info("result sorted by price");

        /* add to favorites */
        String locatorSearchResult = "//div[contains(@class, 'n-filter-applied-results')]"; //layout__col_search-results_normal
        String locatorSamsung = "a[contains(text(), 'Samsung')]/../../..";
        String locatorXiaomi = "a[contains(text(), 'Xiaomi')]/../../..";
        String locatorAddToFavorite = "i[contains(@class, 'image_name_compare')]";

        /* samsung */
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.xpath(locatorSearchResult + "/.//following::" + locatorSamsung)).get(0))).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//" + locatorAddToFavorite)))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='popup-informer__title'][contains(text(), 'Samsung')]")));
        driver.navigate().back();
        logger.info("samsung added to favorites");

        /* xiaomi */
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.xpath(locatorSearchResult + "/.//following::" + locatorXiaomi)).get(0))).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//" + locatorAddToFavorite)))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='popup-informer__title'][contains(text(), 'Xiaomi')]")));
        logger.info("xiaomi added to favorites");

        /* go to compare */
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/compare?track=rmmbr']/.."))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Сравнение товаров')]")));
        logger.info("go to compare");

        /* check two */
        assertThat(driver.findElements(By.cssSelector("div.n-compare-cell.n-compare-cell_js_inited")).size(), equalTo(2));
        logger.info("two items list checked");

        /* all characteristics */
        driver.findElement(By.xpath("//span[contains(text(), 'все характеристики')]/..")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(text(), 'Общие характеристики')]"))));

        /* check os */
        assertThat(driver.findElement(By.xpath("//div[contains(text(), 'Операционная система')]")).isDisplayed(), equalTo(true));
        logger.info("os is present");

        /* different characteristics */
        driver.findElement(By.xpath("//span[contains(text(), 'различающиеся характеристики')]/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Общие характеристики')]")));

        /* check os */
        assertThat(driver.findElement(By.xpath("//div[contains(text(), 'Операционная система')]")).isDisplayed(), equalTo(false));
        logger.info("os is hide");

    }







    @BeforeClass
    public static void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = WebDriverFactory.create(WebDriverFactory.DriverType.CHROME, options);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void teardown(){
        if (driver != null) {
            driver.quit();
        }
    }

}
