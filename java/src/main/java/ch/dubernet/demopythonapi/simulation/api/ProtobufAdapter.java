package ch.dubernet.demopythonapi.simulation.api;

import ch.dubernet.demopythonapi.simulation.events.*;
import ch.dubernet.demopythonapi.simulation.protobuf.JumpEventOuterClass;
import ch.dubernet.demopythonapi.simulation.protobuf.SingEventOuterClass;
import ch.dubernet.demopythonapi.simulation.protobuf.SpeakEventOuterClass;

public class ProtobufAdapter implements JumpEventHandler, SingEventHandler, SpeakEventHandler {
    private final ProtobufEventHandler delegate;

    public ProtobufAdapter(ProtobufEventHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handleEvent(JumpEvent event) {
        final JumpEventOuterClass.JumpEvent pb = ProtobufUtils.toProtocolBuffer(event);

        delegate.handleJumpEventMessage(pb.toByteArray());
    }

    @Override
    public void handleEvent(SingEvent event) {
        final SingEventOuterClass.SingEvent pb = ProtobufUtils.toProtocolBuffer(event);

        delegate.handleSingEventMessage(pb.toByteArray());
    }

    @Override
    public void handleEvent(SpeakEvent event) {
        final SpeakEventOuterClass.SpeakEvent pb = ProtobufUtils.toProtocolBuffer(event);

        delegate.handleSpeakEventMessage(pb.toByteArray());
    }

    @Override
    public void notifyEnd() {
        this.delegate.notifyEnd();
    }

    @Override
    public void notifyStart() {
        this.delegate.notifyStart();
    }
}
