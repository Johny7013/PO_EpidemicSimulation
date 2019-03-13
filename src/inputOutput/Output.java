package inputOutput;

import agent.Agent;
import graph.Graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Output {

    PrintWriter output;

    public Output(Input input) {

        try {
            boolean aux = Files.deleteIfExists(input.getFileWithReport());
            Files.createDirectories(input.getFileWithReport().getParent());
            Files.createFile(input.getFileWithReport());

        } catch (IOException e) {
            System.err.println("Nie można utworzyć pliku z raportem");
            System.exit(1);
        }

        try {
            this.output = new PrintWriter(input.getFileWithReport().toString());
        } catch (FileNotFoundException e) {
            System.err.println("Nie można znaleźć pliku z raportem");
            System.exit(1);
        }
    }

    public void printProperties(Input input) {

        output.println("# twoje wyniki powinny zawierać te komentarze");
        output.println(input.outToString());
        output.println();
    }

    public void printGraph(Graph graph) {
        output.println("# graf");
        output.println(graph.toString());
        output.println();
    }

    public void printAgents(Agent[] agents) {

        output.println("# agenci jako: id typ lub id* typ dla chorego");

        for (int i = 1; i < agents.length; i++) {
            output.println(agents[i].toString());
        }

        output.println();
    }

    public void printState(int healthy, int infected, int immune) {
        output.println(healthy + " " + infected + " " + immune);
    }

    public void printlnString(String s) {
        output.println(s);
    }

    public void printString(String s) {
        output.print(s);
    }

    public void close() {
        output.close();
    }


}
