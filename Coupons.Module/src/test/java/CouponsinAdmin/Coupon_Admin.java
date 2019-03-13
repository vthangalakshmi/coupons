package CouponsinAdmin;

import PoModel.PoModel;
import PoModel.propertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Coupon_Admin 
{
	WebDriver driver;
	propertyReader pReader;
	PoModel ap_obj;
    WebElement createButton;
    Boolean buttonTrue;  
    String Coupon_validation;
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
	@Test(priority=2,testName="Verify the new coupon creation screen ")
	public void coupon_home() throws Exception
	{
		ap_obj.Coupons_grid();
		String coupon_home_title=driver.getTitle();	
	    System.out.println(coupon_home_title);
	    driver.findElement(By.xpath("//button[@title='Create New']")).click();
	}
	@Test(priority=3,testName="Verify the mandatory validation of Coupon Code ")
	public void coupon_code_mandatory_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=4,testName="Verify the validation of coupon code if its less than 4 ")
	public void coupon_code_less_than_four_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("abc");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=5,testName="Verify the validation of coupon code if its greater than 36 ")
	public void coupon_code_greater_than_36_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("coupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintest");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=7,testName="Verify the validation of coupon code if first letter is integer ")
	public void coupon_code_validation_first_letter_is_integer() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("1Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}	
	@Test(priority=6,testName="Verify the validation of coupon code if first letter is special characters ")
	public void coupon_code_validation_first_letter_is_special_characters() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("@Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
}
	
