package entities.building;

import entities.Agent;
import simulation.SimulationEntity;
import utilities.AgentLocation;

import java.util.List;

public class Floor implements SimulationEntity, AgentLocation {

    private final int level;
    private List<Agent> agents;

    public Floor(int level) {
        this.level = level;
    }

    @Override
    public void onEnter(Agent agent) {
        agents.add(agent);
    }

    @Override
    public void onExit(Agent agent) {
        agents.remove(agent);
    }

    @Override
    public void update() {

    }
}
