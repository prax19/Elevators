import entities.Agent;
import entities.building.Building;
import entities.building.Floor;
import entities.building.elevator.Elevator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.Direction;

import java.util.List;
import java.util.NoSuchElementException;

@DisplayName("Miscellaneous system tests")
public class MiscSystemTests {

    private Building building;

    @BeforeEach
    public void setup() {

        building = new Building(-2, 10, 2);

        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 6), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 3), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 8), Floor.getFloor(building.getFloors(), 0)));
        building.getAgents().add(new Agent(building, Floor.getFloor(building.getFloors(), 2), Floor.getFloor(building.getFloors(), 0)));

    }

    @Test
    @DisplayName("Building setup test")
    public void buildingSetupTest() {

        List<Floor> floors = building.getFloors();

        Assertions.assertEquals(-2, Floor.getBottomFloor(floors).getLevel());
        Assertions.assertEquals(9, Floor.getTopFloor(floors).getLevel());
        Assertions.assertEquals(0, Floor.getGroundFloor(floors).getLevel());

        Assertions.assertEquals(2, building.getElevatorSystem().getElevators().size());

        Assertions.assertThrows(
            NoSuchElementException.class,
            () -> { Floor.getFloor(floors, 10); }
        );

    }

    @Test
    public void floorClassStaticFunctionsTest() {

        List<Floor> floors = building.getFloors();

        Floor floor0 = Floor.getFloor(floors, 0);
        Floor floor9 = Floor.getFloor(floors, 9);
        Floor floor5 = Floor.getFloor(floors, 5);
        Floor floorMinus2 = Floor.getFloor(floors, -2);

        Assertions.assertEquals(floor9, Floor.getFloorsSortedByDistance(floors, floorMinus2).getLast());
        Assertions.assertEquals(floorMinus2, Floor.getFloorsSortedByDistance(floors, floor5).getLast());

        Assertions.assertTrue(Floor.isGettingFarther(floor0, floor5, Direction.UP));
        Assertions.assertTrue(Floor.isGettingFarther(floor0, floorMinus2, Direction.DOWN));
        Assertions.assertFalse(Floor.isGettingFarther(floor0, floorMinus2, Direction.UP));

    }

}
