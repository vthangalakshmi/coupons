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
    public String Coupon_Code="Auto_Coupon10";
    public String Coupon_Activation_Date="2019/03/20";
    public String Coupon_Expiry_Date="2019/03/25";
    public int Coupon_Amount=1000;
    public int Coupon_Max_Accounts=2;
    public String Coupon_Account="ui";
    public String Coupon_TandC="Test T&C";
    @Test(priority=0,testName="Verify the Admin Login")
	public void adminLogin() throws Exception
	{
		pReader=new propertyReader();
		System.setProperty("webdriver.chrome.driver", pReader.getbrowserpath());
		driver = new ChromeDriver();
		driver.get(pReader.getAdminUrl());
	  	driver.manage().window().maximize();
	    Thread.sleep(100);
	    ap_obj=PageFactory.initElements(driver,PoModel.class);
	    ap_obj.adminLogin(pReader.getUsername(), pReader.getPassword());
	}
    @Test(priority=1,testName="Verify the locale_check")
	public void localeCheck() throws Exception
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
	public void couponHome() throws Exception
	{
		ap_obj.couponsGrid();
		String coupon_home_title=driver.getTitle();	
	    System.out.println(coupon_home_title);
	    driver.findElement(By.xpath("//button[@title='Create New']")).click();
	}
	
	//Testcases for Coupon Code
	
	@Test(priority=3,testName="Verify the mandatory validation of Coupon Code ")
	public void couponCodeMandatoryValidation() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=4,testName="Verify the validation of coupon code if its less than 4 ")
	public void couponCodeLessThanFourValidation() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("abc");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=5,testName="Verify the validation of coupon code if its greater than 36 ")
	public void couponCodeGreaterThan36Validation() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("coupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintestcoupintest");
		Assert.assertEquals(Coupon_validation, "Please set the coupon code between 4 and 36 characters.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=6,testName="Verify the validation of coupon code if first letter is integer ")
	public void couponCodeValidationIfFirstLetterIsInteger() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("1Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}	
	@Test(priority=7,testName="Verify the validation of coupon code if first letter is special characters ")
	public void couponCodeValidationIfFirstLetterIsSpecialCharacters() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("@Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=8,testName="Verify the validation of coupon code if it contains special chars at anyplace")
	public void couponCodeValidationOfSpecialCharacters() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("Test56$%#56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=6,testName="Verify the validation of coupon code if first letter is special characters", enabled=false)
	public void successfulCouponCreation() throws Exception
	{
		Coupon_validation=ap_obj.couponsCodeValidation("@Test56");
		Assert.assertEquals(Coupon_validation, "Please use only letters (a-z or A-Z), numbers (0-9) and underscore(_) in this field. First character should be a letter.");
		System.out.println(Coupon_validation);
	}
	
	//TestCases for Coupon Amount
	
	@Test(priority=9,testName="Verify the mandatory validation of amount if its blank ")
	public void couponAmountMandatoryValidation() throws Exception
	{
		Coupon_validation=ap_obj.couponAmountValidation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=10,testName="Verify the validation of amount if its Alphabets")
	public void couponAmountValidationIfItContainsAlphabets() throws Exception
	{
		Coupon_validation=ap_obj.couponAmountValidation("Test");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=11,testName="Verify the validation of amount if its special characters")
	public void couponAmountValidationIfItsSpecialCharacters() throws Exception
	{
		Coupon_validation=ap_obj.couponAmountValidation("%^&");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=12,testName="Verify the validation of amount if its mixed of numerics,special characters and alphabets")
	public void couponAmountValidationIfItsMixedOfSpecialCharsAndAlphabets() throws Exception
	{
		Coupon_validation=ap_obj.couponAmountValidation("1&*AS");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=13,testName="Verify the validation of amount if its zero")
	public void couponAmountValidationIfItsZero() throws Exception
	{
		Coupon_validation=ap_obj.couponAmountValidation("0");
		Assert.assertEquals(Coupon_validation, "Please enter a number greater than 0 in this field.");
		System.out.println(Coupon_validation);
	}
	
	//TestCases for Max Account
	
	@Test(priority=14,testName="Verify the mandatory validation of max account")
	public void maxAccountMandatoryValidation() throws Exception
	{
		Coupon_validation=ap_obj.couponsMaxAccountsValidation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=15,testName="Verify the max account if it has alphabets")
	public void maxAccountValidationWithAlphabets() throws Exception
	{
		Coupon_validation=ap_obj.couponsMaxAccountsValidation("ERR");
		Assert.assertEquals(Coupon_validation, "Please enter a valid number in this field.");
		System.out.println(Coupon_validation);
	}
	@Test(priority=16,testName="Verify the max account if it has special characters")
	public void maxAccountValidationWithSpecialChars() throws Exception
	{
		Coupon_validation=ap_obj.couponsMaxAccountsValidation("#$-");
		Assert.assertEquals(Coupon_validation, "Please enter a valid number in this field.");
		System.out.println(Coupon_validation);
	}
	
	// Cases for T&C
	
	@Test(priority=17,testName="Verify the mandatory validation of T&C")
	public void termsAndConditionsMandatoryValidation() throws Exception
	{
		Coupon_validation=ap_obj.couponsTermsAndConditionsValidation("");
		Assert.assertEquals(Coupon_validation, "This is a required field.");
		System.out.println(Coupon_validation);
	}
	
	//Cases for Accounts Dropdown wrt Max Accounts
	
	@Test(priority=18,testName="Verify the Accounts Dropdown if account not selected")
	public void accountsDropdownWithoutValue() throws Exception
	{
		Coupon_validation=ap_obj.couponsAccounts("");
		Assert.assertNull(Coupon_validation);
		System.out.println("Max Account's status is "+Coupon_validation);
	}
	@Test(priority=19,testName="Verify the Accounts Dropdown if account selected")
	public void accountsDropdownWithSelectedValue() throws Exception
	{
		Coupon_validation=ap_obj.couponsAccounts("ui");
		Assert.assertNotNull(Coupon_validation);
		System.out.println("Max Account's status is "+Coupon_validation);
	}
	
	//Cases for Coupon Creation
	
	@Test(priority=20,testName="Verify the Coupon Creation without Accounts dropdown")
	public void couponCreationWithoutAccounts() throws Exception
	{
		Coupon_validation=ap_obj.couponCreation(Coupon_Code,Coupon_Activation_Date,Coupon_Expiry_Date,Coupon_Amount,Coupon_Max_Accounts,Coupon_TandC);
		Assert.assertEquals(Coupon_validation, "Saved the coupon");
		System.out.println("Success Message of Coupon Creation is "+Coupon_validation);
	}
	@Test(priority=21,testName="Verify the Coupon Creation With Existing Coupon Code")
	public void couponCreationWithExistingCouponCode() throws Exception
	{
		driver.findElement(By.xpath("//button[@title='Create New']")).click();
		Coupon_validation=ap_obj.couponCreation(Coupon_Code,Coupon_Activation_Date,Coupon_Expiry_Date,Coupon_Amount,Coupon_Max_Accounts,Coupon_TandC);
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
	
