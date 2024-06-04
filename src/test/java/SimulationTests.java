import entities.building.Building;
import entities.building.Floor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

@DisplayName("Elevator simulation tests")
public class SimulationTests {

    private Building building;

    @BeforeEach
    public void setup() {

        building = new Building(-2, 10, 2);

    }

    @Test
    @DisplayName("Building setup tests")
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

}
