import entities.Agent;
import entities.building.Building;
import entities.building.Floor;
import entities.building.elevator.Elevator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Elevator simulation tests")
public class SimulationTests {

    @Test
    @DisplayName("Test case 1")
    public void testCase1() {

    }

    @Test
    public void testTest() {

        Building building = new Building(-2, 10, 2);

        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 6), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 3), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 8), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 2), Floor.getFloor(building.getFloors(), 0)));

        for (int i = 0; i < 100; i++) {
            building.update();
            printElevatorSystem(building);
        }

        System.out.println();
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 1), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 0), Floor.getFloor(building.getFloors(), 5)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 9), Floor.getFloor(building.getFloors(), -1)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), -2), Floor.getFloor(building.getFloors(), -1)));

        for (int i = 0; i < 100; i++) {
            building.update();
            printElevatorSystem(building);
        }
    }

    private void printElevatorSystem(Building building) {
        for(Floor floor: building.getFloors())
            System.out.printf(" %2s ", floor.getAgents().size());
        System.out.println();
        for(Elevator elevator: building.getElevatorSystem().getElevators())
            printElevatorShaft(building, elevator);
        System.out.println("\n");
    }

    private void printElevatorShaft(Building building, Elevator elevator) {
        for(Floor floor: building.getFloors()) {
            if(elevator.getFloor() == floor)
                printElevator(elevator);
            else
                printEmptyFloor();
        }
        for(Floor entry: elevator.getFloorQueue().getFloors())
            System.out.print(" " + entry.getLevel() + ", " + elevator.getFloorQueue().getDirection(entry) + "; ");
        System.out.println();
    }

    private void printElevator(Elevator elevator) {
        System.out.printf("[%2s]", elevator.getAgentCount());
    }

    private void printEmptyFloor() {
        System.out.print("----");
    }

}
