package PoModel;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class PoModel
{
	WebDriver driver;
	Actions act;
	private String validation;
	String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/a/span")
	WebElement promotionsMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/ul/li/a/span")
	WebElement couponsSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='accountname']")
	WebElement couponsAccountAttribute;
	
	@FindBy(how=How.XPATH,using="//*[@id='maxUser']")
	WebElement couponsMaxAcc;
	
	//admin elements
	By uname=By.xpath("//*[@id='username']");
	By pWord=By.xpath("//*[@id='login']");
	
	//Coupon screen elements
	//By couponnew_button=By.xpath("//button[@title='Create New']");
	By coupon_code_Textbox=By.xpath("//*[@id='couponCode']");
	By coupon_activation_date=By.xpath("//*[@id='activateDate']");
	By coupon_expiry_date=By.xpath("//*[@id='expiryDate']");
	By coupon_amount=By.xpath("//*[@id='amount']");
	By coupon_max_accounts=By.xpath("//*[@id='maxUser']");
	//By coupon_accountName=By.xpath("//*[@id='accountname']");
	By coupon_TandC=By.xpath("//*[@id='termsAndConditions']");
	
	//Marketplace elements
	By mailid=By.id("email");
	By password=By.id("pass");
	@FindBy(how=How.XPATH,using="//a[@id='usr-icn']/span[1]")
	@CacheLookup
	WebElement accountlink;
	//Cachelookup - If we declare any webelemt as cachelookup, that wont be checked everytime
	@FindBy(how=How.XPATH,using="//ul[@id='usr-menu']/li[1]/a")
	WebElement loginlink;
	
	@FindBy(how=How.XPATH,using="//a[@id='usr-icn']/span[1]")
    WebElement accountHeader;
    
    @FindBy(how=How.XPATH,using="//ul[@id='usr-menu']/li[5]/a")
    WebElement logOut;
	
	public PoModel(WebDriver driver)
	{
		this.driver=driver;
		act=new Actions(driver);
	}
	public void adminLogin(String email,String pword)
	{
		driver.findElement(uname).sendKeys(email);
		driver.findElement(pWord).sendKeys(pword);
		driver.findElement(pWord).submit();
	}
	public static String getScreenshot(WebDriver driver,String screenshotname)
	{
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path=System.getProperty("user.dir")+"/Results/"+screenshotname+".png";
		File destination=new File(path);
		
		try 
		{
			FileUtils.copyFile(src, destination);
		} catch (IOException e) 
		{
			System.out.println("Capture Failed "+e.getMessage());
		}
		
		return path;
	}
	public void couponsGrid()
	{
		act.moveToElement(promotionsMenu).click(couponsSubMenu).build().perform();
	}
	public void mpLogin(String email,String pwd) 
	{
		act.moveToElement(accountlink).click(loginlink).build().perform();
		String login_title=driver.getTitle();	
		System.out.println(login_title);
		driver.findElement(mailid).sendKeys(email);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(mailid).submit();
		
	}	
	public void logOut()
	{
		act.moveToElement(accountHeader).click(logOut).build().perform();
	}
	public String couponsCodeValidation(String input) throws Exception
	{
		if(input==null||input.isEmpty())
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-required-entry-couponCode']")).getText();
		}
		else if((input.length()>=37) || (input.length()<=3))
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-couponcode-length-couponCode']")).getText();
		}
		else if((input.charAt(0)>=0) || (input.charAt(0)<=9) || (specialChars.contains(String.valueOf(input.charAt(0)))))
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-code-adm-couponCode']")).getText();
		}
		else if(input.contains(specialChars))
		{
			driver.findElement(coupon_max_accounts).clear();
			driver.findElement(coupon_max_accounts).sendKeys(input);
			driver.findElement(coupon_max_accounts).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-code-adm-couponCode']")).getText();
		}
		return validation;
	}
	public String couponAmountValidation(String input) throws Exception
	{
		if(input==null||input.isEmpty())
		{
           	driver.findElement(coupon_amount).clear();
			driver.findElement(coupon_amount).sendKeys(input);
			driver.findElement(coupon_amount).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-required-entry-amount']")).getText();
		}
		else if((input.charAt(0)>=0) && input.matches(".*[a-zA-Z]+.*"))
		{
            driver.findElement(coupon_amount).clear();
			driver.findElement(coupon_amount).sendKeys(input);
			driver.findElement(coupon_amount).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-number-price-amount']")).getText();
		}
		/*else if(input.contains(specialChars))
		{
        	driver.findElement(coupon_amount).clear();
			driver.findElement(coupon_amount).sendKeys(input);
			driver.findElement(coupon_amount).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-greater-than-zero-amount']")).getText();
		}*/
		else if((input.contains(specialChars)) || (input.matches(".*[a-zA-Z]+.*")))
		{
        	driver.findElement(coupon_amount).clear();
			driver.findElement(coupon_amount).sendKeys(input);
			driver.findElement(coupon_amount).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-greater-than-zero-amount']")).getText();
		}
		else if(input.equals("0"))
		{
			driver.findElement(coupon_amount).clear();
			driver.findElement(coupon_amount).sendKeys(input);
			driver.findElement(coupon_amount).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-greater-than-zero-amount']")).getText();
		}
		
		return validation;
	}
	public String couponsMaxAccountsValidation(String input) throws Exception
	{
		if(input==null||input.isEmpty())
		{
			driver.findElement(coupon_max_accounts).clear();
			driver.findElement(coupon_max_accounts).sendKeys(input);
			driver.findElement(coupon_max_accounts).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-required-entry-maxUser']")).getText();
		}
		else if(input.contains(specialChars) || input.matches(".*[a-zA-Z]+.*"))
		{
			driver.findElement(coupon_max_accounts).clear();
			driver.findElement(coupon_max_accounts).sendKeys(input);
			driver.findElement(coupon_max_accounts).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-number-maxUser']")).getText();
		}
		return validation;
	}
	public String couponsTermsAndConditionsValidation(String input) throws Exception
	{
		if(input==null||input.isEmpty())
		{
			driver.findElement(coupon_TandC).clear();
			driver.findElement(coupon_TandC).sendKeys(input);
			driver.findElement(coupon_TandC).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-required-entry-termsAndConditions']")).getText();
		}
		return validation;
	}
	public String couponsAccounts(String input) throws Exception
	{
		Select sel=new Select(couponsAccountAttribute);
		sel.selectByVisibleText(input);
		String validation=couponsMaxAcc.getAttribute("readonly");		
		return validation;
	}	
	public String couponCreation(String CC,String CAD,String CED,int CA,int CMA,String TC) throws Exception
	{
		driver.navigate().refresh();
		driver.findElement(coupon_code_Textbox).sendKeys(CC);
		driver.findElement(coupon_activation_date).sendKeys(CAD);
		driver.findElement(coupon_expiry_date).sendKeys(CED);
		driver.findElement(coupon_amount).sendKeys(CA+"");
		couponsMaxAcc.sendKeys(CMA+"");
		driver.findElement(coupon_TandC).sendKeys(TC);
		driver.findElement(coupon_TandC).submit();
		Thread.sleep(3000);
		validation=driver.findElement(By.xpath("//*[@id='messages']/ul/li/ul/li/span")).getText();
		return validation;
	}
	public String couponCreation(String CC,String CAD,String CED,int CA,int CMA,String CAcc,String TC)
	{
		
		return validation;
	}
}
