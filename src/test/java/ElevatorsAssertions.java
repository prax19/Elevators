import entities.Agent;
import entities.building.Building;
import entities.building.Floor;
import org.junit.jupiter.api.Assertions;

public class ElevatorsAssertions extends Assertions {

    public static void assertAgentReachedTarget(Agent agent) {
        Assertions.assertEquals(agent.getTargetFloor(), agent.getCurrentLocation());
    }

    public static void assertAllAgentsReachedTarget(Building context) {
        for (Agent agent : context.getAgents())
            assertAgentReachedTarget(agent);
    }

}
