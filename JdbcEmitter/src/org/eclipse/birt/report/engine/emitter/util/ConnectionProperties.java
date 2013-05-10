package org.eclipse.birt.report.engine.emitter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionProperties 
{
	private String driverName = null;
	private String userName = null;
	private String url = null;
	private String decodedPassword = null;
	private boolean isAppendData = false;
	private Map<String,List<String>> dataTypes = null;
	
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
	public Map<String,List<String>> getDataTypes() 
	{
		return dataTypes;
	}
	public void setDataTypes(String dataTypes) 
	{
		if(dataTypes == null || dataTypes.trim().length() ==0)
		{
			return;
		}
		this.dataTypes = new HashMap<String,List<String>>();
		String[] tableDataTypes = dataTypes.split(";");
		for(String tableDataType:tableDataTypes)
		{
			String[] dataTypeNames = tableDataType.split(",");
		    List<String> types = new ArrayList<String>();
			for(int i=1;i<dataTypeNames.length;i++)
			{
				types.add(dataTypeNames[i]);
			}
			this.dataTypes.put(dataTypeNames[0], types);
		}
	}
}
