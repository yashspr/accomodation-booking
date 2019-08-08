package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailabilityDetails {

    private ResultSet rs;
    private int length = 0;

    public AvailabilityDetails(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(this.rs.last()) {
            this.length = rs.getRow();
            this.rs.beforeFirst();
        }
    }

    public int getLength() {
        return length;
    }

    public int getEId() throws SQLException {
        return rs.getInt("e_id");
    }

    public int getTotalRooms() throws SQLException {
        return rs.getInt("total_rooms");
    }

    public int getAvailableRooms() throws SQLException {
        return rs.getInt("available_rooms");
    }

}
