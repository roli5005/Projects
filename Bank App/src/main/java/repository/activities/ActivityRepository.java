package repository.activities;

import model.Activity;

import java.sql.Date;
import java.util.List;

public interface ActivityRepository {
    List<Activity> findall();

    Activity findByID(Long id);

    List<Activity> findBeforeDate(Date date);

    boolean save(Activity activity);

    void removeAll();
}
