package agent;

import graph.Graph;
import meetings.Meetings;
import utils.Pair;

import java.util.ArrayList;
import java.util.Random;

public class NormalAgent extends DefaultAgent {

    public NormalAgent(int id, double meetingProbability) {
        super(id, meetingProbability);
    }

    @Override
    public void setUpMeetings(Graph socialNetwork, Meetings meetings, Random random, int day, int endDay) {

        double currentMeetingProbability;
        int rand;
        Agent friend;

        if (state == AgentState.infected)
            currentMeetingProbability = meetingProbability / 2;
        else
            currentMeetingProbability = meetingProbability;

        ArrayList<Agent> listOfFriends = socialNetwork.bfs(socialNetwork.getVertex(id), 1);

        if (listOfFriends.size() > 0) {

            while (Double.compare(random.nextDouble(), currentMeetingProbability) < 0) {

                rand = random.nextInt(listOfFriends.size());

                friend = listOfFriends.get(rand);

                rand = random.nextInt(endDay - day + 1);

                meetings.addMeeting(Pair.createPair(this, friend), day + rand);
            }

        }


    }

    @Override
    public String toString() {
        if (state == AgentState.infected)
            return id + "* zwykły";
        else
            return id + " zwykły";
    }
}
