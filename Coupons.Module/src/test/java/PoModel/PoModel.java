package PoModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[1]/a/span")
	WebElement accountsMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[1]/ul/li[1]/a/span")
	WebElement resellerSubMenu;
	
	@FindBy(how=How.XPATH,using="//ul[@id='computenext_reseller_adminhtml_tabs']/li[12]/a/span")
	WebElement mpSettings;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[1]/ul/li[2]/a/span")
	WebElement agentSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[1]/ul/li[3]/a/span")
	WebElement customerSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[4]/a/span")
	WebElement salesMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[4]/ul/li[1]/a/span")
	WebElement invoicesSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[4]/ul/li[2]/a/span")
	WebElement ordersSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/a/span")
	WebElement promotionsMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[5]/ul/li/a/span")
	WebElement couponsSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[7]/a/span")
	WebElement modulesMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[7]/ul/li[1]/a/span")
	WebElement admSubMenu;
	
	@FindBy(how=How.XPATH,using="//*[@id='nav']/li[7]/ul/li[3]/a/span")
	WebElement agentSettingsSubMenu;
	//admin elements
	By uname=By.xpath("//*[@id='username']");
	By pWord=By.xpath("//*[@id='login']");
	
	//Marketplace elements
	By mailid=By.id("email");
	By password=By.id("pass");
	@FindBy(how=How.XPATH,using="//a[@id='usr-icn']/span[1]")
	@CacheLookup
	WebElement accountlink;
	//Cachelookup - If we declare any webelemt as cachelookup, that wont be checked everytime
	@FindBy(how=How.XPATH,using="//ul[@id='usr-menu']/li[1]/a")
	WebElement loginlink;
	
	@FindBy(how=How.XPATH,using="//*[@id='catmenu']/ul/li[1]/a")
	WebElement Office365MLLink;
		
    @FindBy(how=How.XPATH,using="//*[@id='toponemenu']/div/a")
    WebElement miniCart;
    
    @FindBy(how=How.XPATH,using="//button[@id='configureworkspace']")
    WebElement configureButton;
    
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
	public void reseller_grid()
	{
		act.moveToElement(accountsMenu).click(resellerSubMenu).build().perform();
	}
	public void mpSettings_verify()
	{
		mpSettings.click();
		String portal_url=driver.findElement(By.xpath("//tr[@id='field-inline-domain']/td[2]/div/div")).getText();
		System.out.println("Reseller Portal URL is "+portal_url+".cloud-marketplace.jp");
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
	public void agent_grid() 
	{
		act.moveToElement(accountsMenu).click(agentSubMenu).build().perform();		
	}
	public void customer_grid() 
	{
		act.moveToElement(accountsMenu).click(customerSubMenu).build().perform();		
	}
	public void invoices_grid()
	{
		act.moveToElement(salesMenu).click(invoicesSubMenu).build().perform();
	}
	public void Orders_grid()
	{
		act.moveToElement(salesMenu).click(ordersSubMenu).build().perform();
	}
	public void Coupons_grid()
	{
		act.moveToElement(promotionsMenu).click(couponsSubMenu).build().perform();
	}
	public void adm_grid()
	{
		act.moveToElement(modulesMenu).click(admSubMenu).build().perform();
	}
	public void agentSettings_grid()
	{
		act.moveToElement(modulesMenu).click(agentSettingsSubMenu).build().perform();
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
	public void product_details()
	{
		act.moveToElement(Office365MLLink).click().build().perform();
		List<WebElement> links=driver.findElements(By.xpath("//div[@id='catalog']/div/div/div[2]/div/div/div/a[2][@class='newproductname']"));
		int totalelements=links.size();
		System.out.println("Total element is "+totalelements);		
		for(int i=0;i<totalelements;i++)
		{
			links=driver.findElements(By.xpath("//div[@id='catalog']/div/div/div[2]/div/div/div/a[2][@class='newproductname']"));
			String name=links.get(i).getText();
			// To go with selected product, use if condition
			if(name.equals("Office 365 Business Essentials with Mail Luck!"))
			{
			links.get(i).click();
			WebElement min=driver.findElement(By.xpath("//*[@id='product-options-wrapper']/div[1]/div/div[1]/span/span"));
		    System.out.println("Min and Max details of "+name+" are "+min.getText());
		    driver.findElement(By.xpath("//*[@id='options_3279_text']")).sendKeys("Test");
		    driver.findElement(By.xpath("//button[@id='addtocart']")).click();
		    break;
			} 
		}
	}
	public void configure_workload()
	{
		act.moveToElement(miniCart).click(configureButton).build().perform();
	}
	public void logout()
	{
		act.moveToElement(accountHeader).click(logOut).build().perform();
	}
}
