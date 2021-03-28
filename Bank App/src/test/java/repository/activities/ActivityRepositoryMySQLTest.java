package repository.activities;

import database.DBConnectionFactory;
import model.Activity;
import model.builder.ActivityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.account.AccountRepository;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityRepositoryMySQLTest {
    private static ActivityRepository activityRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        activityRepository = new ActivityRepositoryMySQL(connection);
        save();
    }

    @After
    public void tearDown() throws Exception {
        removeAll();
    }

    @Test
    public void findall() {
        List<Activity> activities = activityRepository.findall();
        Assert.assertEquals(activities.size(),1);
    }

    @Test
    public void findByID() {
        Activity activity = activityRepository.findall().get(0);
        Assert.assertEquals(activity.getActivity_id(),activityRepository.findByID(activity.getActivity_id()).getActivity_id());
    }

    @Test
    public void findBeforeDate() {
        //actually it should be findAfterDate because it finds the activities since that date
        Date date = Date.valueOf(LocalDate.now());
        List<Activity> activityList = activityRepository.findBeforeDate(date);
        Assert.assertFalse(activityList.isEmpty());
    }

    @Test
    public void save() {
        removeAll();
        Activity activity = new Activity();
                activity.setDate(Date.valueOf(LocalDate.now()));
                activity.setDetails("Test");
                activity.setAmount_of_money((float)156.67);
                activity.setAction("Test");
                activity.setEmployee_id(1L);
        activityRepository.save(activity);
    }

    @Test
    public void removeAll() {
        activityRepository.removeAll();
    }
}
