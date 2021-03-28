package service.activity;

import model.Activity;
import model.validator.Notification;
import repository.activities.ActivityRepository;

import java.sql.Date;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService{
    private ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public Notification<Activity> addActivity(Activity activity) {
        Notification<Activity> activityNotification = new Notification<>();

        if(activityRepository.save(activity)){
            activityNotification.setResult(activity);
        }
        else
            activityNotification.addError("Could not insert activity!");

        return activityNotification;
    }

    @Override
    public List<Activity> getActivitiesBeforeDate(Date date) {
        return activityRepository.findBeforeDate(date);
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }


}
