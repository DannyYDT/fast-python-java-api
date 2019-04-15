package ch.dubernet.demopythonapi.simulation.events;

public interface SpeakEventHandler extends EventHandler {
    void handleEvent(SpeakEvent event);
}
