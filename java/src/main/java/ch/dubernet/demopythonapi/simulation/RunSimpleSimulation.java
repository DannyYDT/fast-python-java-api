package ch.dubernet.demopythonapi.simulation;

import ch.dubernet.demopythonapi.simulation.events.LoggingEventHandler;

public class RunSimpleSimulation {
    public static void main(String... args) {
        Simulation simulation = new Simulation(3, 3);
        simulation.getEvents().addEventHandler(new LoggingEventHandler());
        simulation.run();
    }
}
