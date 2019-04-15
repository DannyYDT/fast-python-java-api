package ch.dubernet.demopythonapi.simulation.events;

import ch.dubernet.demopythonapi.simulation.Id;

public class JumpEvent implements Event {
    private final double time;
    private final Id id;
    private final double height_m;

    public JumpEvent(double time, Id id, double height_m) {
        this.time = time;
        this.id = id;
        this.height_m = height_m;
    }

    @Override
    public Id getAgentId() {
        return id;
    }

    @Override
    public double getTime() {
        return time;
    }
}
