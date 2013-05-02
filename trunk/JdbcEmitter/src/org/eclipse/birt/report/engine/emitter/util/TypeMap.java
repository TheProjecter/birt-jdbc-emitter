package org.eclipse.birt.report.engine.emitter.util;

import java.util.HashMap;
import java.util.Map;

public class TypeMap 
{

	    public static final String CHAR = "CHAR";
	    public static final String VARCHAR = "VARCHAR";
	    public static final String LONGVARCHAR = "LONGVARCHAR";
	    public static final String CLOB = "CLOB";
	    public static final String NUMERIC = "NUMERIC";
	    public static final String DECIMAL = "DECIMAL";
	    public static final String BIT = "BIT";
	    public static final String TINYINT = "TINYINT";
	    public static final String SMALLINT = "SMALLINT";
	    public static final String INTEGER = "INTEGER";
	    public static final String BIGINT = "BIGINT";
	    public static final String REAL = "REAL";
	    public static final String FLOAT = "FLOAT";
	    public static final String DOUBLE = "DOUBLE";
	    public static final String BINARY = "BINARY";
	    public static final String VARBINARY = "VARBINARY";
	    public static final String LONGVARBINARY = "LONGVARBINARY";
	    public static final String BLOB = "BLOB";
	    public static final String DATE = "DATE";
	    public static final String TIME = "TIME";
	    public static final String TIMESTAMP = "TIMESTAMP";
	    public static final String BOOLEANCHAR = "BOOLEANCHAR";
	    public static final String BOOLEANINT = "BOOLEANINT";
	    public static final String ARRAY = "ARRAY";
	    
	    private static Map<String,String> JavaSqlTypeMap = new HashMap<String,String>();
	    
	    static
	    {
	    	JavaSqlTypeMap.put("STRING", LONGVARCHAR);
	    	JavaSqlTypeMap.put("BIGDECIMAL", NUMERIC);
	    	JavaSqlTypeMap.put("BOOLEAN", BIT);
	    	JavaSqlTypeMap.put("INTEGER", INTEGER);
	    	JavaSqlTypeMap.put("LONG", BIGINT);
	    	JavaSqlTypeMap.put("FLOAT", REAL);
	    	JavaSqlTypeMap.put("DOUBLE", DOUBLE);
	    	JavaSqlTypeMap.put("BYTE[]", LONGVARBINARY);
	    	JavaSqlTypeMap.put("DATE", DATE);
	    	JavaSqlTypeMap.put("TIME", TIME);
	    	JavaSqlTypeMap.put("TIMESTAMP", TIMESTAMP);
	    	JavaSqlTypeMap.put("CLOB", CLOB);
	    	JavaSqlTypeMap.put("BLOB", BLOB);
	    	JavaSqlTypeMap.put("ARRAY", ARRAY);
	    }
	    
	    /**
	     * Returns the SQL type name which maps to 
	     * for the given JAVA type
	     */
	    public static String getJdbcTypeCode(String typeName)
	    {
	        String sqlType = JavaSqlTypeMap.get(typeName.toUpperCase());
	        if ( sqlType == null ) 
	        {
	            System.out.println("Couldn't find JDBC Name for typeCode: "+typeName);
	            typeName = "UNKNOWN";
	        }
	        return sqlType;
	    }

}
