package Simulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class Manager implements Runnable{
    private List<Client> WaitingClients;
    private int currentTime;
    private List<Server> Queues;
    private List<Thread> RunningThreads;
    private int maxSimTime;
    private int NrClients;
    private float avgWaiting = 0;
    private FileWriter fout;

    public Manager(ArrayList<Client> WaitingClients,int NrCliets,int NrQs,int maxSimTime, String filename) throws IOException {

        this.NrClients = NrCliets;
        this.WaitingClients = WaitingClients;
        Queues = new ArrayList<Server>(NrQs);
        RunningThreads = new ArrayList<Thread>(NrQs);

        for(int i=0;i<NrQs;i++){
            Server s = new Server(i);
            Queues.add(i,s);
            RunningThreads.add(i,new Thread(s));
        }

        this.maxSimTime = maxSimTime;
        currentTime = 0;
        fout = new FileWriter(filename);
    }

    private void DistributeClients(){
        for (Client client:WaitingClients
             ) {
            if(client.gettArrival()==currentTime)
            {
                int serverID = getFastestServer();
                Queues.get(serverID).addClients(client);
            }
        }
        int size = 0;
        while(size<WaitingClients.size()){
            if(WaitingClients.get(size).gettArrival()==currentTime)
                WaitingClients.remove(size);
            else size++;
        }
    }

    private int getFastestServer(){
        int serverID=0;
        int min=10000;
        for (Server s:Queues
             ) {
            if(s.getWaitingTime().get()<min)
            {
                min = s.getWaitingTime().get();
                serverID = s.getID();
            }
        }
        return serverID;
    }

    private float getAvgWaiting(){
        for(Server s:Queues)
            avgWaiting += s.getTotalWaitingTimeOfClients();
        avgWaiting /= NrClients;
        return avgWaiting;
    }

    private void sync() {
        for (Thread t:RunningThreads
             ) {
            try {
                t.join(10);
                //Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void restart(){
        for(int i=0;i<Queues.size();i++){
            if(RunningThreads.get(i).getState() == Thread.State.TERMINATED)
            {
                Thread t = new Thread(Queues.get(i));
                RunningThreads.add(i,t);
                t.start();
            }
        }


    }

    private boolean checkListsIfEmpty(){
        if(!WaitingClients.isEmpty()) return false;
        else
            for (Server s : Queues
            ) {
                if (!s.Clients.isEmpty()) return false;
            }

        return true;
    }

    @Override
    public void run(){
        for (Thread t:RunningThreads) t.start();
        do {
            if (!WaitingClients.isEmpty())
                DistributeClients();
            sync();
            try {
                display();
            } catch (IOException e) {
                e.printStackTrace();
            }
            restart();
            currentTime++;
            sync();

        } while (currentTime <= maxSimTime && !checkListsIfEmpty());
        sync();
        for (Thread t:RunningThreads) t.interrupt();
        try {
            fout.write("Average waiting time: "+getAvgWaiting());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display() throws IOException {
        fout.write("Time "+currentTime+"\n");
        fout.write(clientsToString()+"\n");
        fout.write(serversToString()+"\n");
    }

    private String clientsToString(){
        StringBuilder result = new StringBuilder();
        result.append("Waiting clients: ");
        for (Client c : WaitingClients) {
            result.append(c.toString()).append("; ");
        }
        return result.toString();
    }

    private String serversToString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Queues.size(); i++) {
            result.append("Queue ").append(i+1).append(": ").append(Queues.get(i).clientsToString()).append("\n");
        }
        return result.toString();
    }




}
