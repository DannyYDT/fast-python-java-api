package ch.dubernet.demopythonapi.simulation.events;

import ch.dubernet.demopythonapi.simulation.Id;

public class SingEvent implements Event{
    private final double time;
    private final Id id;
    private final Song song;

    public enum Song {oldMacDonalds, ifYouAreHappy, NinetyNineBottles}

    public SingEvent(double time, Id id, Song song) {
        this.time = time;
        this.id = id;
        this.song = song;
    }

    @Override
    public Id getAgentId() {
        return id;
    }

    @Override
    public double getTime() {
        return time;
    }

    public Song getSong() {
        return song;
    }
}
