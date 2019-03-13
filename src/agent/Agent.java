package agent;

import graph.Graph;
import meetings.Meetings;

import java.util.Random;

public interface Agent {
    void becomeInfected();

    void becomeImmune();

    void becomeDead();

    AgentState getState();

    int getId();

    void setUpMeetings(Graph socialNetwork, Meetings meetings, Random random, int day, int endDay);

    String toString();
}
