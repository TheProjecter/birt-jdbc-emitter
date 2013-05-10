package org.eclipse.birt.report.engine.emitter.jdbcsession;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.jdbcdrivers.JdbcDriver;
import org.eclipse.birt.report.engine.emitter.util.ConnectionProperties;
import org.eclipse.birt.report.engine.emitter.util.PropertyReader;


public class JdbcSession 
{
	private Connection connection = null;
	private JdbcDriver jdbcDriver = null;
	private ConnectionProperties properties;
	
	public JdbcSession(File propertiesFile) throws BirtException
	{
		PropertyReader propertReader = new PropertyReader();
		propertReader.loadProperties(propertiesFile);
		this.properties = propertReader.getConnectionProperties();
	}
	private void openConnection( ) throws BirtException
	{
		try 
		{
			Class.forName(properties.getDriverName());
			connection = DriverManager.getConnection(properties.getUrl(),properties.getUserName(),properties.getDecodedPassword());
		} 
		catch (ClassNotFoundException e) 
		{
			throw new BirtException("Could not load driver "+e.getMessage());
		} 
		catch (SQLException e) 
		{
			throw new BirtException("Could not establish the connection "+e.getMessage());
		}
	}
	
	private void closeConnection() throws BirtException
	{
		if( connection != null )
		{
			try
			{
				connection.commit();
				connection.close();
			} 
			catch (SQLException e)
			{
				throw new BirtException(e.getMessage());
			}
		}
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	
	 public JdbcDriver getDriver() throws BirtException
	 {
	    	return this.jdbcDriver;
	 }
	 
	 public ConnectionProperties getProperties() throws BirtException
	 {
	    	return this.properties;
	 }
	 
	public void start() throws BirtException
	{
		openConnection();
		this.jdbcDriver  =  JdbcDriver.getDriverFactory(this.getConnection());
	}
	
	public void stop() throws BirtException
	{
		closeConnection();
	}
}
