package entities;

import entities.building.Building;
import entities.building.Floor;
import entities.building.elevator.Elevator;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import javax.xml.stream.Location;
import java.util.List;

public class Agent implements SimulationEntity {

    private final Building context;

    private AgentLocation currentLocation;
    private final Floor targetFloor;

    private boolean isElevatorCalled;
    private boolean isFloorChoosen;

    public Agent(Building context, AgentLocation currentLocation, Floor targetFloor) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.targetFloor = targetFloor;
        this.isElevatorCalled = false;
        this.isFloorChoosen = false;
        currentLocation.onEnter(this);
    }

    public Floor getTargetFloor() {
        return targetFloor;
    }

    public AgentLocation getCurrentLocation() {
        return currentLocation;
    }

    public Direction getDirection() {
        if(!currentLocation.equals(targetFloor)) {
            if(currentLocation instanceof Floor)
                return Floor.determineDirection(
                        context.getFloors(),
                        (Floor) currentLocation,
                        targetFloor
                );
            else
                return ((Elevator) currentLocation).getDirection();
        }
        return null;
    }

    public void enter(AgentLocation location) {
        currentLocation.onExit(this);
        location.onEnter(this);
        currentLocation = location;

    }

    @Override
    public void update() {
        if(currentLocation instanceof Floor && !isElevatorCalled && !currentLocation.equals(targetFloor)) {
            context.getElevatorSystem().callElevator((Floor)currentLocation, getDirection());
            isElevatorCalled = true;
        }
        if(currentLocation instanceof Elevator && !isFloorChoosen) {
            context.getElevatorSystem().sendElevator((Elevator) currentLocation, targetFloor);
            isFloorChoosen = true;
        }
    }

}
