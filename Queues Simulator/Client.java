package Simulator;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private int ID;
    private int tArrival;
    private AtomicInteger tService = new AtomicInteger(0);
    private int tWait;


    public Client(int ID,int tArrival, int tService){
        this.ID = ID;
        this.tArrival = tArrival;
        this.tService.set(tService);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int gettArrival() {
        return tArrival;
    }

    public void settArrival(int tArrival) {
        this.tArrival = tArrival;
    }

    public int gettService() {
        return tService.get();
    }

    public void settService(int tService) {
        this.tService.set(tService);
    }

    public int gettWait() {
        return tWait;
    }

    public void settWait(int tWait) {
        this.tWait = tWait;
    }

    public void decrement(){
        tService.decrementAndGet();
    }

    @Override
    public String toString() {
        return "(" + ID + "," + tArrival +
                "," + tService.get() + ")";
    }

}
