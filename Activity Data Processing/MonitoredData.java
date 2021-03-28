import java.time.Duration;
import java.time.LocalTime;

class MonitoredData {
    String start_time;
    String end_time;
    String activity_label;
    MonitoredData(){
        start_time = "";
        end_time = "";
        activity_label = "";
    }

    public MonitoredData(String activity, String startTime, String endTime) {
        activity_label = activity;
        start_time = startTime;
        end_time = endTime;
    }
    public String getActivity_label(){
        return  activity_label;
    }
    public int getDay(){
        int day=0;
        String[] arrOfStr = start_time.split(" ");
        String[] Date = arrOfStr[0].split("-");
        day = Integer.parseInt(Date[2]);
        return day;
    }
    public int getStartHour(){
        int hour=0;
        String[] arrOfStr = start_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[0]);
        return hour;
    }
    public int getEndHour(){
        int hour=0;
        String[] arrOfStr = end_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[0]);
        return hour;
    }
    public int getStartMinute(){
        int hour=0;
        String[] arrOfStr = start_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[1]);
        return hour;
    }
    public int getEndMinute(){
        int hour=0;
        String[] arrOfStr = end_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[1]);
        return hour;
    }
    public int getStartSecond(){
        int hour=0;
        String[] arrOfStr = start_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[2]);
        return hour;
    }
    public int getEndSecond(){
        int hour=0;
        String[] arrOfStr = end_time.split(" ");
        String[] Time = arrOfStr[1].split(":");
        hour = Integer.parseInt(Time[2]);
        return hour;
    }
    public long calculateTime(){
        LocalTime start = LocalTime.of(getStartHour(),getStartMinute(),getStartSecond());
        LocalTime end = LocalTime.of(getEndHour(),getEndMinute(),getEndSecond());
        Duration diff = Duration.between(start,end);
        if(diff.toHours()<0)
            return -diff.toMillis()/1000;
        else
            return diff.toMillis()/1000;
    }

}
