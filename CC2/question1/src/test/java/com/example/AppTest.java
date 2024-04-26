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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    ExtentReports reports;
    ExtentTest test;
    @BeforeTest
    public void report(){
        ExtentSparkReporter spark=new ExtentSparkReporter("C:\\Users\\asus\\Desktop\\CC2\\question1\\src\\reports\\report1.html");
        reports=new ExtentReports();
        reports.attachReporter(spark);
    }
    @BeforeMethod
    public void setup() throws Exception{
        test=reports.createTest("test started");
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
            test.log(Status.PASS, "chetan bhagat text is present in the serachbox results");
            System.out.println("passed");
        }
        else{
            test.log(Status.FAIL, "chetan bhagat text is not present in the searchbox results");    
            System.out.println("fail");
        }
    }
    @Test(priority = 1)
    public void test2() throws Exception {
        WebElement audio=wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/div[2]/header/nav/div/div[4]/div/ul/li[5]/a"))));
        Actions actions=new Actions(driver);
        Thread.sleep(3000);
        actions.moveToElement(audio).build().perform();;
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Audiobooks Top 100")))).click();
        Thread.sleep(3000);
        WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Funny Story"))));
        js.executeScript("arguments[0].scrollIntoView();",element);
        Thread.sleep(3000);
        element.click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/div[2]/div[2]/section/div[2]/div/div[3]/div[2]/div[1]/section/div/div/div/div/div[3]/a/p")))).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html/body/main/div[2]/div[2]/section/div[2]/div/div[3]/div[2]/div[3]/form[1]/input[11]")))).click();
        if(driver.getPageSource().contains("Item Successfully Added To Your Cart")){
            test.log(Status.PASS, "add to cart text is present in the page");
            System.out.println("pass");
        }
        else{
            test.log(Status.FAIL, "add to cart not text is present in the page");
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
        Thread.sleep(5000);
        WebElement frame=driver.findElement((By.xpath("/html/body/div[7]/div/iframe")));
        driver.switchTo().frame(frame);
        WebElement element3=driver.findElement((By.xpath("/html/body/div[2]/div/h2")));
        String str=element3.getText();
        if(str.equalsIgnoreCase("Sign in or Create an Account")){
            test.log(Status.PASS, "Sign in or create account is present in the dialog box");
            System.out.println("passed");
        }
        else{
            test.log(Status.FAIL, "Sign in or create account is not present in the dialog box");
            System.out.println("fail");
        }

    }

    @AfterMethod
    public void after() throws Exception{
        driver.quit();
    }
    @AfterTest
    public void quit() throws Exception{
        reports.flush();
    }
}
