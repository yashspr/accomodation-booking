package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getNewConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/accomodation","yashas","yashas");
            return con;
        } catch(ClassNotFoundException e1) {
            System.out.println("Class not found");
            return null;
        } catch(SQLException e2) {
            System.out.println(e2.getLocalizedMessage());
            return null;
        }

    }

}
