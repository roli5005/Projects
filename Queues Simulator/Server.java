package Simulator;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private final int ID;
    public BlockingQueue<Client> Clients;
    private AtomicInteger WaitingTime;
    private int totalWaitingTimeOfClients;



    public Server(int ID){
        this.ID = ID;
        Clients = new LinkedBlockingQueue<Client>();
        WaitingTime = new AtomicInteger();
        totalWaitingTimeOfClients = 0;
    }

    public int getID(){
        return ID;
    }

    public AtomicInteger getWaitingTime(){
        return WaitingTime;
    }

    public int getTotalWaitingTimeOfClients() {
        return totalWaitingTimeOfClients;
    }

    void addClients(Client client){
        Clients.add(client);
        calculateTime();
        client.settWait(WaitingTime.get());
        totalWaitingTimeOfClients += client.gettWait();
    }

    private void calculateTime(){
        WaitingTime.set(0);
        if(!Clients.isEmpty())
        for(Client x:Clients){
            WaitingTime.addAndGet(x.gettService());
        }
    }
    private void serveClient(){
        assert Clients.peek() != null;
        Clients.peek().decrement();
        WaitingTime.decrementAndGet();
    }
    @Override
    public synchronized void run()
    {

            if (!Clients.isEmpty())
            {
                try {
                    Thread.sleep(10);
                    serveClient();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                assert Clients.peek() != null;
                if (Clients.peek().gettService() == 0) {
                    try {
                        Clients.take();
                        calculateTime();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }

    }

    String clientsToString(){
        if(Clients.isEmpty())
            return "closed";
        StringBuilder result = new StringBuilder();
        for (Client c : Clients) {
            result.append(c.toString()).append("; ");
        }
        return result.toString();
    }

}
