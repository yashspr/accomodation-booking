package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstablishmentsDetails {

    private ResultSet rs;
    private int length = 0;

    public EstablishmentsDetails(ResultSet rs) throws SQLException {
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
        return rs.getInt("e_id");
    }

    public String getName() throws SQLException {
        String s = rs.getString("e_name");
        if(s==null)
            return "";
        else
            return s;
    }

    public String getAddress() throws SQLException {
        String s = rs.getString("address");
        if(s==null)
            return "";
        else
            return s;
    }

    public int getOwnerId() throws SQLException {
        return rs.getInt("owner_id");
    }

    public String getServiceType() throws SQLException {
        String s = rs.getString("service_type");
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

    public String getPicLocation() throws SQLException {
        String s = rs.getString("pics");
        if(s==null)
            return "placeholder.png";
        else
            return s;
    }

    public ResultSet getResultSet() {
        return this.rs;
    }

}
