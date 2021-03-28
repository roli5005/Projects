package Simulator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Data {
    Scanner sc;
    ArrayList<Client> CreatedClients;
    int NrClients;
    int NrQs;
    int maxSimTime;
    int minArrival, maxArrival;
    int minService, maxService;

    public Data(String filename) throws IOException {
        CreatedClients = new ArrayList<Client>();
        sc = new Scanner(new File(filename));
        NrClients = Integer.parseInt(sc.nextLine());
        NrQs = Integer.parseInt(sc.nextLine());
        maxSimTime = Integer.parseInt(sc.nextLine());
        String Line4 = sc.nextLine();
        String[] Arrival = Line4.split(",");
        minArrival = Integer.parseInt(Arrival[0]);
        maxArrival = Integer.parseInt(Arrival[1]);
        String Line5 = sc.nextLine();
        String[] Service = Line5.split(",");
        minService = Integer.parseInt(Service[0]);
        maxService = Integer.parseInt(Service[1]);
        sc.close();
        createClients();
    }

    public void createClients(){
        for(int i=1;i<=NrClients;i++){
            Random random = new Random();
            int arr = random.nextInt(maxArrival - minArrival + 1) + minArrival;
            int ser = random.nextInt(maxService - minService + 1) + minService;
            Client c = new Client(i,arr,ser);
            CreatedClients.add(c);
        }
        sort();
    }

    public ArrayList<Client> getCreatedClients() {
        return CreatedClients;
    }
    public int getNrQs(){
        return NrQs;
    }
    public int getMaxSimTime(){
        return maxSimTime;
    }
    public int getNrClients(){
        return NrClients;
    }

    public void sort(){
        for (Client x:CreatedClients
             ) {
            for (Client y:CreatedClients
                 ) {
                if(x!=y && x.gettArrival()<y.gettArrival()){
                    int a,b,c;
                    a = x.getID();
                    b = x.gettArrival();
                    c = x.gettService();
                    x.setID(y.getID());
                    x.settArrival(y.gettArrival());
                    x.settService(y.gettService());
                    y.setID(a);
                    y.settArrival(b);
                    y.settService(c);
                }
            }
        }
    }


}
