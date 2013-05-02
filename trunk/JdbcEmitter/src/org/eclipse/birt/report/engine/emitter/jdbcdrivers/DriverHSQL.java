package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


public class DriverHSQL extends JdbcDriver
{
	@Override
	public String driverName()
	{
		return "HSQL Database Engine Driver";
	}

	protected String columnTypeSQL( String defalt) 
	{
		if (defalt.equals("BYTES"))
		{
			return "BINARY"; 
		}
        else if ( defalt.equals("NUMERIC(18,0)") )
        {
        	// NUMERIC(18,0) does not work for Insert IDENTITY key columns
            return "BIGINT"; 
        }
        else if (defalt.equals("CLOB")) 
        {
			return "LONGVARCHAR";
		}
        else
        {
			return defalt;
        }
	}
}
