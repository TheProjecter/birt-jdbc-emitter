package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * Contains DB2/400 (iSeries DB2/Implementation tested on OS400 v5.1)
 */
public class JdbcDriverDB2_400 extends JdbcDriverDB2 
{
	protected String driverName() 
	{
		return "AS/400 Toolbox for Java JDBC Driver";
	}

}
