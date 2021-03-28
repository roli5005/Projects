package model.builder;

import model.Activity;

import java.sql.Date;

public class ActivityBuilder {
    private Activity activity;

    public ActivityBuilder setActivityId(Long activityId){
        activity.setActivity_id(activityId);
        return this;
    }
    public ActivityBuilder setDate(Date date){
        activity.setDate(date);
        return this;
    }
    public ActivityBuilder setEmployeeId(Long employeeId){
        activity.setEmployee_id(employeeId);
        return this;
    }
    public ActivityBuilder setAction(String action){
        activity.setAction(action);
        return this;
    }
    public ActivityBuilder setAmount(float amount){
        activity.setAmount_of_money(amount);
        return this;
    }
    public ActivityBuilder setDetails(String details){
        activity.setDetails(details);
        return this;
    }
    public Activity build(){return activity;}
}
