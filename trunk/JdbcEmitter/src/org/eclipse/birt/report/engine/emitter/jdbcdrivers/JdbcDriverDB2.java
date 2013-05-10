package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * Contains DB2/400 (iSeries DB2/Implementation tested on OS400 v5.1)
 * 
 * CHAR/VARCHAR max 255 else LONG VARCHAR. CHARS are auto trimed on retrieval. ''
 * is not null (unlike ORACLE).
 * 
 * @author Eric Merritt
 */
public class JdbcDriverDB2 extends JdbcDriver 
{

	protected String driverName() 
	{
		return "IBM DB2 JDBC Universal Driver Architecture"; // new type 4
		// return "IBM DB2 JDBC 2.0 Type 2"; // old type 2
	}

	@Override
	protected String columnTypeSQL(String type)
	{
		return type;
	}
}
