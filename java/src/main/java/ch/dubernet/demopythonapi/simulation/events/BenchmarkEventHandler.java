package ch.dubernet.demopythonapi.simulation.events;

/**
 * An event handler that simply calls every getter on the events, to compare with the cost of accessing them from
 * Python. Totally useless outside of the performance benchmark.
 */
public class BenchmarkEventHandler implements SpeakEventHandler, SingEventHandler, JumpEventHandler {
    @Override
    public void handleEvent(JumpEvent event) {
        event.getAgentId();
        event.getHeight_m();
        event.getTime();
    }

    @Override
    public void handleEvent(SingEvent event) {
        event.getAgentId();
        event.getSong();
        event.getTime();
    }

    @Override
    public void handleEvent(SpeakEvent event) {
        event.getAgentId();
        event.getMessage();
        event.getTime();
    }

    @Override
    public void notifyEnd() {

    }

    @Override
    public void notifyStart() {

    }
}
