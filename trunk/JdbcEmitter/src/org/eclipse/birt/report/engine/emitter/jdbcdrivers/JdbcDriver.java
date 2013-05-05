package org.eclipse.birt.report.engine.emitter.jdbcdrivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.util.TypeMap;

public abstract class JdbcDriver 
{
	 static JdbcDriver driver = null;
	 private static ArrayList<JdbcDriver> drivers = new ArrayList<JdbcDriver>();
	 
	static 
	{
		JdbcDriver[] ds = new JdbcDriver[] { new DriverHSQL(),new DriverOracle() };
		for (int dx = 0; dx < ds.length; dx++)
			ds[dx].registerDriver();
	}

 abstract protected String driverName();
 abstract protected String columnTypeSQL(String type);
 public String getCreateTableSql(String name,LinkedHashMap<String,String> columnDetails)
 {
		String type = null;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("\nCREATE TABLE " + name);
		sql.append("(");
		for (Entry<String, String> column : columnDetails.entrySet()) 
		{
			sql.append("\n   ");
			type = TypeMap.getJdbcTypeCode(column.getValue());
			sql.append(column.getKey() + "   " + driver.columnTypeSQL(type) );
			sql.append(","); 
		}
		//Remove extra comma
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		return sql.toString();
 }
 public String getInsertQuerySql(String name,LinkedHashMap<String,String> columnDetails)
 {
	   StringBuilder sql = new StringBuilder(1000);
	   sql = insertNoValuesSQL(name, columnDetails, sql);
	   sql.append("VALUES ( ");
		for (int sx = 0; sx < columnDetails.size(); sx++) {
			if (sx > 0)
				sql.append(", ");
			sql.append("?");
		}
		sql.append(")");
		return sql.toString();
 }

 protected StringBuilder insertNoValuesSQL(String tableName,LinkedHashMap<String,String> columnDetails, StringBuilder sql) 
 {	
	  sql.append("INSERT INTO ");
	  sql.append(tableName+" ( ");
		for (String columnName : columnDetails.keySet()) 
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
