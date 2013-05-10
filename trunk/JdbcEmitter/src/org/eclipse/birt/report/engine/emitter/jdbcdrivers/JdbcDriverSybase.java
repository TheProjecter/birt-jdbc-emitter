package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * This contains Sybase Adaptive Server specific code.
 */

public class JdbcDriverSybase extends JdbcDriver 
{

	protected String driverName() {
		return "jConnect (TM) for JDBC (TM)";
	}

	/**
	 * Sybase only understands DATETIME (and SMALLDATETIME) and timestamp (must
	 * be lc!)
	 */
	@Override 
	protected String columnTypeSQL(String defalt) 
	{
		if (defalt.equals("TIMESTAMP"))
			return "timestamp";
		if (defalt.equals("DATE"))
			return "datetime";
		if (defalt.equals("TIME") )
			return "datetime";
		else
			return defalt;
	}
}
