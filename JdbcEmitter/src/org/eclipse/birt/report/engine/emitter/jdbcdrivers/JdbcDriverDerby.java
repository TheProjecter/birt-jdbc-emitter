package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * Contains Derby/Cloudscape 10.0 implementation
 * 
 * $Revision: 1.0 $ $Date: Apr 12 2004 14:33:46 $
 * 
 * @author Denis Rodrigues Cassiano deniscassiano@gmail.com
 */
public class JdbcDriverDerby extends JdbcDriver {

	// protected String driverName() {
	// return "IBM DB2 JDBC Universal Driver Architecture"; // new type 4
	// //return "IBM DB2 JDBC 2.0 Type 2"; // old type 2
	// }

	@Override protected String driverName() 
	{
		return "Apache Derby Embedded JDBC Driver";
	}

	
	@Override protected String columnTypeSQL(String defalt)
	{
		if (defalt.equals("BYTES"))
			return "LONG VARCHAR";
		else
			return defalt;
	}
}
