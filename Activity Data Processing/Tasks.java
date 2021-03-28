import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Tasks {
    List<MonitoredData> data;

    Tasks(){
        data = new ArrayList<MonitoredData>();
            ReadData rd = new ReadData();
            data = rd.getData();

    }

    public List<MonitoredData> Task1(){
        return data;
    }
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public int Task2(){
        int nr = (int) data.stream().filter(distinctByKey(MonitoredData::getDay)).count();
        return nr;
    }

    public Map<String,Long> Task3() throws IOException {

        return data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity_label,Collectors.counting()));
    }

    public Map<Integer,Map<String,Long>> Task4() throws IOException {

        return data.stream().collect(Collectors.groupingBy(MonitoredData::getDay,
                Collectors.groupingBy(MonitoredData::getActivity_label,Collectors.counting())));
    }

    public Map<String, Long> Task5() throws IOException {

        return data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity_label,
                Collectors.summingLong(MonitoredData::calculateTime)));
    }
    public List<String> Task6() throws IOException {

        List<String> activities = data.stream().filter(distinctByKey(MonitoredData::getActivity_label)).map(MonitoredData::getActivity_label).collect(Collectors.toList());

        Map<String,Long> lessThen5 = data.stream().filter(d->d.calculateTime()<300)
                .collect(Collectors.groupingBy(MonitoredData::getActivity_label,Collectors.counting()));
        Map<String,Long> timesDone = data.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivity_label,Collectors.counting()));
        List<String> quick = new ArrayList<String>();

        for (String s:activities
             ) {

            if(lessThen5.containsKey(s) && lessThen5.get(s)/timesDone.get(s)>0.9)
                quick.add(s);
        }
        return quick;


    }


    public static void main(String[] args) throws IOException {
        Tasks tasks = new Tasks();
        WriteFiles writer = new WriteFiles();
        writer.PrintTask1(tasks.Task1());
        writer.PrintTask2(tasks.Task2());
        writer.PrintTask3(tasks.Task3());
        writer.PrintTask4(tasks.Task4());
        writer.PrintTask5(tasks.Task5());
        writer.PrintTask6(tasks.Task6());
    }
}
