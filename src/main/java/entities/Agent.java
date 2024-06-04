package entities;

import entities.building.Building;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import java.util.List;

public class Agent implements SimulationEntity {

    private final Building context;

    private AgentLocation currentLocation;
    private final Floor targetFloor;

    private boolean isElevatorCalled;

    public Agent(Building context, AgentLocation currentLocation, Floor targetFloor) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.targetFloor = targetFloor;
        this.isElevatorCalled = false;
        currentLocation.onEnter(this);
    }

    @Override
    public void update() {
        if(currentLocation instanceof Floor && !currentLocation.equals(targetFloor)) {
            Direction direction =  Floor.determineDirection(
                    context.getFloors(),
                    (Floor)currentLocation,
                    targetFloor
            );
            if(!isElevatorCalled) {
                context.getElevatorSystem().callElevator((Floor)currentLocation, direction);
                isElevatorCalled = true;
            }
        }
    }

}
