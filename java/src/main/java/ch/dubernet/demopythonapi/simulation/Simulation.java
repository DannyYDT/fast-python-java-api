package ch.dubernet.demopythonapi.simulation;

import ch.dubernet.demopythonapi.simulation.events.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final List<Id> agentIds;
    private final int nTimeSteps;

    private final Random random = new Random(42);
    private final EventManager events = new EventManager();

    private final List<EventCreator> eventCreators =
            Arrays.asList(
                    (time, agent, random) -> {
                        byte[] bytes = new byte[7];
                        random.nextBytes(bytes);
                        return new SpeakEvent(time, agent, new String(bytes));
                    },
                    (time, agent, random) ->
                            new SingEvent(
                                    time,
                                    agent,
                                    SingEvent.Song.values()[random.nextInt(SingEvent.Song.values().length)]),
                    (time, agent, random) ->
                            new JumpEvent(time, agent, random.nextDouble() * 5)
            );

    public Simulation(int nAgents, int nTimeSteps) {
        this.nTimeSteps = nTimeSteps;
        agentIds = new ArrayList<>(nAgents);
        for (int i=0; i < nAgents; i++) {
            agentIds.add(new Id(i));
        }
    }

    public EventManager getEvents() {
        return events;
    }

    public void run() {
        events.handleStartOfSimulation();
        for (int time=0; time < nTimeSteps; time++) {
            for (Id agent : agentIds) {
                events.handleEvent(createEvent(time, agent));
            }
        }
        events.handleEndOfSimulation();
    }

    private Event createEvent(int time, Id agent) {
        final EventCreator creator = eventCreators.get(random.nextInt(eventCreators.size()));
        return creator.createEvent(time, agent, random);
    }

    private interface EventCreator {
        Event createEvent(double time, Id agent, Random random);
    }
}
