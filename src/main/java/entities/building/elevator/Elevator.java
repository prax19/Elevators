package entities.building.elevator;

import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;

public class Elevator implements SimulationEntity, AgentLocation {

    private Floor floor;
    private boolean closed;

    // Single step of elevator
    public void move() {}

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }
}
