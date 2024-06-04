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

    public void move() {
        floor = Floor.nextFloor(floors, floor, direction);
        if(floor == Floor.getTopFloor(floorQueue.getFloors()))
            direction = Direction.DOWN;
        else if(floor == Floor.getBottomFloor(floorQueue.getFloors()))
            direction = Direction.UP;
    }

    public void addToQueue(Floor floor, Direction direction) {
        if(!floorQueue.getFloors().contains(floor))
            floorQueue.addFloor(floor, direction);
    }

    public void removeFromQueue(Floor floor) {
        floorQueue.remove(floor);
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
        if(floorQueue.contains(floor) && floorQueue.getDirection(floor) == direction) {
            if(closed) {
                closed = false;
            } else {
                List<Agent> leavingAgents = new ArrayList<>();
                for(Agent agent : agents) {
                    if(agent.getTargetFloor() == floor)
                        leavingAgents.add(agent);
                }

                List<Agent> enteringAgents = new ArrayList<>();
                for(Agent agent : floor.getAgents()) {
                    if(direction == agent.getDirection())
                        enteringAgents.add(agent);
                }

                for(Agent agent : leavingAgents) {
                    agent.enter(floor);
                }

                for(Agent agent : enteringAgents) {
                    agent.enter(this);
                }

                removeFromQueue(floor);
                closed = true;
            }
        }
        else if(!floorQueue.getFloors().isEmpty()) {
            Floor bottomQueueFloor = Floor.getBottomFloor(floorQueue.getFloors());
            Floor topQueueFloor = Floor.getTopFloor(floorQueue.getFloors());
            if(previousQueueSize == 0) {
                List<Floor> edgeFloors = new ArrayList<>();
                edgeFloors.add(bottomQueueFloor);
                edgeFloors.add(topQueueFloor);
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

    public int getAgentCount() {
        return agents.size();
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
