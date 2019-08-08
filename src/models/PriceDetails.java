package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceDetails {

    private ResultSet rs;
    private int length = 0;

    public PriceDetails(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(this.rs.last()) {
            this.length = rs.getRow();
            this.rs.beforeFirst();
        }
    }

    public int getLength() {
        return length;
    }

    public int getId() throws SQLException {
        return rs.getInt("e_id");
    }

    public int getPrice() throws SQLException {
        return rs.getInt("price_per_day");
    }

    public ResultSet getResultSet() {
        return this.rs;
    }
}
