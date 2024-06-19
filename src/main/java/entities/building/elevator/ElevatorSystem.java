package entities.building.elevator;

import entities.Agent;
import entities.building.Building;
import entities.building.Floor;
import simulation.SimulationEntity;
import utilities.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorSystem implements SimulationEntity {

    private final Building context;
    private final List<Agent> agents;

    private final List<Elevator> elevators;

    public ElevatorSystem(Building context, int numberOfElevators) {
        this.context = context;
        this.agents = context.getAgents();
        elevators = new ArrayList();
        for(int i = 0; i < numberOfElevators; i++)
            elevators.add(
                    new Elevator(context.getFloors(), Floor.getGroundFloor(context.getFloors()))
            );
    }

    // Calls idle or on-the-way elevator.
    // Equivalent of calling elevator from an up/down control panel on each floor.
    public void callElevator(Floor floor, Direction direction) {
        Elevator elevator = selectElevator(floor, direction);
        elevator.addToQueue(floor, direction);

    }

    // Send specific elevator to specific floor.
    // Equivalent of selecting floor from an elevator control panel.
    public void sendElevator(Elevator elevator, Floor floor) {
        elevator.addToQueue(floor, elevator.getDirection());
    }

    public Elevator selectElevator(Floor targetFloor, Direction direction) {

        Elevator selectedElevator = null;
        int minDistance = Integer.MAX_VALUE;

        List<Elevator> selectedElevators = new ArrayList<>();
        for(Elevator elevator : elevators) {
            boolean isGettingCloser = !Floor.isGettingFarther(targetFloor, elevator.getFloor(), elevator.getDirection());
            boolean isIdle = elevator.isIdle();
            if(isGettingCloser)
                selectedElevators.addFirst(elevator);
            else if(isIdle)
                selectedElevators.addLast(elevator);
        }
        if(selectedElevators.isEmpty())
            selectedElevator = elevators.getFirst();
        else {
            for(Elevator elevator : selectedElevators) {

                int distance = Math.abs(elevator.getFloor().getLevel() - targetFloor.getLevel());
                if(distance < minDistance) {
                    minDistance = distance;
                    selectedElevator = elevator;
                }
            }
        }

        return selectedElevator;

    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void update() {
        for(Elevator elevator : elevators) {
            elevator.update();
        }
    }

    @Override
    public boolean isIdle() {
        boolean idle = true;
        for(Elevator elevator: elevators)
            if(!elevator.isIdle()) {
                idle = false;
                break;
            }
        return idle;
    }

}
