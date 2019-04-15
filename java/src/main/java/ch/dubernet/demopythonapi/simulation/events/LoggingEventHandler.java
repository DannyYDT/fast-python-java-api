package ch.dubernet.demopythonapi.simulation.events;

public class LoggingEventHandler implements JumpEventHandler, SpeakEventHandler, SingEventHandler {
    @Override
    public void handleEvent(JumpEvent event) {
        System.out.println(event.getAgentId()+" jumps "+event.getHeight_m()+"m at time "+event.getTime());
    }

    @Override
    public void handleEvent(SingEvent event) {
        System.out.println(event.getAgentId()+" sings "+event.getSong()+" at time "+event.getTime());
    }

    @Override
    public void handleEvent(SpeakEvent event) {
        System.out.println(event.getAgentId()+" says "+event.getMessage()+" at time "+event.getTime());
    }

    @Override
    public void notifyEnd() {
        System.out.println("end simulation");
    }

    @Override
    public void notifyStart() {
        System.out.println("start simulation");
    }
}
