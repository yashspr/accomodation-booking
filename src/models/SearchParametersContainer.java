package models;

import java.sql.Date;

public class SearchParametersContainer {

    private Date check_in;
    private Date check_out;
    private int noofrooms;
    private String location;
    private int budget;
    private String type;

    public Date getCheckIn() {
        return check_in;
    }

    public void setCheckIn(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheckOut() {
        return check_out;
    }

    public void setCheckOut(Date check_out) {
        this.check_out = check_out;
    }

    public int getNoofrooms() {
        return noofrooms;
    }

    public void setNoofrooms(int noofrooms) {
        this.noofrooms = noofrooms;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
