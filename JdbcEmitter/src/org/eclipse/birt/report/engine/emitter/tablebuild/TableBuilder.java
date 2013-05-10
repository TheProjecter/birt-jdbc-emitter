package org.eclipse.birt.report.engine.emitter.tablebuild;

import java.util.List;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.jdbcsession.JdbcSession;

public class TableBuilder 
{
    protected JdbcSession session = null;
    protected TableMediator mediator = null;
    protected TableColumns tableColumns = null;
    
	public TableBuilder(JdbcSession session)
	{
		this.session = session;
		mediator = new TableMediator(session.getConnection());
	}
	
	public void buildTableInfo(String tableName,List<String>  columnNames) throws BirtException
	{
		tableColumns = new TableColumns();
		tableColumns.buildTableInfo(tableName, this.session,this.mediator,columnNames);
	}
	
	public void addRecord(List<Object> params) throws BirtException
	{
		mediator.addBatch(params, tableColumns);
	}
	
	public void flush() throws BirtException
	{
		mediator.flush();
	}
}
