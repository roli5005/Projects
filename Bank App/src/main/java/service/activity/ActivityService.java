package service.activity;

import model.Activity;
import model.validator.Notification;

import java.sql.Date;
import java.util.List;

public interface ActivityService {
    Notification<Activity> addActivity(Activity activity);

    List<Activity> getActivitiesBeforeDate(Date date);
}
