import agent.Agent;
import agent.AgentState;
import agent.NormalAgent;
import agent.SociableAgent;
import graph.Graph;
import inputOutput.Input;
import inputOutput.Output;
import meetings.Meetings;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Symulacja {

    private Input input = new Input();
    private Output output;
    private Agent[] agents;
    private Random random;
    private Graph socialNetwork;
    private Meetings meetings;
    private int numberOfHealthyAgents;
    private int numberOfInfectedAgents = 1;
    private int numberOfImmuneAgents = 0;
    private HashSet<Agent> currentPopulation = new HashSet<>(input.getNumberOfAgents());
    private LinkedList<Agent> agentsToRemove = new LinkedList<>();
    private ArrayList<Pair<Agent, Agent>> todaysMeetings;

    private void initialiseAttributesByInput() {
        this.output = new Output(input);
        this.random = new Random(input.getSeed());
        this.agents = new Agent[input.getNumberOfAgents() + 1];
        this.meetings = new Meetings(input.getNumberOfDays());
        this.numberOfHealthyAgents = input.getNumberOfAgents() - 1;
    }

    private void processInput() {
        this.input.processInput();
    }

    private void randomAgents() {

        for (int i = 1; i <= input.getNumberOfAgents(); i++) {

            if (Double.compare(random.nextDouble(), input.getProbSociable()) < 0)
                agents[i] = new SociableAgent(i, input.getProbMeeting());
            else
                agents[i] = new NormalAgent(i, input.getProbMeeting());
        }

    }

    private void infectRandomAgent() {
        int randomInt = random.nextInt(input.getNumberOfAgents()) + 1;
        agents[randomInt].becomeInfected();
    }

    private void createRandomSocialNetwork() {
        long numberOfEdges = (long) input.getMeanFriends() * (long) input.getNumberOfAgents() / (long) 2;
        // creates random graph
        socialNetwork = new Graph(input.getNumberOfAgents(), numberOfEdges, random, agents);
    }

    private void initialiseCurrentPopulation() {
        for (int i = 1; i <= input.getNumberOfAgents(); i++)
            currentPopulation.add(agents[i]);
    }

    private void processDay(int currentDay) {

        output.printState(numberOfHealthyAgents, numberOfInfectedAgents, numberOfImmuneAgents);

        // checks if infected agents become dead or immune
        for (Agent agent : currentPopulation) {

            if (agent.getState() == AgentState.infected) {

                if (Double.compare(random.nextDouble(), input.getMortality()) < 0) {

                    agent.becomeDead();
                    agentsToRemove.addLast(agent);
                    numberOfInfectedAgents--;
                } else if (Double.compare(random.nextDouble(), input.getProbRecovery()) < 0) {

                    agent.becomeImmune();
                    numberOfImmuneAgents++;
                    numberOfInfectedAgents--;
                }

            }

        }

        // removes dead agents from population
        for (Agent agent : agentsToRemove) {
            currentPopulation.remove(agent);
        }
        agentsToRemove.clear();

        // Sets up meetings
        for (Agent agent : currentPopulation) {
            agent.setUpMeetings(socialNetwork, meetings, random, currentDay, input.getNumberOfDays());
        }

        // Processes today's meetings
        todaysMeetings = meetings.getMeetings(currentDay);

        AgentState state1;
        AgentState state2;

        for (Pair<Agent, Agent> meeting : todaysMeetings) {

            state1 = meeting.getFirst().getState();
            state2 = meeting.getSecond().getState();

            if (state1 == AgentState.infected && state2 == AgentState.healthy) {

                if (Double.compare(random.nextDouble(), input.getProbInfection()) < 0) {
                    meeting.getSecond().becomeInfected();
                    numberOfHealthyAgents--;
                    numberOfInfectedAgents++;
                }

            }

            if (state2 == AgentState.infected && state1 == AgentState.healthy) {

                if (Double.compare(random.nextDouble(), input.getProbInfection()) < 0) {
                    meeting.getFirst().becomeInfected();
                    numberOfHealthyAgents--;
                    numberOfInfectedAgents++;
                }

            }

        }

    }

    private void printProperties() {
        output.printProperties(input);
    }

    private void printAgents() {
        output.printAgents(agents);
    }

    private void printGraph() {
        output.printGraph(socialNetwork);
    }

    private void printlnString(String s) {
        output.printlnString(s);
    }

    private int getNumberOfDays() {
        return input.getNumberOfDays();
    }

    private void closeOutput() {
        output.close();
    }


    public static void main(String[] args) {

        Symulacja simulation = new Symulacja();

        simulation.processInput();

        // get data from input and initialise attributes
        // with approprite values
        simulation.initialiseAttributesByInput();

        simulation.printProperties();

        // randoms agents
        simulation.randomAgents();

        // infects one of the agents
        simulation.infectRandomAgent();

        simulation.printAgents();

        // creates random graph, which represents social network in the population
        simulation.createRandomSocialNetwork();

        simulation.printGraph();

        // initialise current population, which will be changing during days
        simulation.initialiseCurrentPopulation();

        simulation.printlnString("# liczność w kolejnych dniach");

        for (int currentDay = 1; currentDay <= simulation.getNumberOfDays(); currentDay++) {

            simulation.processDay(currentDay);

        }

        simulation.closeOutput();

    }

}
