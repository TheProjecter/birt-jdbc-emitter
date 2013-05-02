package org.eclipse.birt.report.engine.emitter.jdbcemitter;

import org.eclipse.birt.report.engine.api.RenderOption;

public class JdbcRenderOption extends RenderOption
{
	public static final String JDBC = "JDBC";
	private String propertiesFile;
	private String tableName;
	private boolean appendFlag;
	
	JdbcRenderOption()
	{
		
	}

	public String getPropertiesFile() 
	{
		return propertiesFile;
	}

	public void setPropertiesFile(String propertiesFile)
	{
		this.propertiesFile = propertiesFile;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public boolean isAppendFlag()
	{
		return appendFlag;
	}

	public void setAppendFlag(boolean appendFlag) 
	{
		this.appendFlag = appendFlag;
	}
}
