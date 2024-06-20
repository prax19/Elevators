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

        System.out.println("\nPassenger load 1:");
        building.addAgent(6, 0);
        building.addAgent(3, 0);
        building.addAgent(8, 0);
        building.addAgent(2, 0);

        while(!building.isIdle()) {
            building.update();
            printElevatorSystem(building);
        }

        ElevatorsAssertions.assertAllAgentsReachedTarget(building);

        System.out.println("\nPassenger load 2:");
        building.addAgent(1, 0);
        building.addAgent(0, 5);
        building.addAgent(9, -1);
        building.addAgent(-2, -1);

        while(!building.isIdle()) {
            building.update();
            printElevatorSystem(building);
        }

        ElevatorsAssertions.assertAllAgentsReachedTarget(building);

    }

    @Test
    @DisplayName("Test case 2")
    public void testCase2() {
        Building building = new Building(-4, 4, 2);

        building.addAgent(-3, 0);

        while(!building.isIdle()) {
            building.update();
            printElevatorSystem(building);
        }

    }

    @Test
    @DisplayName("Test case 3")
    public void testCase3() {
        Building building = new Building(-4, 3, 2);

        building.addAgent(-2, 0);
        building.addAgent(2, 0);

        while(!building.isIdle()) {
            building.update();
            printElevatorSystem(building);
        }

    }

    @Test
    @DisplayName("Test case 4")
    public void testCase4() {
        Building building = new Building(-4, 5, 2);

        building.addAgent(-2, -4);
        building.addAgent(-2, 4);

        while(!building.isIdle()) {
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
        if(!elevator.isOpened())
            System.out.printf("[%2s]", elevator.getAgentCount());
        else
            System.out.printf("|%2s|", elevator.getAgentCount());
    }

    private void printEmptyFloor() {
        System.out.print("----");
    }

}
