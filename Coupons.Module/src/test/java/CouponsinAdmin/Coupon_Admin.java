package CouponsinAdmin;

import PoModel.PoModel;
import PoModel.propertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
//import org.testng.Assert;
import org.testng.annotations.Test;

public class Coupon_Admin 
{
	WebDriver driver;
	propertyReader pReader;
	PoModel ap_obj;
    WebElement createButton;
    Boolean buttonTrue;  
    @Test(priority=0,testName="Verify the Admin Login")
	public void admin_login() throws Exception
	{
		pReader=new propertyReader();
		System.setProperty("webdriver.chrome.driver", pReader.getbrowserpath());
		driver = new ChromeDriver();
		driver.get(pReader.getAdminUrl());
	  	driver.manage().window().maximize();
	    Thread.sleep(100);
	    ap_obj=PageFactory.initElements(driver,PoModel.class);
	    ap_obj.admin_login(pReader.getUsername(), pReader.getPassword());
	    /*WebElement locale=driver.findElement(By.id("interface_locale"));
	    Select english=new Select(locale);
	    english.selectByValue("en_US");
	    String title=driver.getTitle();	
	    System.out.println(title);*/ 
	}
    @Test(priority=1,testName="Verify the locale_check")
	public void locale_check() throws Exception
	{
		WebElement locale=driver.findElement(By.id("interface_locale"));
		Boolean default_locale=locale.equals("ja_jp");
		if(!default_locale)
		{
		  Select change_locale=new Select(locale);
		  change_locale.selectByValue("en_US");
	      System.out.println("The default locale is Japanese and its changed to English now");
		}
		else
		{
		  System.out.println("The default locale is english");
		}
	    String title=driver.getTitle();	
	    System.out.println(title); 
	}
	@Test(priority=2,testName="Verify the Coupon Landing ")
	public void coupon_home() throws Exception
	{
		ap_obj.Coupons_grid();
		String coupon_title=driver.getTitle();	
	    System.out.println(coupon_title); 
	}
	
}
	
