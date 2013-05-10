package org.eclipse.birt.report.engine.emitter.jdbcdrivers;



/**
 * Open source verion of Interbase.
 * 
 * @author John Price, Augustin
 */
public class JdbcDriverFirebird extends JdbcDriverInterbase 
{
//protected String driverName() {return "firebirdsql jca/jdbc resource adapter";}
  protected String driverName() {return "Jaybird JCA/JDBC driver";}
}
