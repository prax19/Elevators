package entities.building;

import entities.Agent;
import entities.building.elevator.ElevatorSystem;
import simulation.SimulationEntity;

import java.util.ArrayList;
import java.util.List;

public class Building implements SimulationEntity {

    private final List<Floor> floors;
    private final ElevatorSystem elevatorSystem;

    private List<Agent> agents;

    public Building(int minLevel, int maxLevel, int numberOfElevators) {
        floors = Floor.getFloorsInRange(minLevel, maxLevel);
        elevatorSystem = new ElevatorSystem(this, numberOfElevators);
        agents = new ArrayList<>();
    }

    @Override
    public void update() {
        elevatorSystem.update();
        for(Floor floor : floors)
            floor.update();
        for(Agent agent : agents)
            agent.update();
    }

    public void addAgent(Floor startingFloor, Floor targetFloor) {
        agents.add(
                new Agent(
                    this,
                    startingFloor,
                    targetFloor
                )
        );
    }

    public void addAgent(int startingFloorLevel, int targetFloorLevel) {
        agents.add(
            new Agent(
                this,
                startingFloorLevel,
                targetFloorLevel
            )
        );
    }

    public void removeAllAgents() {
        for(Agent agent: agents) {
            agent.remove();
        }
        agents.clear();
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public Floor getFloorByLevel(int level) {
        return Floor.getFloor(getFloors(), level);
    }

    public ElevatorSystem getElevatorSystem() {
        return elevatorSystem;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    @Override
    public boolean isIdle() {
        boolean idle = true;
        for(Agent agent: agents)
            if(!agent.isIdle()) {
                idle = false;
                break;
            }
        if(!elevatorSystem.isIdle())
            idle = false;
        return idle;
    }

}
