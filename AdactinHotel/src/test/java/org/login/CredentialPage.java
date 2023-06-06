package org.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CredentialPage {

   public static WebDriver driver;
   
 @SuppressWarnings("deprecation")
@Test(priority=1)
   public static void Initial() throws InterruptedException, IOException {
	
	WebDriverManager.chromedriver().setup();
	WebDriver driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get("https://adactinhotelapp.com/");	 
	 
	 File f = new File("C:\\Users\\bkarth\\Desktop\\Test.xlsx");
	 
	 FileInputStream stream = new FileInputStream(f);
	 
	 Workbook book = new XSSFWorkbook(stream);
	 
	 org.apache.poi.ss.usermodel.Sheet sheet = book.getSheet("Sheet1");
	 
	int lastRowNum = sheet.getLastRowNum();
	System.out.println(lastRowNum);

	 	for (int i=1;i<=3;i++) {	
		 Row r = sheet.getRow(i);
	 
			 String userName = r.getCell(0).getStringCellValue();
			 String password= r.getCell(1).getStringCellValue();
			 System.out.println("userName:"+userName+"password:"+password);
			 Thread.sleep(5000);
	 driver.findElement(By.id("username")).sendKeys(userName);
	
driver.findElement(By.id("password")).sendKeys(password);
	 
	 driver.findElement(By.id("login")).click();
	 
	 XSSFCell cell = (XSSFCell) sheet.getRow(i).createCell(2);
	 
	 WebElement confirmation = driver.findElement(By.xpath("//td[text()='Welcome to Adactin Group of Hotels']"));
	  if (confirmation.isDisplayed()) {
		  cell.setCellValue("TRUE");
		  WebElement logOut = driver.findElement(By.xpath("//a[text()='Logout']"));
	      logOut.click();
	      
	      WebElement back = driver.findElement(By.xpath("//a[text()='Click here to login again']"));
	      back.click();
	  }
	  else {
		  cell.setCellValue("FAIL");
	  }
	  FileOutputStream outputStream = new FileOutputStream(f);
      book.write(outputStream);
      
      
      Thread.sleep(2000);
	}
 }
 
 

 @Test
 void close()
 {
	 driver.quit();
 }
}
