package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FreshService {
    private WebDriver driver;
    private Properties config;

    @BeforeClass
    public void setUpConfig() {
        config = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            config.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod
    public void Setup() {
        System.setProperty("webdriver.chrome.driver" , "D:\\webdriver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test()
    public void FSAddRequester() throws InterruptedException {
        System.out.println("*************************start***********************************");
        String Base_URI = "https://armorcode.freshservice.com/";
        driver.get(Base_URI);
        Assert.assertTrue(driver.getTitle().contains("Support"));
        WebElement loginLink = driver.findElement(By.linkText("Login"));
        loginLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys("meetraj.desai@armorcode.io");
        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys("M@eetraj052512");
        WebElement submit = driver.findElement(By.xpath("//button[@type = \"submit\"]"));
        submit.click();
        Thread.sleep(3000);
        for (int i=1 ; i<200 ; i++){
            WebElement kebab = driver.findElement(By.xpath("//a[@title = \"more\"]"));
            kebab.click();
            Wait<WebDriver> wait = new WebDriverWait(driver,Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#moreMenu")));
            WebElement admin = driver.findElement(By.xpath("//a[@title = \"Admin\"]"));
            admin.click();
            WebElement req = driver.findElement(By.xpath("//div[@class = \"admin even user \"]//ul//li[5]//a"));
            req.click();
            WebElement newreq = driver.findElement(By.linkText("New Requester"));
            newreq.click();
            WebElement emailadd = driver.findElement(By.id("user_email"));
            emailadd.sendKeys(i + "@gmail.com");
            Thread.sleep(200);
            WebElement fname = driver.findElement(By.id("user_first_name"));
            fname.sendKeys(i + "first");
            Thread.sleep(200);
            WebElement lname = driver.findElement(By.id("user_last_name"));
            lname.sendKeys(i + "last");
            Thread.sleep(200);
            WebElement save = driver.findElement(By.id("user_submit"));
            save.click();
            driver.get("https://armorcode.freshservice.com/a/dashboard/default");
        }
        System.out.println("**************************close**********************************");
        driver.close();
    }


}
