package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDetails {

    private ResultSet rs;
    private int length = 0;

    public BookingDetails(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(this.rs.last()) {
            this.length = rs.getRow();
            this.rs.beforeFirst();
        }
    }

    public int getLength() {
        return length;
    }

    public int getTransactionId() throws SQLException {
        return rs.getInt("t_id");
    }

    public int getUserId() throws SQLException {
        return rs.getInt("u_id");
    }

    public int getEId() throws SQLException {
        return rs.getInt("e_id");
    }

    public Date getStartDate() throws SQLException {
        return rs.getDate("start_date");
    }

    public Date getEndDate() throws SQLException {
        return rs.getDate("end_date");
    }

    public int getBookedRooms() throws SQLException {
        return rs.getInt("no_of_rooms");
    }

    public int getTotalPrice() throws SQLException {
        return rs.getInt("total_price");
    }

    public boolean getPriceStatus() throws SQLException {
        return rs.getBoolean("price_status");
    }
}
