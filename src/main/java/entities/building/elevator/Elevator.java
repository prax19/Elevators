package entities.building.elevator;

import entities.Agent;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Elevator implements SimulationEntity, AgentLocation {

    private final List<Floor> floors;

    private Floor floor;
    private boolean closed;
    private List<Agent> agents;

    private Direction direction;
    private FloorRequestQueue floorQueue;

    private int previousQueueSize;

    public Elevator(List<Floor> floors, Floor floor) {
        this.floors = floors;
        this.floor = floor;
        this.closed = true;
        this.agents = new ArrayList<>();
        this.direction = Direction.UP;
        this.floorQueue = new FloorRequestQueue();
        this.previousQueueSize = 0;
    }

    @Override
    public void onEnter(Agent agent) {
        agents.add(agent);
    }

    @Override
    public void onExit(Agent agent) {
        agents.remove(agent);
    }

    public void move() {
        floor = Floor.nextFloor(floors, floor, direction);
    }

    private void changeDirection() {
        if(direction == Direction.UP)
            direction = Direction.DOWN;
        else
            direction = Direction.UP;
    }

    @Override
    public void update() {
        if(!isIdle()) {
            if (floorQueue.contains(floor)) {
                if (floorQueue.getDirection(floor) != direction) {
                    boolean reachesTop = floor == floorQueue.getTopFloor() && direction == Direction.UP;
                    boolean reachesBottom = floor == floorQueue.getBottomFloor() && direction == Direction.DOWN;
                    if ( reachesTop || reachesBottom )
                        changeDirection();
                }
                if (floorQueue.getDirection(floor) == direction) {
                    List<Agent> agentsToCheck = new ArrayList<>();
                    agentsToCheck.addAll(agents);
                    agentsToCheck.addAll(floor.getAgents());
                    for(Agent agent: agentsToCheck) {
                        if (floor.equals(agent.getTargetFloor()))
                            agent.enter(floor);
                        if (direction == agent.getDirection())
                            agent.enter(this);
                    }
                    removeFromQueue(floor);
                } else {
                    move();
                }
            } else
                move();
        }
    }

    public void addToQueue(Floor floor, Direction direction) {
        if(!floorQueue.getFloors().contains(floor))
            floorQueue.addFloor(floor, direction);
        if(floorQueue.size() == 1)
            setDirection(Floor.determineDirection(floors, this.floor, floorQueue.getFloors().get(0)));
    }

    public void removeFromQueue(Floor floor) {
        floorQueue.remove(floor);
    }

    public FloorRequestQueue getFloorQueue() {
        return floorQueue;
    }

    public int getQueueSize() {
        return floorQueue.size();
    }

    public Floor getFloor() {
        return floor;
    }

    public int getAgentCount() {
        return agents.size();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if(direction != null)
            this.direction = direction;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean isIdle() {
        return floorQueue.isEmpty();
    }
}
