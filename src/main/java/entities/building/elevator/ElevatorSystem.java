package entities.building.elevator;

import entities.Agent;
import entities.building.Floor;
import utilities.Direction;

import java.util.List;

public class ElevatorSystem {

    private List<Elevator> elevators;

    // Calls idle or on-the-way elevator.
    // Equivalent of calling elevator from an up/down control panel on each floor.
    public void callElevator(Floor floor, Direction direction) {}

    // Send specific elevator to specific floor.
    // Equivalent of selecting floor from an elevator control panel.
    public void sendElevator(Elevator elevator, Floor floor) {}

    // Allows agents to enter the elevator
    public void pickUp(Elevator elevator) {}

    // Allows agents to leave the elevator
    public void dropOff(Elevator elevator) {}

}
