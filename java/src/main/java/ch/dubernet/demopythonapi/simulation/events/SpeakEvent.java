package ch.dubernet.demopythonapi.simulation.events;

import ch.dubernet.demopythonapi.simulation.Id;

public class SpeakEvent implements Event {
    private final double time;
    private final Id id;
    private final String message;

    public SpeakEvent(double time, Id id, String message) {
        this.time = time;
        this.id = id;
        this.message = message;
    }

    @Override
    public Id getAgentId() {
        return id;
    }

    @Override
    public double getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
