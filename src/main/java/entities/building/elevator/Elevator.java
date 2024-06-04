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

    private int previousQueueSize;

    public Elevator(List<Floor> floors, Floor floor) {
        this.floors = floors;
        this.floor = floor;
        this.closed = true;
        this.agents = new ArrayList<>();
        this.direction = Direction.UP;
        this.floorQueue = new ArrayList<>();
        this.previousQueueSize = 0;
    }

    public void move() {
        floor = Floor.nextFloor(floors, floor, direction);
        if(floor == Floor.getTopFloor(floorQueue))
            direction = Direction.DOWN;
        else if(floor == Floor.getBottomFloor(floorQueue))
            direction = Direction.UP;
    }

    public void addToQueue(Floor floor) {
        if(!floorQueue.contains(floor))
            floorQueue.add(floor);
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
        else if(!floorQueue.isEmpty()) {
            if(previousQueueSize == 0) {
                List<Floor> edgeFloors = new ArrayList<>();
                edgeFloors.add(Floor.getBottomFloor(floorQueue));
                edgeFloors.add(Floor.getTopFloor(floorQueue));
                if(edgeFloors.get(0) == edgeFloors.get(1)) {
                    direction = Floor.determineDirection(floors, floor, edgeFloors.get(0));
                } else {
                    Floor nearestFloor = Floor.getNearestFloor(edgeFloors, floor);
                    direction = Floor.determineDirection(floors, floor, nearestFloor);
                }
            }
            move();
        }
        previousQueueSize = floorQueue.size();

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

    public boolean isIdling() {
        return floorQueue.isEmpty();
    }
}
