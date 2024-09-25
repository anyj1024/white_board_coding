package src.loadBalance;

import java.util.ArrayList;
import java.util.List;

public class WeightedRoundRobin<T> {

    private final List<Server<T>> servers;
    private int totalWeight;

    public WeightedRoundRobin() {
        this.servers = new ArrayList<>();
        this.totalWeight = 0;
    }

    public void addServer(T server, int weight) {
        Server<T> newServer = new Server<>(server, weight);
        servers.add(newServer);
        totalWeight += weight;
    }

    public T getNextServer() {
        if (servers.isEmpty()) return null;
        Server<T> maxWeightServer = servers.get(0);
        int maxWeight = Integer.MIN_VALUE;

        for (Server<T> server : servers) {
            server.currentWeight += server.weight;
            if (server.currentWeight > maxWeight) {
                maxWeight = server.currentWeight;
                maxWeightServer = server;
            }
        }

        maxWeightServer.currentWeight = maxWeightServer.weight;
        return maxWeightServer.server;
    }

    private static class Server<T> {
        T server;
        int weight;
        int currentWeight;

        public Server(T server, int weight) {
            this.server = server;
            this.weight = weight;
            currentWeight = weight;
        }
    }
}
