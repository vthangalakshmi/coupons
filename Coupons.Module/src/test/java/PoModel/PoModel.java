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


public class PoModel
{
	WebDriver driver;
	Actions act;
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/a/span")
	WebElement promotionsMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/ul/li/a/span")
	WebElement couponsSubMenu;
	
	//admin elements
	By uname=By.xpath("//*[@id='username']");
	By pWord=By.xpath("//*[@id='login']");
	
	//Coupon screen elements
	//By couponnew_button=By.xpath("//button[@title='Create New']");
	By coupon_code_Textbox=By.xpath("//*[@id='couponCode']");
	
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
	public void admin_login(String email,String pword)
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
	public void Coupons_grid()
	{
		act.moveToElement(promotionsMenu).click(couponsSubMenu).build().perform();
	}
	public void mp_login(String email,String pwd) 
	{
		act.moveToElement(accountlink).click(loginlink).build().perform();
		String login_title=driver.getTitle();	
		System.out.println(login_title);
		driver.findElement(mailid).sendKeys(email);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(mailid).submit();
		
	}	
	public void logout()
	{
		act.moveToElement(accountHeader).click(logOut).build().perform();
	}
	public String Coupons_code_validation(String input) throws Exception
	{
		String validation=null;
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		if(input==null||input.isEmpty())
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			//Thread.sleep(2000);
			validation=driver.findElement(By.xpath("//*[@id='advice-required-entry-couponCode']")).getText();
		}
		else if(input.length()>=0 && input.length()<=3)
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-couponcode-length-couponCode']")).getText();
		}
		else if(input.length()>=37)
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-couponcode-length-couponCode']")).getText();
		}
		else if(input.charAt(0)>=0 || input.charAt(0)<=9)
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-code-adm-couponCode']")).getText();
		}
		else if((specialChars.contains(String.valueOf(input.charAt(0)))))
		{
			driver.findElement(coupon_code_Textbox).clear();
			driver.findElement(coupon_code_Textbox).sendKeys(input);
			driver.findElement(coupon_code_Textbox).submit();
			validation=driver.findElement(By.xpath("//*[@id='advice-validate-code-adm-couponCode']")).getText();
		}
		return validation;
	}
}
