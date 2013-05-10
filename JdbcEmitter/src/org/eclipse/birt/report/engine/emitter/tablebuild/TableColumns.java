package org.eclipse.birt.report.engine.emitter.tablebuild;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.jdbcsession.JdbcSession;

public class TableColumns 
{
    protected List<Integer> columnTypes = new ArrayList<Integer>();
		
   protected void buildTableInfo(String tableName,JdbcSession session,TableMediator tableMediator,List<String> columnNames) throws BirtException
   {
	   try
	   {
	    DatabaseMetaData md = session.getConnection().getMetaData();
		ResultSet rs = md.getColumns(null, null, tableName.toUpperCase(), null);
		int index = 0;
		
		if( !rs.next() )
		{
		 // Table is not there, create one
		 tableMediator.executeQuery(session.getDriver().getCreateTableSql(tableName,columnNames,session.getProperties().getDataTypes().get(tableName)));
		 rs = md.getColumns(null, null, tableName.toUpperCase(), null);
		}
		else
		{
			if( !session.getProperties().isAppendData())
			{
				//Drop table
				tableMediator.executeQuery(" drop table "+tableName);
				tableMediator.executeQuery(session.getDriver().getCreateTableSql(tableName,columnNames,session.getProperties().getDataTypes().get(tableName)));
				rs = md.getColumns(null, null, tableName.toUpperCase(), null);
			}
			else
			{
			int type = rs.getInt(5);
			this.columnTypes.add(index, type);
			}
		}
		
	    while(rs.next())
	    {
	    	int type = rs.getInt(5);
			this.columnTypes.add(index, type);
	    }
	    
	    tableMediator.prepareStatement(session.getDriver().getInsertQuerySql(tableName, columnNames));
	} 
	catch (SQLException e) 
	{
		throw new BirtException(e.getMessage());
	}
   }
}
