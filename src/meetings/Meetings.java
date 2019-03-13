package meetings;

import agent.Agent;
import utils.Pair;

import java.util.ArrayList;

public class Meetings {

    private ArrayList<ArrayList<Pair<Agent, Agent>>> meetings;

    public Meetings(int numberOfDays) {
        this.meetings = new ArrayList<>(numberOfDays + 1);

        for (int i = 1; i <= numberOfDays + 1; i++) {
            this.meetings.add(new ArrayList<>());
        }

    }

    public void addMeeting(Pair<Agent, Agent> meeting, int day) {
        meetings.get(day).add(meeting);
    }

    public ArrayList<Pair<Agent, Agent>> getMeetings(int day) {
        return meetings.get(day);
    }
}
