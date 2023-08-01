package com.googleSearchAutomation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class googleSearchAutomation {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		FileInputStream file = new FileInputStream(".\\dataFiles\\Excel.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		Calendar calendar = Calendar.getInstance();
		int dayNo = calendar.get(Calendar.DAY_OF_WEEK);
		
		XSSFSheet sheet = workbook.getSheetAt(dayNo);
		
		for(int r=2; r<=sheet.getLastRowNum(); r++)
		{
			int c=2;
			Cell cell=sheet.getRow(r).getCell(c);
			String keyword=cell.getStringCellValue();
			
			System.out.println(keyword);
			
			driver.get("http://www.google.com");
			driver.manage().window().maximize();
			
			Thread.sleep(1000);
			
			driver.findElement(By.name("q")).sendKeys(keyword);
			
			Thread.sleep(3000);
			
			List <WebElement > list = driver.findElements(By.className("wM6W7d"));
			
			//System.out.println(list.size());
			
			int minLength = Integer.MAX_VALUE;
			int maxLength = 0;
			String shortestOption = null;
			String longestOption = null;
			for(int i=0; i<list.size(); i++)
			{
				String listitem=list.get(i).getText();
				
				//System.out.println(listitem +" "+listitem.length());
				
				if((listitem.length()<minLength) && (listitem.length()!=0))
				{
					shortestOption=listitem;
					minLength=listitem.length();
				}
				if(listitem.length()>maxLength)
				{
					longestOption=listitem;
					maxLength=listitem.length();
				}
				
			}
			System.out.println("Shortest Option: "+shortestOption);
			System.out.println("Longest Option: "+longestOption);
			
			Thread.sleep(500);
			
			sheet.getRow(r).createCell(c+1).setCellValue(longestOption);
			sheet.getRow(r).createCell(c+2).setCellValue(shortestOption);
			
			FileOutputStream fos = new FileOutputStream(".\\dataFiles\\Excel.xlsx");
			workbook.write(fos);
			
		}
		workbook.close();
		driver.close();
		
	}

}
