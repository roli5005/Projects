package model;

import java.sql.Date;

public class Activity {
    private Long activity_id;
    private Date date;
    private Long employee_id;
    private String action;
    private float amount_of_money;
    private String details;

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getAmount_of_money() {
        return amount_of_money;
    }

    public void setAmount_of_money(float amount_of_money) {
        this.amount_of_money = amount_of_money;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
