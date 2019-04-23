package ch.dubernet.demopythonapi.simulation.api;

import ch.dubernet.demopythonapi.simulation.events.*;
import ch.dubernet.demopythonapi.simulation.protobuf.EventBufferOuterClass;

import java.util.ArrayList;
import java.util.List;

public class ProtobufBufferedAdapter implements JumpEventHandler, SpeakEventHandler, SingEventHandler {
    private final ProtobufEventBufferHandler delegate;

    private final List<EventBufferOuterClass.EventContainer> buffer;
    private final int bufferSize;

    public ProtobufBufferedAdapter(ProtobufEventBufferHandler delegate, int bufferSize) {
        this.delegate = delegate;
        this.buffer = new ArrayList<>(bufferSize);
        this.bufferSize = bufferSize;
    }

    @Override
    public void handleEvent(JumpEvent event) {
        handleContainer(ProtobufUtils.toEventContainer(event));
    }

    @Override
    public void handleEvent(SingEvent event) {
        handleContainer(ProtobufUtils.toEventContainer(event));
    }

    @Override
    public void handleEvent(SpeakEvent event) {
        handleContainer(ProtobufUtils.toEventContainer(event));
    }

    private void handleContainer(EventBufferOuterClass.EventContainer container) {
        buffer.add(container);

        if (buffer.size() >= bufferSize) {
            flushBuffer();
        }
    }

    private void flushBuffer() {
        delegate.handleEventBuffer(ProtobufUtils.toEventBuffer(buffer).toByteArray());
        buffer.clear();
    }

    @Override
    public void notifyEnd() {
        if (!buffer.isEmpty()) {
            flushBuffer();
        }
        this.delegate.notifyEnd();
    }

    @Override
    public void notifyStart() {
        this.delegate.notifyStart();
    }
}
