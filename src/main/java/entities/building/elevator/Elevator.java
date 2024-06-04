package entities.building.elevator;

import entities.building.Building;
import entities.building.Floor;
import utilities.AgentLocation;

public class Elevator implements AgentLocation {

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
}
