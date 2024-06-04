package entities.building.elevator;

import entities.Agent;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements SimulationEntity, AgentLocation {

    private final List<Floor> floors;

    private Floor floor;
    private boolean closed;
    private List<Agent> agents;

    private Direction direction;
    private List<Floor> floorQueue;


    public Elevator(List<Floor> floors, Floor floor) {
        this.floors = floors;
        this.floor = floor;
        this.closed = true;
        this.agents = new ArrayList<>();
        this.direction = Direction.UP;
        this.floorQueue = new ArrayList<>();
    }

    public void move() {
        floor = Floor.nextFloor(floors, floor, direction);
        if(floor == Floor.getTopFloor(floorQueue))
            direction = Direction.DOWN;
        else if(floor == Floor.getBottomFloor(floorQueue))
            direction = Direction.UP;
    }

    @Override
    public void onEnter(Agent agent) {
        agents.add(agent);
    }

    @Override
    public void onExit(Agent agent) {
        agents.remove(agent);
    }

    @Override
    public void update() {
        if(floorQueue.contains(floor)) {
            if(closed) {
                closed = false;
            } else {
                //TODO: agent entrance
                floorQueue.remove(floor);
                closed = true;
            }
        }
        else if(!floorQueue.isEmpty())
            move();

    }

    public Floor getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isClosed() {
        return closed;
    }
}
