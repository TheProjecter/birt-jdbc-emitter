This BIRT emitter plugin would output tables to a relational database

1) This JDBC emitter is quite flexible and configurable.

2) It supports many relational data bases. All we have to do is, provide DB details in properties file. No code change or recompilation is required.

3) This emitter respects your security as it uses internally BASE64 algorithm to decrypt the passwords.

4) We can use a standalone program (BASE64 algorithm implementation) to get encrypted password by passing actual password as an argument.

> a) Enter Base64.bat ‘your password’ in command prompt.

> b) Make sure that Base64.class is added to your class path.

> c) Output will be encrypted password and put this password in properties file.

5) This emitter is capable of creating the tables on the fly by deriving equivalent SQL data types for different DB’s from Java data types.

6) If you want to append new data to already existing table, you can achieve this by setting ‘Append Data’ value to true in properties file.

7) To address performance issue, JDBC emitter will do batch insertions.

8) Emitter takes table name from designer.

9) Designed and implemented in very simple manner so that everybody can understand easily and modify.