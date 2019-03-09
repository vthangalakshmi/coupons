package PoModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class propertyReader
{
	Properties pro;
	public propertyReader() throws Exception
	{
		File source=new File("./Config/config.properties");
		FileInputStream fis= new FileInputStream(source);
		pro=new Properties();
		pro.load(fis);
	}
	public String getAdminUrl()
	{
		String admin_Url=pro.getProperty("AdminURL");
		return admin_Url;
	}
	public String getUsername()
	{
		String uName=pro.getProperty("Username");
		return uName;
	}
	public String getPassword()
	{
		String pWord=pro.getProperty("Password");
		return pWord;
	}
	public String getbrowserpath()
	{
		String bPath=pro.getProperty("ChromePath");
		return bPath;
	}
	
	public String getMarketplaceUrl()
	{
		String mp_Url=pro.getProperty("MPURL");
		return mp_Url;
	}
	public String getMarketplaceEmailid()
	{
		String mp_Emailid=pro.getProperty("MPEmailid");
		return mp_Emailid;
	}
	public String getMarketplacePassword()
	{
		String mp_Password=pro.getProperty("MPPassword");
		return mp_Password;
	}



}
