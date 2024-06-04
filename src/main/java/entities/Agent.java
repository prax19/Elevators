package entities;

import entities.building.Building;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;

public class Agent implements SimulationEntity {

    private final Building context;

    private AgentLocation currentLocation;
    private final Floor targetFloor;

    public Agent(Building context, AgentLocation currentLocation, Floor targetFloor) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.targetFloor = targetFloor;
    }

    @Override
    public void update() {

    }
}
