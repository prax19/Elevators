package entities;

import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;

public class Agent implements SimulationEntity {

    private AgentLocation currentLocation;
    private Floor targetFloor;

    @Override
    public void update() {

    }
}
