package agent;

import java.util.Objects;

public abstract class DefaultAgent implements Agent {

    int id;
    AgentState state;
    double meetingProbability;

    DefaultAgent(int id, double meetingProbability) {
        this.id = id;
        this.state = AgentState.healthy;
        this.meetingProbability = meetingProbability;
    }

    @Override
    public void becomeDead() {
        state = AgentState.dead;
    }

    @Override
    public void becomeInfected() {
        state = AgentState.infected;
    }

    @Override
    public void becomeImmune() {
        state = AgentState.immune;
    }

    @Override
    public AgentState getState() {
        return state;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAgent that = (DefaultAgent) o;
        return id == that.id &&
                Double.compare(that.meetingProbability, meetingProbability) == 0 &&
                state == that.state;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);//Objects.hash(id, state, meetingProbability);
    }
}
