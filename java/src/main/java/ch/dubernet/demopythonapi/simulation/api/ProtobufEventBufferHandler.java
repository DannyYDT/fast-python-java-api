package ch.dubernet.demopythonapi.simulation.api;

public interface ProtobufEventBufferHandler {
    void notifyStart();
    void notifyEnd();

    void handleEventBuffer(byte[] message);
}
