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

        Building building = new Building(-2, 10, 2);

        building.addAgent(6, 0);
        building.addAgent(3, 0);
        building.addAgent(8, 0);
        building.addAgent(2, 0);

        for (int i = 0; i < 100; i++) {
            building.update();
            printElevatorSystem(building);
        }

        ElevatorsAssertions.assertAllAgentsReachedTarget(building);

        System.out.println();
        building.addAgent(1, 0);
        building.addAgent(0, 5);
        building.addAgent(9, -1);
        building.addAgent(-2, -1);

        for (int i = 0; i < 100; i++) {
            building.update();
            printElevatorSystem(building);
        }

        ElevatorsAssertions.assertAllAgentsReachedTarget(building);

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
