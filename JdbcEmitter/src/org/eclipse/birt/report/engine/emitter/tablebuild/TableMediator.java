package org.eclipse.birt.report.engine.emitter.tablebuild;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.eclipse.birt.core.exception.BirtException;

public class TableMediator 
{
	
	Connection connection  = null;
	PreparedStatement jdbcPreparedStatement = null;
	long batchSize = 0;
	
	TableMediator(Connection connection)
	{
		this.connection = connection;
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
    		ps = this.connection.createStatement();
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
					throw new BirtException("Exception while closing statement " + sqlQuery); // we keep first exception
				}
			}
			throw new BirtException("Executing " + sqlQuery);
		}
    	return isSuccesful;
    }
    
    /** Create a PreparedStatement and sets its parameters
     * @throws BirtException */
    protected PreparedStatement prepareStatement( String sqlQuery ) throws BirtException
    {
		try 
		{
			jdbcPreparedStatement = connection.prepareStatement(sqlQuery);
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
    
    protected void addBatch(List<Object> params,TableColumns tblColumns) throws BirtException
    {
    	try 
		{
    		if(batchSize == 1000)
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
    		batchSize++;
    	// Set the parameters
		for (int px = 0; px < params.size(); px++) 
		{
			Object param = params.get(px);
			if(param =="" || param == null)
			{
				jdbcPreparedStatement.setNull(px + 1, tblColumns.columnTypes.get(px+1));
				continue;
			}
		 jdbcPreparedStatement.setObject(px + 1, param); 
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
    
    protected void flush() throws BirtException
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
    }
}
