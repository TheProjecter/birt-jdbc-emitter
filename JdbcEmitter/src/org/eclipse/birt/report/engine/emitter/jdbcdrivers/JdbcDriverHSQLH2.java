/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eclipse.birt.report.engine.emitter.jdbcdrivers;


/**
 * Common code between HSQL and H2. Note that these toy databases do not support
 * locking, so is not safe in multi user mode even with optimistic locking. See
 * {@link SDriver#supportsLocking}.
 * 
 * @author aberglas
 */
public abstract class JdbcDriverHSQLH2 extends JdbcDriver {

	@Override
	protected String columnTypeSQL(String defalt) {
		if (defalt.equals("BYTES"))
			return "BINARY"; // Ie. just a byte array.
		else if (defalt.equals("NUMERIC(18,0)"))
			return "BIGINT"; // NUMERIC(18,0) does not work for Insert IDENTITY
								// key columns.
		else
			return defalt;
	}
}
