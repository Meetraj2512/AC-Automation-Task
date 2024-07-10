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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;

import java.time.Duration;
import java.util.*;

public class AmazonTask {
    /*
    public static void main(String[] args) {
        System.out.println("hello");
    }
     */
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
    @Test(dataProvider = "query_data")
    public void SearchOperation(String query) {
        System.out.println("************************************************************");
        String Base_URI = config.getProperty("Base_URI");
        driver.get(Base_URI);
        Assert.assertTrue(driver.getTitle().contains("Amazon"));
        WebElement searchBar = driver.findElement(By.id(config.getProperty("SearchBar_ID")));
        searchBar.sendKeys(query);
        searchBar.submit();
        verifyResults();
        System.out.println("************************************************************");
    }

    private void verifyResults() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(config.getProperty("WaitCondtion"))));

        List<WebElement> parentElements = driver.findElements(By.xpath(config.getProperty("ParentElement")));
        LinkedHashMap<String, String> Products = new LinkedHashMap<>();
        //System.out.println(parentElements.size());
        //int cnt = 0;
        for (WebElement ele : parentElements) {
            //cnt++;
            String productName = ele.findElement(By.xpath(config.getProperty("Name"))).getText();
            try {
                String productPrice = ele.findElement(By.xpath(config.getProperty("Price"))).getText();
                Products.put(productName, productPrice);
            } catch (NoSuchElementException ex) {
                Products.put(productName, "N/A");
            }

        }
        for (Map.Entry<String, String> mp : Products.entrySet()) {
            System.out.println("\nProduct: " + mp.getKey() + "\nPrice: " + mp.getValue());
        }
        driver.close();
    }

    @DataProvider(name = "query_data")
    public Object[][] queryDataProvider() {
        return new Object[][]{{config.getProperty("Search_Iphone")}, {config.getProperty("Search_Samsung")}, {config.getProperty("Search_OnePlus")}};
    }
}
