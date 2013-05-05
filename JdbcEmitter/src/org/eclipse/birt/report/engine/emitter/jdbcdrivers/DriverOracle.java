package org.eclipse.birt.report.engine.emitter.jdbcdrivers;

public class DriverOracle extends JdbcDriver
{
	@Override
	protected String driverName()
	{
		return "Oracle JDBC driver";
	}

	protected String columnTypeSQL( String defalt) 
	{
		if (defalt.equals("BYTES"))
			return "RAW (50)" ; 
		else if (defalt.startsWith("TIME"))
			return "TIMESTAMP";
		else if (defalt.startsWith("VARCHAR") ||  defalt.startsWith("LONGVARCHAR"))
			return "VARCHAR2(50)";
		else
			return defalt;
	}
}
