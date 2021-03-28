package Simulator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String in = args[0];
        Data file = new Data(in);
        String out = args[1];
        Manager M = new Manager(file.CreatedClients,file.getNrClients(),file.getNrQs(),file.getMaxSimTime(),out);
        Thread managerThread = new Thread(M);
        managerThread.start();
    }
}
