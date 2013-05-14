package org.eclipse.birt.report.engine.emitter.jdbcsession;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.jdbcdrivers.DriverShim;
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
	private void openConnection(String driverPath ) throws BirtException
	{
		try 
		{
		    if(driverPath != null && driverPath.trim().length() !=0)
		    {
		    	URL u = new URL(driverPath);
				URLClassLoader ucl = new URLClassLoader(new URL[] { u });
				Driver d = (Driver)Class.forName(properties.getDriverName(), true, ucl).newInstance();
				DriverManager.registerDriver(new DriverShim(d));
		    }
		    else
		    {
			Class.forName(properties.getDriverName());
		    }
			connection = DriverManager.getConnection(properties.getUrl(),properties.getUserName(),properties.getDecodedPassword());
			connection.setAutoCommit(Boolean.FALSE);
		} 
		catch (ClassNotFoundException e) 
		{
			throw new BirtException("Could not load driver "+e.getMessage());
		} 
		catch (SQLException e) 
		{
			throw new BirtException("Could not establish the connection "+e.getMessage());
		} 
		catch (MalformedURLException e) 
		{
			throw new BirtException("Could not establish the connection/MalformedURL "+e.getMessage()+" , driver jar is not accessible "+driverPath);
		} 
		catch (InstantiationException e)
		{
			throw new BirtException("Could not establish the connection/instantiation "+e.getMessage());
		} 
		catch (IllegalAccessException e) 
		{
			throw new BirtException("Could not establish the connection/instantiation "+e.getMessage());
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
	 
	public void start(String driverPath) throws BirtException
	{
		openConnection(driverPath);
		this.jdbcDriver  =  JdbcDriver.getDriverFactory(this.getConnection());
	}
	
	public void stop() throws BirtException
	{
		closeConnection();
	}
}
