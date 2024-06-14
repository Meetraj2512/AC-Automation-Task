package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Driver;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AmazonTask {
    /*
    public static void main(String[] args) {
        System.out.println("hello");
    }
     */
    private WebDriver driver;
    private Properties config;

    @BeforeClass
    public void setUpConfig(){
        config = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            config.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod
    public void Setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openAmazon() throws InterruptedException {
        driver.get(config.getProperty("Base_URI"));
        Assert.assertTrue(driver.getTitle().contains("Amazon"));
        Thread.sleep(500);
        driver.quit();
    }

    private void SearchOperation(String query){
        String Base_URI = config.getProperty("Base_URI");
        driver.get(Base_URI);
        WebElement searchBar = driver.findElement(By.id(config.getProperty("SearchBar_ID")));
        searchBar.sendKeys(query);
        searchBar.submit();

    }
    private void verifyResults(){
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(config.getProperty("WaitCondtion"))));
        List<WebElement> productNames = driver.findElements(By.xpath(config.getProperty("ProductNames")));
        List<WebElement> productPrices = driver.findElements(By.xpath(config.getProperty("ProductPrices")));
        Map<String,String> Products = new HashMap<>();

        for(int i=0 ; i < productNames.size() ; i++){
            String productName = productNames.get(i).getText();
            String productPrice = productPrices.get(i).getText();
            Products.put(productName,productPrice);
        }
        for (Map.Entry<String, String> mp : Products.entrySet()) {
            System.out.println("Product: " + mp.getKey() + " ||| Price: " + mp.getValue());
        }
    }

    @Test(testName = "IPHONE")
    public void searchIphone(){
        System.out.println("************************* IPHONE SEARCH *******************************");
        SearchOperation(config.getProperty("Search_Iphone"));
        verifyResults();
        System.out.println("********************************************************");
    }
    @Test(testName = "SAMSUNG")
    public void searchSamsung(){
        System.out.println("************************* SAMSUNG SEARCH *******************************");
        SearchOperation(config.getProperty("Search_Samsung"));
        verifyResults();
        System.out.println("********************************************************");
    }
    @Test(testName = "ONEPLUS")
    public void searchOnePlus(){
        System.out.println("************************* ONEPLUS SEARCH *******************************");
        SearchOperation(config.getProperty("Search_OnePlus"));
        verifyResults();
        System.out.println("********************************************************");
    }
}
