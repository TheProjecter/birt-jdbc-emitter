package org.eclipse.birt.report.engine.emitter.jdbcdrivers;

/**
 * This contains MS SQL Server specific code.
 * 
 * CHAR, VARCHAR max 8,000. TEXT 2^31. NVARCHAR/NCHAR UTF16, max size 4,000.
 */

public class JdbcDriverMSSQL extends JdbcDriver
{

	protected String driverName()
	{
		return "Microsoft JDBC Driver 4.0 for SQL Server";
	}
	/** Apparantly need DATETIME instead of TIMESTAMP */
	@Override protected String columnTypeSQL(String defalt) 
	{
		// TODO review types
		if (defalt.equals("TIMESTAMP"))
			return "DATETIME";
		else if (defalt.equals("DATE"))
			return "DATETIME";
		else if (defalt.equals("TIME") )
			return "DATETIME";
		else if (defalt.equals("BYTES") )
			return "IMAGE"; // Ie. just a byte array.
		else if(defalt.startsWith("LONGVARCHAR"))
		     return "VARCHAR(8000)";
		else if(defalt.startsWith("BLOB") || defalt.startsWith("CLOB"))
		     return "NVARCHAR(4000)";
		else if(defalt.startsWith("DOUBLE"))
			return "REAL";
		else
			return defalt;
	}
}
