import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteFiles {
    public WriteFiles(){};


    public void PrintTask1(List<MonitoredData> data){
        try {
            FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_1.txt", true);
            for (MonitoredData d:data
            ) {
                writer.write(d.start_time+"\t\t"+d.end_time+"\t\t"+d.activity_label+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void PrintTask2(int nrOfDays) throws IOException {
        FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_2.txt", true);
        writer.write("Number of different days: "+nrOfDays);
        writer.close();
    }
    public void PrintTask3(Map<String,Long> activitycount) throws IOException {
        FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_3.txt", true);
        activitycount.entrySet().forEach(entry->{
            try {
                writer.write(entry.getKey() + " " + entry.getValue()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
    public void PrintTask4(Map<Integer,Map<String,Long>> activities) throws IOException {
        FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_4.txt", true);
        activities.forEach((key, value) -> {
            try {
                writer.write(key + " " + value + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
    public void PrintTask5(Map<String, Long> activityduration) throws IOException {
        FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_5.txt", true);
        activityduration.forEach((key, value) -> {
            try {
                writer.write(key + " " + value + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
    public void PrintTask6(List<String> QuickActivities) throws IOException {
        FileWriter writer = new FileWriter("D:\\PersonMonitor\\src\\Task_6.txt", true);
        for (String s:QuickActivities
        ) {
            writer.write(s+"\n");
        }
        writer.close();
    }
}
