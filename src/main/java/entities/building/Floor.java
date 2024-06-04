package entities.building;

import entities.Agent;
import simulation.SimulationEntity;
import utilities.AgentLocation;
import utilities.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Floor implements SimulationEntity, AgentLocation {

    private final int level;
    private List<Agent> agents;

    public Floor(int level) {
        this.level = level;
        agents = new ArrayList<>();
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

    }

    public int getLevel() {
        return level;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public static List<Floor> getFloorsInRange(int minLevel, int maxLevel) {
        if(minLevel > maxLevel)
            throw new IllegalArgumentException("Minimal level greater than maximal");
        List<Floor> floors = new ArrayList();
        for(int i = minLevel; i < maxLevel; i++)
            floors.add(new Floor(i));
        return floors;
    }

    public static Floor getTopFloor(List<Floor> floors) {
        if(floors.isEmpty())
            throw new IllegalArgumentException("No floors found");
        Floor topFloor = floors.getFirst();
        for(Floor floor : floors)
            if(topFloor.getLevel() < floor.getLevel())
                topFloor = floor;
        return topFloor;
    }

    public static Floor getBottomFloor(List<Floor> floors) {
        if(floors.isEmpty())
            throw new IllegalArgumentException("No floors found");
        Floor bottomFloor = floors.getFirst();
        for(Floor floor : floors)
            if(bottomFloor.getLevel() > floor.getLevel())
                bottomFloor = floor;
        return bottomFloor;
    }

    public static Floor getGroundFloor(List<Floor> floors) {
        return getFloor(floors, 0);
    }

    public static Floor getFloor(List<Floor> floors, int level) {
        if(floors.isEmpty())
            throw new IllegalArgumentException("No floors found");
        for(Floor floor : floors)
            if(floor.getLevel() == level)
                return floor;
        throw new NoSuchElementException("No such floor found");
    }

    public static Floor nextFloor(List<Floor> floors, Floor currentFloor, Direction direction) {
        if(floors.isEmpty())
            throw new IllegalArgumentException("No floors found");
        for(Floor floor : floors) {
            if(floor.getLevel() == currentFloor.getLevel() + 1 && direction == Direction.UP)
                return floor;
            if(floor.getLevel() == currentFloor.getLevel() - 1 && direction == Direction.DOWN)
                return floor;
        }
        throw new NoSuchElementException("No such floor found");
    }

    public static Direction determineDirection(List<Floor> floors, Floor initialFloor, Floor targetFloor) {
        if(floors.isEmpty())
            throw new IllegalArgumentException("No floors found");
        if(initialFloor.getLevel() == targetFloor.getLevel())
            throw new IllegalArgumentException("Both floors are the same");
        if(initialFloor.getLevel() < targetFloor.getLevel())
            return Direction.UP;
        else
            return Direction.DOWN;
    }

    public static Floor getNearestFloor(List<Floor> floors, Floor targetFloor) {
        if (floors.isEmpty())
            throw new IllegalArgumentException("No floors found");

        Floor nearestFloor = floors.get(0);
        int minDistance = Math.abs(nearestFloor.getLevel() - targetFloor.getLevel());

        for (Floor floor : floors) {
            int distance = Math.abs(floor.getLevel() - targetFloor.getLevel());
            if (distance < minDistance) {
                nearestFloor = floor;
                minDistance = distance;
            }
        }
        return nearestFloor;
    }

}
