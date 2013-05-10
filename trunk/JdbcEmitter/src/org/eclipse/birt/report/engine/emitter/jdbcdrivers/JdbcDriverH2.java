/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eclipse.birt.report.engine.emitter.jdbcdrivers;

/**
 * This contains H2 specific code. 
 * Cree par Harry Karadimas a 9 sept. 08
 */

public class JdbcDriverH2 extends JdbcDriverHSQLH2 
{

	protected String driverName() 
	{
		return "H2 JDBC Driver";
	}

}

