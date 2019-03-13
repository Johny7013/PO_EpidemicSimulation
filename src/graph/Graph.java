package graph;

import agent.Agent;
import agent.AgentState;
import utils.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class Graph {
    private int numberOfVertices;
    private long numberOfEdges;
    private Vertex[] vertices;
    private LinkedList<Pair<Vertex, Integer>> queue = new LinkedList<>();
    private int[] visited;
    private int bfsCounter = 1;

    public Graph(int numberOfVertices, long numberOfEdges, Random random, Agent[] agents) {


        RandomGraph randomGraph = new RandomGraph(random);
        ArrayList<Pair<Integer, Integer>> edges = randomGraph.generateEdges(numberOfEdges, numberOfVertices);

        this.numberOfVertices = numberOfVertices;
        this.numberOfEdges = numberOfEdges;
        this.vertices = new Vertex[numberOfVertices + 1];
        this.visited = new int[numberOfVertices + 1];

        for (int i = 1; i <= numberOfVertices; i++) {
            visited[i] = 0;
        }

        for (int i = 1; i <= numberOfVertices; i++) {
            vertices[i] = new Vertex(i, agents[i]);
        }

        for (Pair<Integer, Integer> e : edges) {
            vertices[e.getFirst()].addNeighbour(vertices[e.getSecond()]);
            vertices[e.getSecond()].addNeighbour(vertices[e.getFirst()]);
        }

    }

    public ArrayList<Agent> bfs(Vertex v, int depth) {

        ArrayList<Agent> list = new ArrayList<>();
        Pair<Vertex, Integer> pair;  // Vertex and depth

        if (v.getAgent().getState() != AgentState.dead) {
            queue.addLast(Pair.createPair(v, 0));
            visited[v.getId()] = bfsCounter;
        }


        while (!queue.isEmpty()) {

            pair = queue.pollFirst();
            list.add(pair.getFirst().getAgent());

            if (pair.getSecond() < depth) {

                ArrayList<Vertex> neighbours = pair.getFirst().getNeighbours();

                for (Vertex vertex : neighbours) {

                    if (vertex.getAgent().getState() != AgentState.dead && visited[vertex.getId()] < bfsCounter) {
                        queue.addLast(Pair.createPair(vertex, pair.getSecond() + 1));
                        visited[vertex.getId()] = bfsCounter;
                    }
                }
            }
        }

        bfsCounter++;

        if (bfsCounter == 2e9) {
            bfsCounter = 1;

            for (int i = 1; i <= numberOfVertices; i++)
                visited[i] = 0;
        }

        if (list.size() > 0) {
            list.set(0, list.get(list.size() - 1));
            list.remove(list.size() - 1);
        }

        return list;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public long getNumberOfEdges() {
        return numberOfEdges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Vertex getVertex(int id) {

        try {
            return this.vertices[id];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid id");
        }

        return null;
    }

    public void print() {
        for (int i = 1; i <= numberOfVertices; i++) {
            System.out.print(i + " : ");

            for (Vertex v : vertices[i].getNeighbours()) {
                System.out.print(v.getId() + " ");
            }

            System.out.println();
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 1; i <= numberOfVertices; i++) {
            s.append(i).append(" ");

            for (Vertex v : vertices[i].getNeighbours()) {
                s.append(v.getId()).append(" ");
            }
            s.setCharAt(s.length() - 1, '\n');
        }

        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }
}