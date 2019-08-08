package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    ResultSet rs;
    int length = 0;

    public Users(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(this.rs.last()) {
            this.length = this.rs.getRow();
            this.rs.beforeFirst();
        }
        this.rs.next();
    }

    public int getLength() throws SQLException {
        return length;
    }

    public int getId() throws SQLException {
        return rs.getInt("id");
    }

    public String getEmail() throws SQLException {
        return rs.getString("email");
    }

    public String getPassword() throws SQLException {
        return rs.getString("password");
    }
}
