package entities.building.elevator;

import entities.Agent;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements SimulationEntity, AgentLocation {

    private Floor floor;
    private boolean closed;
    private List<Agent> agents;

    public Elevator(Floor floor) {
        this.floor = floor;
        this.closed = true;
        this.agents = new ArrayList<>();
    }

    // Single step of elevator
    public void move() {}

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
