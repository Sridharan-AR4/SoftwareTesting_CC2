package com.example;

import java.io.FileInputStream;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    @BeforeMethod
    public void setup() throws Exception{
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get("https://www.barnesandnoble.com/");
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        js=(JavascriptExecutor) driver;
    }
    @Test(priority = 0)
    public void test1() throws Exception
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("All")))).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Books")))).click();
        Thread.sleep(2000);
        FileInputStream fs=new FileInputStream("D:\\Softwaretesting-XL Files\\Barnes & Noble.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(fs);
        XSSFSheet sheet=wb.getSheet("Sheet1");
        XSSFRow row1=sheet.getRow(1);
        String search=row1.getCell(0).getStringCellValue();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/div[2]/header/nav/div/div[3]/form/div/div[2]/div/input[1]")))).sendKeys(search);
        Thread.sleep(2000);
        WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/div[2]/header/nav/div/div[3]/form/div/div[2]/div[2]/a[1]/span"))));
        String str=element.getText();
        Thread.sleep(2000);
        if(str.contains("chetan bhagat")){
            System.out.println("passed");
        }
        else{
            System.out.println("fail");
        }
    }
    @Test(priority = 1)
    public void test2() throws Exception {
        WebElement audio=wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Audiobooks"))));
        Actions actions=new Actions(driver);
        Thread.sleep(5000);
        actions.moveToElement(audio);
        System.out.println(audio.getText());
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Audiobooks Top 100")))).click();
        Thread.sleep(5000);
        WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Funny Story"))));
        js.executeScript("arguments[0].scrollIntoView();",element);
        Thread.sleep(5000);
        element.click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/div[2]/div[2]/section/div[2]/div/div[3]/div[2]/div[1]/section/div/div/div/div/div[3]/a")))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/div[2]/div[2]/section/div[2]/div/div[3]/div[2]/div[3]/form[1]/input[11]")))).click();
        if(driver.getPageSource().contains("Item Successfully Added To Your Cart")){
            System.out.println("pass");
        }
        else{
            System.out.println("fail");
        }
        Thread.sleep(3000);
    }
    @Test(priority = 2)
    public void test3() throws Exception{
        WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/div[3]/div[3]/div/section/div/div/div/div/div/a[1]"))));
        js.executeScript("arguments[0].scrollIntoView();", element);
        element.click();
        WebElement element2=wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/section/div[1]/div[2]/div/div/div[2]/div/div[73]/div/div[1]/a"))));
        js.executeScript("arguments[0].scrollIntoView()", element2);
        element2.click();
        WebElement element3=wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/div[2]/div/h2"))));
        String str=element3.getText();
        if(str.equalsIgnoreCase("Sign in or Create an Account")){
            System.out.println("passed");
        }
        else{
            System.out.println("fail");
        }

    }

    @AfterMethod
    public void after() throws Exception{
        driver.quit();
    }
}
