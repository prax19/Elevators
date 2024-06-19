package entities;

import entities.building.Building;
import entities.building.Floor;
import entities.building.elevator.Elevator;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import java.util.Objects;

public class Agent implements SimulationEntity {

    private final Building context;

    private AgentLocation currentLocation;
    private final Floor targetFloor;

    private boolean isElevatorCalled;
    private boolean isFloorChosen;

    public Agent(Building context, AgentLocation currentLocation, Floor targetFloor) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.targetFloor = targetFloor;
        this.isElevatorCalled = false;
        this.isFloorChosen = false;
        currentLocation.onEnter(this);
    }

    public Agent(Building context, int currentFloorLevel, int targetFloorLevel) {
        this(
            context,
            context.getFloorByLevel(currentFloorLevel),
            context.getFloorByLevel(targetFloorLevel)
        );
    }

    public void remove() {
        currentLocation.onExit(this);

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
        if(currentLocation instanceof Elevator && !isFloorChosen) {
            context.getElevatorSystem().sendElevator((Elevator) currentLocation, targetFloor);
            isFloorChosen = true;
        }
    }

    @Override
    public boolean isIdle() {
        if(Objects.equals(currentLocation, targetFloor))
            return true;
        else
            return false;
    }

}
