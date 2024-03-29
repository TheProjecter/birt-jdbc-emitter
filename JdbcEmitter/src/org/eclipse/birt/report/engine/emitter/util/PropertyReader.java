package org.eclipse.birt.report.engine.emitter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.birt.core.exception.BirtException;

public class PropertyReader
{
	private ConnectionProperties connectionProperties;
	
	public void loadProperties(File propertiesFile)  throws BirtException
	{
		try
		{
			Properties props = new Properties();
			
			props.load(new FileInputStream(propertiesFile));

			String driverName = props.getProperty(EmitterConstants.DRIVER_NAME_PROPERTY);
			String userName = props.getProperty(EmitterConstants.USER_NAME_PROPERTY);
			String url = props.getProperty(EmitterConstants.DB_URL_PROPERTY);
			String encodedPassword = props.getProperty(EmitterConstants.USER_PASSWORD_PROPERTY);
			Boolean appendData = Boolean.parseBoolean(props.getProperty(EmitterConstants.APPEND_DATA));
			String dataTypes = props.getProperty(EmitterConstants.DATA_TYPES);
			
			// Decode the password
			byte[] decoded = Base64.decodeBase64(encodedPassword.getBytes());   
			String decodedPassword = new String(decoded);
			
			//Store properties
			connectionProperties = new ConnectionProperties();
			connectionProperties.setDecodedPassword(decodedPassword);
			connectionProperties.setDriverName(driverName);
			connectionProperties.setUserName(userName);
			connectionProperties.setUrl(url);
			connectionProperties.setAppendData(appendData);
			connectionProperties.setDataTypes(dataTypes);
		} 
		catch (FileNotFoundException e) 
		{
			throw new BirtException(e.getMessage());
		}
		catch (IOException e) 
		{
			throw new BirtException(e.getMessage());
		}
	}
	
	public ConnectionProperties getConnectionProperties()
	{
		return connectionProperties;
	}
}
