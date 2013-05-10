package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


public class JdbcDriverMySQL extends JdbcDriver {

	protected String driverName() 
	{
		return "MySQL-AB JDBC Driver";
	}
  	@Override protected String columnTypeSQL(String defalt) 
  	{
		if (defalt.equals("CLOB"))
			return "TEXT"; // An unlimited varchar
		else
			return defalt;
	}
}
