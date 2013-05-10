package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


public class JdbcDriverPostgres extends JdbcDriver
{
	protected String driverName()
	{
		return "PostgreSQL Native Driver";
	}
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
