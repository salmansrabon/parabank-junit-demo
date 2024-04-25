import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunitTest {
    WebDriver driver;
    @BeforeAll
    public void Setup(){
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--headed");
        driver=new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }
    @AfterAll
    public void closeDriver(){
        driver.quit();
    }
    @Test
    public void runAllTest() throws InterruptedException {
        a_signup();
        //b_login();
        c_openNewAccount();
        d_getTransactionId();
        e_findTransaction();
    }
    @DisplayName("Sign up")
    @Test
    public void a_signup(){
        driver.findElement(By.partialLinkText("Register")).click();
        List<WebElement> textBox= driver.findElements(By.className("input"));
        textBox.get(2).sendKeys("Test");
        textBox.get(3).sendKeys("User");
        textBox.get(4).sendKeys("Gulshan");
        textBox.get(5).sendKeys("Dhaka");
        textBox.get(6).sendKeys("Dhaka");
        textBox.get(7).sendKeys("1212");
        textBox.get(8).sendKeys("01504454111");
        textBox.get(9).sendKeys("1234");
        textBox.get(10).sendKeys("testuser2");
        textBox.get(11).sendKeys("1234");
        textBox.get(12).sendKeys("1234");
        driver.findElement(By.cssSelector("[value='Register']")).click();

    }
//    @DisplayName("User Login")
//    @Test
    public void b_login() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("testuser5");
        driver.findElement(By.name("password")).sendKeys("1234");
        driver.findElement(By.cssSelector("[value='Log In']")).click();
    }
    @DisplayName("Open new account")
    @Test
    public void c_openNewAccount() throws InterruptedException {
        driver.findElement(By.partialLinkText("Open New Account")).click();
        Select select=new Select(driver.findElement(By.id("type")));
        select.selectByValue("1");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        String newAccountId= driver.findElement(By.id("newAccountId")).getText();
        System.out.println(newAccountId);
        driver.findElement(By.id("newAccountId")).click();


    }
    String trnxId;
    @DisplayName("Get transaction Id")
    @Test
    public void d_getTransactionId() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Funds Transfer Received')]"));
        element.click();
        String html= driver.getPageSource();
        int startIndex= html.indexOf("Transaction ID:");
        String str= html.substring(startIndex,startIndex+50);
        trnxId=str.replaceAll("[^0-9]", "");
        System.out.println(trnxId);
    }
    @DisplayName("Find Transaction")
    @Test
    public void e_findTransaction() throws InterruptedException {
        driver.findElement(By.partialLinkText("Find Transactions")).click();
        Thread.sleep(1000);
        driver.findElements(By.tagName("input")).get(0).sendKeys(trnxId);
        driver.findElements(By.cssSelector("[type=submit]")).get(0).click();

        String trnxDate= driver.findElements(By.tagName("td")).get(0).getText();
        System.out.println(trnxDate);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd-yyyy");
        String todate= simpleDateFormat.format(new Date());

        Assertions.assertEquals(trnxDate,todate);
    }
}
