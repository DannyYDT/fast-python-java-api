package ch.dubernet.demopythonapi.simulation.api;

public interface ProtobufEventHandler {
    void notifyStart();
    void notifyEnd();

    // Methods need to be specific to an event type for two reasons:
    // - dynamic dispatch obviously does not work
    // - protocol buffer conversion requires knowing the type of message beforehand
    void handleJumpEventMessage(byte[] message);
    void handleSingEventMessage(byte[] message);
    void handleSpeakEventMessage(byte[] message);
}
