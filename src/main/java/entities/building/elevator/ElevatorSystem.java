package entities.building.elevator;

import entities.Agent;
import entities.building.Building;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.Direction;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem implements SimulationEntity {

    private final Building context;

    private final List<Elevator> elevators;

    public ElevatorSystem(Building context, int numberOfElevators) {
        this.context = context;
        elevators = new ArrayList();
        for(int i = 0; i < numberOfElevators; i++)
            elevators.add(
                    new Elevator(context.getFloors(), Floor.getGroundFloor(context.getFloors()))
            );
    }

    // Calls idle or on-the-way elevator.
    // Equivalent of calling elevator from an up/down control panel on each floor.
    public void callElevator(Floor floor, Direction direction) {

    }

    // Send specific elevator to specific floor.
    // Equivalent of selecting floor from an elevator control panel.
    public void sendElevator(Elevator elevator, Floor floor) {}

    // Allows agents to enter the elevator
    public void pickUp(Elevator elevator) {}

    // Allows agents to leave the elevator
    public void dropOff(Elevator elevator) {}

    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void update() {
        for(Elevator elevator : elevators)
            elevator.update();
    }
}
