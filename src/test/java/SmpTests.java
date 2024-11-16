import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmpTests {
    private static final String SITE_LINK = "http://5.181.254.246:8080";
    private static final String FIELD_USER_NAME = "username";
    private static final String USER_NAME = "User35";
    private static final String FIELD_USER_PASSWORD = "//input[@id='password']";
    private static final String USER_PASSWORD = "Ndidnoei9474()";
    private static final String LOG_IN = "//form[@id='login-form']/div[3]/input";
    private static final String ADD_TO_FAVORITES = "//div[@id='gwt-debug-favorite']";
    private static final String SAVE_TO_FAVORITES = "//div[@id='gwt-debug-buttons']/div";
    private static final String FAVORITES = "//div[6]/div/div[2]/div";
    private static final String CARD_USER_IN_FAVORITES = "gwt-debug-menuItem.uuid:employee$7534-content";
    private static final String DELETE_FROM_FAVORITES = "//td[2]/div/div/div/span";
    private static final String CONFIRM_DELETE_FROM_FAVORITES = "//div[@id='gwt-debug-yes']";
    private static final String HIDE_MENU = "//div[@id='gwt-debug-collapseNavTreeButton']/div";
    private static final String LOG_OUT = "//a[contains(.,'Выйти')]";

    static WebDriver driver;
    static Actions action;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        action = new Actions(driver);
    }

    @Test
    @DisplayName("Проверка функции добавления карточки пользователя в избранное")
    public void testThatUserCardAddedInFavorites() throws InterruptedException {
        logIn();
        click(ADD_TO_FAVORITES);
        sleep(5000);
        click(SAVE_TO_FAVORITES);
        sleep(5000);
        click(FAVORITES);
        sleep(5000);
        assertFalse(driver.findElements(By.id(CARD_USER_IN_FAVORITES)).isEmpty(),
                "Карточка пользователя не была добавлена в \"Избранное\"");
        moveToElement(driver.findElement(By.id(CARD_USER_IN_FAVORITES)));
        click(DELETE_FROM_FAVORITES);
        sleep(5000);
        click(CONFIRM_DELETE_FROM_FAVORITES);
        sleep(5000);
        click(HIDE_MENU);
        click(LOG_OUT);
    }

    @Test
    @DisplayName("Проверка функции удаления карточки пользователя из избранного")
    public void testThatUserCardDeleteFromFavorites() throws InterruptedException {
        logIn();
        click(ADD_TO_FAVORITES);
        sleep(5000);
        click(SAVE_TO_FAVORITES);
        sleep(5000);
        click(FAVORITES);
        moveToElement(driver.findElement(By.id(CARD_USER_IN_FAVORITES)));
        click(DELETE_FROM_FAVORITES);
        sleep(5000);
        click(CONFIRM_DELETE_FROM_FAVORITES);
        sleep(5000);
        assertTrue(driver.findElements(By.id(CARD_USER_IN_FAVORITES)).isEmpty(),
                "Карточка пользователя не была удалена из \"Избранного\"");
        click(HIDE_MENU);
        click(LOG_OUT);
    }

    private void logIn() {
        driver.get(SITE_LINK);
        driver.findElement(By.id(FIELD_USER_NAME)).sendKeys(USER_NAME);
        driver.findElement(By.xpath(FIELD_USER_PASSWORD)).sendKeys(USER_PASSWORD);
        click(LOG_IN);
    }

    private WebElement waitElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    private void click(String xpath) {
        waitElement(xpath).click();
    }

    private void moveToElement(WebElement webElement) {
        action.moveToElement(webElement).perform();
    }

    @AfterAll
    public static void close() {
        driver.close();
    }
}
