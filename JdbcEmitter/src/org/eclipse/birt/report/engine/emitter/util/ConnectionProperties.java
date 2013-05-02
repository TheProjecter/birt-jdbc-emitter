package org.eclipse.birt.report.engine.emitter.util;

public class ConnectionProperties 
{

	private String driverName = null;
	private String userName = null;
	private String url = null;
	private String decodedPassword = null;
	private boolean isAppendData = false;
	
	public boolean isAppendData()
	{
		return isAppendData;
	}
	public void setAppendData(boolean isAppendData) 
	{
		this.isAppendData = isAppendData;
	}
	public String getDriverName() 
	{
		return driverName;
	}
	public void setDriverName(String driverName) 
	{
		this.driverName = driverName;
	}
	public String getUserName() 
	{
		return userName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getDecodedPassword() 
	{
		return decodedPassword;
	}
	public void setDecodedPassword(String decodedPassword) 
	{
		this.decodedPassword = decodedPassword;
	}

}
