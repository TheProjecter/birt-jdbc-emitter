package org.eclipse.birt.report.engine.emitter.jdbcdrivers;




/**
 * This contains Informix specific code.
 */

public class JdbcDriverInformix extends JdbcDriver {

	protected String driverName() {
		return "Informix JDBC Driver for Informix Dynamic Server";
	}

	@Override
	protected String columnTypeSQL(String type) {
		// TODO Auto-generated method stub
		return type;
	}

}
