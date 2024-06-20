package entities.building.elevator;

import entities.building.Floor;
import utilities.Direction;

import java.util.*;

public class FloorRequestQueue {

    private final List<Floor> floors;
    private final List<Direction> direction;

    public FloorRequestQueue() {
        this.floors = new ArrayList<Floor>();
        this.direction = new ArrayList<>();
    }

    public void addFloor(Floor floor, Direction direction) {
        this.floors.add(floor);
        this.direction.add(direction);
    }

    public void remove(Floor floor) {
        int index = this.floors.indexOf(floor);
        this.floors.remove(index);
        this.direction.remove(index);
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public Floor getTopFloor() {
        if(floors.isEmpty())
            return null;
        return Floor.getTopFloor(floors);
    }

    public Floor getBottomFloor() {
        if(floors.isEmpty())
            return null;
        return Floor.getBottomFloor(floors);
    }

    public Direction getDirection(Floor floor) {
        return this.direction.get(this.floors.indexOf(floor));
    }

    public boolean contains(Floor floor) {
        return this.floors.contains(floor);
    }

    public int size() {
        return this.floors.size();
    }

    public boolean isEmpty() {
        return this.floors.isEmpty();
    }

}
