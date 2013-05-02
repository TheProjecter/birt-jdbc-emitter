package org.eclipse.birt.report.engine.emitter.jdbcsession;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.jdbcdrivers.JdbcDriver;
import org.eclipse.birt.report.engine.emitter.util.ConnectionProperties;
import org.eclipse.birt.report.engine.emitter.util.PropertyReader;


public class JdbcSession 
{
	private Connection connection = null;
	private JdbcDriver jdbcDriver = null;
	private PreparedStatement jdbcPreparedStatement = null;
	
	private PropertyReader propertReader = null;
	int batchSize =0;
	
	public JdbcSession(File propertiesFile) throws BirtException
	{
		propertReader = new PropertyReader();
		propertReader.loadProperties(propertiesFile);
	}
	private void openConnection( ConnectionProperties properties) throws BirtException
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
	
	public void start() throws BirtException
	{
		openConnection(propertReader.getConnectionProperties());
	}
	
	public void stop() throws BirtException
	{
		if( jdbcPreparedStatement != null)
		{
			try 
			{
				//Flush everything
				jdbcPreparedStatement.executeBatch();
			} 
			catch (SQLException e) 
			{
				throw new BirtException(e.getMessage());
			}
			finally
			{
				if( jdbcPreparedStatement != null )
				{
					try 
					{
						jdbcPreparedStatement.close();
					}
					catch (SQLException e) 
					{
						throw new BirtException(e.getMessage());
					}
				}
			}
		}
		closeConnection();
	}
	
	  /**
     * Creates a new Statement and executes the query.
     */
    protected boolean executeQuery( String sqlQuery ) throws BirtException 
    {
    	Statement ps = null;
    	boolean isSuccesful = false;
    	try 
    	{
    		ps = connection.createStatement();
			isSuccesful = ps.execute(sqlQuery);
		}
    	catch (Exception rsex)
    	{
			if (ps != null) 
			{
				try 
				{
					ps.close();
				} 
				catch (SQLException e)
				{
					throw new BirtException("Executing " + sqlQuery); // we keep first exception
				}
			}
			throw new BirtException("Executing " + sqlQuery);
		}
    	return isSuccesful;
    }
    
    /** Create a PreparedStatement and sets its parameters
     * @throws BirtException */
    private PreparedStatement prepareStatement( String sqlQuery ) throws BirtException
    {
		try 
		{
			jdbcPreparedStatement = getConnection().prepareStatement(sqlQuery);
		} 
		catch (Exception psex)
		{
			if (jdbcPreparedStatement != null) 
			{
				try 
				{
					jdbcPreparedStatement.close();
				} 
				catch (SQLException e)
				{
					throw new BirtException("Preparing '" + sqlQuery + "'"); // we keep first exception
				}
			}
			throw new BirtException("Preparing '" + sqlQuery + "'");
		}
	
		return jdbcPreparedStatement;
    }
    
    private void addBatch(List<Object> params) throws BirtException
    {
    	try 
		{
    	// Set the parameters
		for (int px = 0; px < params.size(); px++) 
		{
		 jdbcPreparedStatement.setObject(px + 1, params.get(px)); 
		}
		jdbcPreparedStatement.addBatch();
		} 
		catch (Exception se)
		{
			if (jdbcPreparedStatement != null)
			{
				try 
				{
					jdbcPreparedStatement.close();
				}
				catch (SQLException e)
				{
					throw new BirtException(e.getMessage()); 
				}
			}
			throw new BirtException(se.getMessage());
		}
    }
    
    private void getDriver() throws BirtException
    {
    	this.jdbcDriver  =  JdbcDriver.getDriverFactory(this.getConnection());
    }
    
    public boolean createTable(String tableName,boolean isCreateTable, LinkedHashMap<String, String> columnNameTypeMap) throws BirtException
    {
    	if( this.jdbcDriver != null )
    	{
    		//Table has been already created. sanity check
    		return true;
    	}
    	
    	boolean isTableCreationSucessful = false;
		try 
		{
			//Load driver
			getDriver();
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName.toUpperCase(), null);
	    	if (rs.next())
	    	{
	    		if(!propertReader.getConnectionProperties().isAppendData())
    			{
    	      	executeQuery(" drop table "+tableName);
    	      	isTableCreationSucessful = executeQuery(this.jdbcDriver.getCreateTableSql(tableName,columnNameTypeMap));
    			}
	    	}
	    	else
	    	{
	    		isTableCreationSucessful = executeQuery(this.jdbcDriver.getCreateTableSql(tableName,columnNameTypeMap));
	    	}
		} 
		catch (SQLException e1) 
		{
			throw new BirtException(e1.getMessage());
		}
    	return isTableCreationSucessful;
    }
    public void insertRecord(String tableName,LinkedHashMap<String, String> columnNameTypeMap,ArrayList<Object> params) throws BirtException
    {
		if (jdbcPreparedStatement == null)
		{
			jdbcPreparedStatement = prepareStatement(this.jdbcDriver.getInsertQuerySql(tableName, columnNameTypeMap));
		}
		
		if(batchSize == 100)
		{
			try 
			{
				jdbcPreparedStatement.executeBatch();
			} 
			catch (SQLException e)
			{
				throw new BirtException(e.getMessage());
			}
			batchSize =0;
		}
		addBatch(params);
		batchSize++;
    }
}
