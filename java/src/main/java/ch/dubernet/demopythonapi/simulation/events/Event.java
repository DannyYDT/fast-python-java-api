package ch.dubernet.demopythonapi.simulation.events;

import ch.dubernet.demopythonapi.simulation.Id;

public interface Event {
    Id getAgentId();
    double getTime();
}
