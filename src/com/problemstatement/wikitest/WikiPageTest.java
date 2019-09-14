package com.problemstatement.wikitest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class WikiPageTest {
	
	public static WebDriver driver;
	
	@BeforeMethod(description="Lauching the browser")
	
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", 
		"G:/Selenium/Selenium Workspace/Selenium/driver/geckodriver.exe");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("https://en.wikipedia.org/wiki/Selenium");
	}
	
	
	@Test(priority=0, description="Launching the Provided URL")
	
	public void launchUrL()
	{
		String expectedtitle="Selenium - Wikipedia";
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedtitle);
	}
	
	@Test(priority=1,description="Verify External link in External Links Section")
	public void verifyExternalLink()
	{
		String expectedURL="https://en.wikipedia.org/wiki/Selenium#External_links";
		WebElement linkElement=driver.findElement(By.xpath("//span[@class='toctext'][contains(text(),'External links')]"));
		/*JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView", linkElement);	*/
		linkElement.click();
		String actualURL=driver.getCurrentUrl();
		Assert.assertEquals(actualURL, expectedURL);	
	}
	
	@Test(priority=2,description="Clicking on the oxygen Link")
	public void clickOnOxygenLInk()
	{
		String expectedURL="https://en.wikipedia.org/wiki/Oxygen";
		WebElement OxygenLink=driver.findElement(By.xpath("//div[@class='navbox']//tr[3]//td[7]//a[1]//span[1]"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView", OxygenLink);
		OxygenLink.click();
		String actualURL=driver.getCurrentUrl();
		Assert.assertEquals(actualURL, expectedURL);	
	}
	@Test(priority=3,description="Verifying Featured Article or not")
	public void verifyfeaturedArticle()
	{
		WebElement OxygenLink=driver.findElement(By.xpath("//div[@class='navbox']//tr[3]//td[7]//a[1]//span[1]"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView", OxygenLink);
		OxygenLink.click();
		String titleToVerify="This is a featured article. Click here for more information.";
		WebElement featuredArticle=driver.findElement(By.xpath("//div[@id='mw-indicator-featured-star']//a"));
		String tooltipTitle=featuredArticle.getAttribute("title");
		Assert.assertEquals(titleToVerify, tooltipTitle);
		System.out.println("Given Article is a featured Article");	
	}
	
	@Test(priority=4,description="Taking screenshot of a table containing properties")
	public void takeScreenshot() throws IOException
	{
		WebElement OxygenLink=driver.findElement(By.xpath("//div[@class='navbox']//tr[3]//td[7]//a[1]//span[1]"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView", OxygenLink);
		OxygenLink.click();
		WebElement box=driver.findElement(By.xpath("//table[@class='infobox']"));
		File file=box.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(file, new File("G:/Selenium/Selenium Workspace/Selenium/Screenshots/propertytable.png"));
		System.out.println("Screen shot captured successfully");	
	}
	
	@Test(priority=5,description="Checking the number of PDF Links")
	public void numberOdfLInks() 
	{
		WebElement OxygenLink=driver.findElement(By.xpath("//div[@class='navbox']//tr[3]//td[7]//a[1]//span[1]"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView", OxygenLink);
		OxygenLink.click();
		List<WebElement> references=driver.findElements(By.xpath("//div[47]//ol[1]//li"));
		System.out.println(references.size());
		int count=0;
		for(WebElement elements: references )
		{
			String name=elements.getText();
	//		String s1=elements.getAttribute("href");
		//	System.out.println(s1);
		//	System.out.println(name);
			if(name.contains("PDF"))
			{
				count=count+1;
			}			
		}
		
	System.out.println("There are total "+ count+ " PDF links availbale under References");
	}
	
	@Test(priority=6,description="Checking Plutonium under suggestions")
	public void CheckPlutoniumUnderSuggestions() throws InterruptedException
	{
		String expectedSecondSuggestion="Plutonium";
		driver.findElement(By.id("searchInput")).sendKeys("pluto");
		Thread.sleep(2000);
		List<WebElement> suggestions=driver.findElements(By.xpath("//div[@class='suggestions-results']//a"));
		
		String actualSecondSuggestion=suggestions.get(1).getText();
		Assert.assertEquals(actualSecondSuggestion, expectedSecondSuggestion);	
	}
	
	@AfterMethod(description="Closing the browser")
	
	public void tearDown()
	{
		driver.close();
	}
}
