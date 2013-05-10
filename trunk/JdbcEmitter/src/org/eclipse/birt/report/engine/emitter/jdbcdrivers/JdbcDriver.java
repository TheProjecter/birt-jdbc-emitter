package org.eclipse.birt.report.engine.emitter.jdbcdrivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.core.exception.BirtException;

public abstract class JdbcDriver 
{
	 static JdbcDriver driver = null;
	 private static ArrayList<JdbcDriver> drivers = new ArrayList<JdbcDriver>();
	 
	static 
	{
		JdbcDriver[] ds = new JdbcDriver[] { new JdbcDriverHSQL(),new JdbcDriverOracle(),new JdbcDriverDB2_400(),new JdbcDriverDB2(),
		new JdbcDriverDerby(),new JdbcDriverFirebird(),new JdbcDriverH2(),new JdbcDriverInformix(),new JdbcDriverInterbase(),
		new JdbcDriverMSSQL(),new JdbcDriverMySQL(),new JdbcDriverPostgres(),new JdbcDriverSapDB(),new JdbcDriverSybase()
		};
		for (int dx = 0; dx < ds.length; dx++)
			ds[dx].registerDriver();
	}

 abstract protected String driverName();
 abstract protected String columnTypeSQL(String type);
 public String getCreateTableSql(String name,List<String> columnNames,List<String> dataTypes) throws BirtException
 {
		if (dataTypes == null || dataTypes.size() == 0) 
		{
			throw new BirtException("No datatypes configured for table name: "+ name);
		}
		if (columnNames.size() != dataTypes.size())
		{
			throw new BirtException("Number of columns and number of datatypes are not same.");
		}
		String type = null;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("\nCREATE TABLE " + name);
		sql.append("(");
		for (int i =0;i<columnNames.size();i++) 
		{
			sql.append("\n   ");
			type = dataTypes.get(i);
			sql.append(columnNames.get(i) + "   " + driver.columnTypeSQL(type) );
			sql.append(","); 
		}
		//Remove extra comma
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		return sql.toString();
 }
 public String getInsertQuerySql(String name,List<String> columnNames)
 {
	   StringBuilder sql = new StringBuilder(1000);
	   sql = insertNoValuesSQL(name, columnNames, sql);
	   sql.append("VALUES ( ");
		for (int sx = 0; sx < columnNames.size(); sx++) 
		{
			if (sx > 0)
			{
				sql.append(", ");
			}
			sql.append("?");
		}
		sql.append(")");
		return sql.toString();
 }

 protected StringBuilder insertNoValuesSQL(String tableName,List<String> columnNames, StringBuilder sql) 
 {	
	  sql.append("INSERT INTO ");
	  sql.append(tableName+" ( ");
		for (String columnName : columnNames) 
		{
			sql.append(columnName);
			sql.append(","); 
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" ) ");
		return sql;
	}

public static JdbcDriver getDriverFactory(Connection connection) throws BirtException
{
		String driverName = null;
		try 
		{
			driverName = connection.getMetaData().getDriverName();
			for (int dx = 0; dx < drivers.size(); dx++)
			{
				driver = drivers.get(dx);
				if (driver.driverName().equals(driverName))
				{
					driver = driver.getClass().newInstance();
					break;
				}
			}
		} 
		catch (SQLException e)
		{
			throw new BirtException(e.getMessage());
		}
		catch (InstantiationException e) 
		{
			throw new BirtException(e.getMessage());
		} 
		catch (IllegalAccessException e) 
		{
			throw new BirtException(e.getMessage());
		}
		return driver;
}

public void registerDriver() 
{
	drivers.add(this);
}

}
