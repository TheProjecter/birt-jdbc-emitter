package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * Borland's db.
 * 
 * @author Richard Schmidt
 */
public class JdbcDriverInterbase extends JdbcDriver 
{
	protected String driverName() 
	{
		return "InterClient";
	}

	@Override
	protected String columnTypeSQL(String type) {
		// TODO Auto-generated method stub
		return type;
	}
}
