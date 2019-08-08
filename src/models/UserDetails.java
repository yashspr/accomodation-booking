package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetails {

    private ResultSet rs;
    private int length = 0;

    public UserDetails(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(this.rs.last()) {
            this.length = this.rs.getRow();
            this.rs.beforeFirst();
        }
    }

    public int getLength() {
        return length;
    }

    public int getId() throws SQLException {
        return rs.getInt("id");
    }

    public String getFirstName() throws SQLException {
        String s = rs.getString("fname");
        if(s==null)
            return "";
        else
            return s;
    }

    public String getLastName() throws SQLException {
        String s = rs.getString("lname");
        if(s==null)
            return "";
        else
            return s;
    }

    public String getPhone() throws SQLException {
        String s = rs.getString("phone");
        if(s==null)
            return "";
        else
            return s;
    }

    public String getUserType() throws SQLException {
        String s = rs.getString("user_type");
        if(s==null)
            return "";
        else
            return s;
    }

    public ResultSet getResultSet() {
        return this.rs;
    }

}
