package repository.activities;

import model.Activity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryMySQL implements ActivityRepository{


    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Activity> findall() {
        List<Activity> activities = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from activities";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                activities.add(buildActivityFromResultset(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return activities;
    }

    private Activity buildActivityFromResultset(ResultSet resultSet) throws SQLException {
        Activity activity = new Activity();
                activity.setEmployee_id(resultSet.getLong("employee_id"));
                activity.setAmount_of_money(resultSet.getLong("amount_of_money"));
                activity.setAction(resultSet.getString("action"));
                activity.setDate(resultSet.getDate("data"));
                activity.setDetails(resultSet.getString("details"));
                activity.setActivity_id(resultSet.getLong("activity_id"));
        return  activity;
    }

    @Override
    public Activity findByID(Long id) {
        Activity activity = new Activity();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from activities where `activity_id` = "+id;
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                activity = buildActivityFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activity;
    }

    @Override
    public List<Activity> findBeforeDate(Date date) {
        List<Activity> activities = new ArrayList<>();
        long referenceDate = date.getTime();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from activities";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Activity activity = buildActivityFromResultset(resultSet);
                long activityDate = activity.getDate().getTime();
                if(referenceDate <= activityDate) activities.add(activity);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return activities;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO activities values (null, ?, ?, ?,?,?)");
            insertStatement.setDate(1, Date.valueOf(LocalDate.now()));
            insertStatement.setLong(2,activity.getEmployee_id());
            insertStatement.setString(3,activity.getAction());
            insertStatement.setFloat(4,activity.getAmount_of_money());
            insertStatement.setString(5,activity.getDetails());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from activities where activity_id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
