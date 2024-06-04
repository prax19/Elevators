package entities.building;

import entities.Agent;
import entities.building.elevator.ElevatorSystem;
import simulation.SimulationEntity;

import java.util.List;

public class Building implements SimulationEntity {

    private List<Floor> floors;
    private ElevatorSystem elevatorSystem;

    private List<Agent> agents;

    @Override
    public void update() {
        for(Floor floor : floors)
            floor.update();
        for(Agent agent : agents)
            agent.update();
    }
}
