package com.cogniance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.*;


public class TestHomePage {
	
	protected WebDriver driver;
	
	@Before
	public void startup(){
		this.driver = new HtmlUnitDriver();
		driver.get("http://www.cogniance.com");
	}
	
	@After
	public void teardown() {
		driver.quit();		
	}

	@Test
	public void testHomePageLoaded(){
		WebElement Home = driver.findElement(By.cssSelector(".home"));
		Home.click();
		Assert.assertNotNull(driver.getPageSource());		
	}
	
	@Test
	public void testCompanyPageLoaded(){
		WebElement Company = driver.findElement(By.cssSelector(".company"));
		Company.click();
		Assert.assertEquals(driver.getPageSource(), "");		
	}
	
	@Test
	public void testNewsPageLoaded(){
		WebElement News = driver.findElement(By.cssSelector(".home"));
		News.click();
		Assert.assertNotNull(driver.getPageSource());		
	}
}
