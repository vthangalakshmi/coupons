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
    public String Coupon_Code="Auto_Coupon4";
    public String Coupon_Activation_Date="2019/03/17";
    public String Coupon_Expiry_Date="2019/03/25";
    public int Coupon_Amount=1000;
    public int Coupon_Max_Accounts=2;
    public String Coupon_Account="ui";
    public String Coupon_TandC="Test T&C";
    @Test(priority=0,testName="Verify the Admin Login")
	public void Admin_login() throws Exception
	{
		pReader=new propertyReader();
		System.setProperty("webdriver.chrome.driver", pReader.getbrowserpath());
		driver = new ChromeDriver();
		driver.get(pReader.getAdminUrl());
	  	driver.manage().window().maximize();
	    Thread.sleep(100);
	    ap_obj=PageFactory.initElements(driver,PoModel.class);
	    ap_obj.admin_login(pReader.getUsername(), pReader.getPassword());
	}
    @Test(priority=1,testName="Verify the locale_check")
	public void Locale_check() throws Exception
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
	public void Coupon_home() throws Exception
	{
		ap_obj.Coupons_grid();
		String coupon_home_title=driver.getTitle();	
	    System.out.println(coupon_home_title);
	    driver.findElement(By.xpath("//button[@title='Create New']")).click();
	}
	
	//Testcases for Coupon Code
	
	@Test(priority=3,testName="Verify the mandatory validation of Coupon Code ")
	public void Coupon_code_mandatory_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=4,testName="Verify the validation of coupon code if its less than 4 ")
	public void Coupon_code_less_than_four_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("abc");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=5,testName="Verify the validation of coupon code if its greater than 36 ")
	public void Coupon_code_greater_than_36_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("coupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintest");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=6,testName="Verify the validation of coupon code if first letter is integer ")
	public void Coupon_code_validation_first_letter_is_integer() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("1Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}	
	@Test(priority=7,testName="Verify the validation of coupon code if first letter is special characters ")
	public void Coupon_code_validation_first_letter_is_special_characters() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("@Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=8,testName="Verify the validation of coupon code if it contains special chars at anyplace")
	public void Coupon_code_validation_of_special_characters() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("Test56$%#56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=6,testName="Verify the validation of coupon code if first letter is special characters", enabled=false)
	public void Successful_coupon_Creation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_code_validation("@Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	
	//TestCases for Coupon Amount
	
	@Test(priority=9,testName="Verify the mandatory validation of amount if its blank ")
	public void Coupon_amount_mandatory_validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_amount_Validation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=10,testName="Verify the validation of amount if its letters")
	public void Coupon_amount_validation_If_its_letters() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_amount_Validation("Test");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=11,testName="Verify the validation of amount if its special characters")
	public void Coupon_amount_validation_If_its_special_characters() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_amount_Validation("%^&");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=12,testName="Verify the validation of amount if its mixed of numerics,special characters and alphabets")
	public void Coupon_amount_validation_If_its_mixed() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_amount_Validation("1&*1");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=13,testName="Verify the validation of amount if its zero")
	public void Coupon_amount_validation_If_its_zero() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_amount_Validation("0");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	
	//TestCases for Max Account
	
	@Test(priority=14,testName="Verify the mandatory validation of max account")
	public void Max_Account_Mandatory_Validation() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_Max_Accounts_validation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=15,testName="Verify the max account if it has alphabets")
	public void Max_Account_Validation_with_Alphabets() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_Max_Accounts_validation("ERR");
		Assert.assertEquals(Coupon_validation, "Please enter a valid number in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=16,testName="Verify the max account if it has special characters")
	public void Max_Account_Validation_with_SpecialChars() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_Max_Accounts_validation("#$-");
		Assert.assertEquals(Coupon_validation, "Please enter a valid number in this field.");
		System.out.println(Coupon_validation);
	}
	
	// Cases for T&C
	
	@Test(priority=17,testName="Verify the mandatory validation of T&C")
	public void Verify_Mandatory_Validation_TermsAndConditions() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_TermsAndConditions_validation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	
	//Cases for Accounts Dropdown wrt Max Accounts
	
	@Test(priority=18,testName="Verify the Accounts Dropdown if account not selected")
	public void Verify_Accounts_Dropdown_With_No_Selected_Value() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_Accounts("");
		Assert.assertNull(Coupon_validation);
		System.out.println("Max Account's status is "+Coupon_validation);
	}
	@Test(priority=19,testName="Verify the Accounts Dropdown if account selected")
	public void Verify_Accounts_Dropdown_With_Selected_Value() throws Exception
	{
		Coupon_validation=ap_obj.Coupons_Accounts("ui");
		Assert.assertNotNull(Coupon_validation);
		System.out.println("Max Account's status is "+Coupon_validation);
	}
	
	//Cases for Coupon Creation
	
	@Test(priority=20,testName="Verify the Coupon Creation without Accounts dropdown")
	public void Coupon_Creation_Without_Accounts() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_Creation(Coupon_Code,Coupon_Activation_Date,Coupon_Expiry_Date,Coupon_Amount,Coupon_Max_Accounts,Coupon_TandC);
		Assert.assertEquals(Coupon_validation, "Saved the coupon");
		System.out.println("Success Message of Coupon Creation is "+Coupon_validation);
	}
	@Test(priority=21,testName="Verify the Coupon Creation With Existing Coupon Code")
	public void Coupon_Creation_With_Existing_Coupon_Code() throws Exception
	{
		driver.findElement(By.xpath("//button[@title='Create New']")).click();
		Coupon_validation=ap_obj.Coupon_Creation(Coupon_Code,Coupon_Activation_Date,Coupon_Expiry_Date,Coupon_Amount,Coupon_Max_Accounts,Coupon_TandC);
		Assert.assertEquals(Coupon_validation, "Coupon Code Already Exists");
		System.out.println("Validation message of existing coupon code is "+Coupon_validation);
	}
	/*@Test(priority=21,testName="Verify the Coupon Creation with Accounts dropdown")
	public void Coupon_Creation_With_Accounts() throws Exception
	{
		Coupon_validation=ap_obj.Coupon_Creation("Coupon Account",Coupon_Activation_Date,Coupon_Expiry_Date,Coupon_Amount,Coupon_Max_Accounts,Coupon_Account,Coupon_TandC);
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}*/
}
	
