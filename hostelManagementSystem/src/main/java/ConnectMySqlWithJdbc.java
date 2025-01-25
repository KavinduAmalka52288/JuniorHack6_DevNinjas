import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySqlWithJdbc {
private static final String URL = "jdbc:mysql://localhost:3306/hostel_management_system";
private static final String USERID = "root";
private static final String USERPASSWORD = "";
private static final String JDBCDRIVER = "com.mysql.jdbc.Driver";
protected static Connection getConnection() {
Connection conn = null;

try {
Class.forName(JDBCDRIVER);
conn = (Connection) DriverManager.getConnection(URL, USERID, USERPASSWORD);
return conn;
}
catch (SQLException | ClassNotFoundException e) {
System.out.println("ExceptionMessage: " + e.getMessage());
System.out.println("ErrorCode: " + e.getCause());
return null;
}
}
}