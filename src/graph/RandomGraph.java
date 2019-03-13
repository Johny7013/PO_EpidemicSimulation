package graph;

import utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomGraph {

    private Random random;

    RandomGraph(Random random) {
        this.random = random;
    }

    ArrayList<Pair<Integer, Integer>> generateEdges(long numberOfEdges, long numberOfVertices) {

        ArrayList<Pair<Integer, Integer>> edges = new ArrayList<>((int) numberOfEdges);
        HashSet<Long> differentRandomNumbersFromInterval = new HashSet<>((int) numberOfEdges);

        long maxNumberOfEdges = numberOfVertices * (numberOfVertices - 1) / 2;
        long aux;

        //Floyd Sampling Algorithm
        for (long i = maxNumberOfEdges - numberOfEdges + 1; i <= maxNumberOfEdges; i++) {

            aux = (long) (random.nextDouble() * (i - 1)) + 1;

            if (!differentRandomNumbersFromInterval.contains(aux))
                differentRandomNumbersFromInterval.add(aux);
            else
                differentRandomNumbersFromInterval.add(i);

        }

        // we have to solve this equation
        // maxNumberOfEdges - k * (k-1) / 2 <= x,
        // where k is unknown, to find out which edge
        // is represented by value x
        // exists bijection within values from range 1 to n * (n-1) / 2
        // (n  number of vertices) and edges so we can sort edges (undirected) by first value
        // and then get edge which correspond with this value

        double delta, k;
        int firstEnd, secondEnd;

        for (long x : differentRandomNumbersFromInterval) {
            delta = 1 + 8 * (maxNumberOfEdges - x);

            k = ((1 + Math.sqrt(delta)) / 2);

            firstEnd = (int) numberOfVertices - (int) k;
            secondEnd = (int) (numberOfVertices - ((maxNumberOfEdges - ((long) k * ((long) k - (long) 1) / (long) 2)) - x));

            edges.add(Pair.createPair(firstEnd, secondEnd));

        }

        return edges;

    }
}
