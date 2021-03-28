import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReadData {
    private final List<MonitoredData> data = new ArrayList<MonitoredData>();

    ReadData(){
        try(Stream<String> stream= Files.lines(Paths.get("D:\\PersonMonitor\\src\\Activities.txt"))) {
            stream.forEach(line-> {
                String[] contents=line.split("\t\t");
                String startTime=contents[0];
                String endTime= contents[1];
                String activity=contents[2];
                MonitoredData d = new MonitoredData(activity, startTime, endTime);
                data.add(d);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<MonitoredData> getData(){
        return  data;
    }

}
