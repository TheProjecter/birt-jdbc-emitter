package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * This contains PostgreSQL specific code.
 * 
 * VARCHAR without size means unlimited. No artificial upper size? TEXT has the
 * same behaviour as VARCHAR, apparantly.
 * 
 */

public class JdbcDriverPostgres extends JdbcDriver
{
	protected String driverName()
	{
		return "PostgreSQL Native Driver";
	}

	/** Never need to specify byteSize for varchar */
	@Override 
	protected String columnTypeSQL(String defalt) 
	{
		if (defalt.startsWith("VARCHAR") )
			return "VARCHAR"; // No max size necessary or useful for Postgres
		else if (defalt.equals("BYTES"))
			return "BYTEA"; // Ie. just a byte array.
		else if (defalt.equals("CLOB"))
			return "TEXT"; // An unlimited varchar
		else
			return defalt;
	}
}
