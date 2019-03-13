package graph;

import agent.Agent;

import java.util.ArrayList;

public class Vertex {
    private int id = 0;
    private ArrayList<Vertex> neighbours;
    private Agent agent;

    public Vertex(int id, Agent agent) {
        this.id = id;
        this.neighbours = new ArrayList<>();
        this.agent = agent;
    }

    public void addNeighbour(Vertex v) {
        this.neighbours.add(v);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Vertex> getNeighbours() {
        return neighbours;
    }

    public int getNumberOfNeighbours() {
        return this.neighbours.size();
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", neighbours=" + neighbours.size() +
                '}';
    }
}