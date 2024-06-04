package utilities;

import entities.Agent;

public interface AgentLocation {

    void onEnter(Agent agent);
    void onExit(Agent agent);

}
