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
        final JumpEventOuterClass.JumpEvent pb =
                JumpEventOuterClass.JumpEvent.newBuilder()
                        .setAgentId(event.getAgentId().toString())
                        .setHeightM(event.getHeight_m())
                        .setTime(event.getTime())
                        .build();

        delegate.handleJumpEventMessage(pb.toByteArray());
    }

    @Override
    public void handleEvent(SingEvent event) {
        final SingEventOuterClass.SingEvent pb =
                SingEventOuterClass.SingEvent.newBuilder()
                        .setAgentId(event.getAgentId().toString())
                        .setSong(convert(event.getSong()))
                        .setTime(event.getTime())
                        .build();

        delegate.handleSingEventMessage(pb.toByteArray());
    }

    /**
     * Convert to protocol-buffer enum. There might be a cleaner way to do this, but using the protobuf generated enum
     * directly everywhere in the simulation definitely is not clean.
     * In particular, this approach here allows to add the protobuf connection as an extension above the simulation
     * (potentially in another maven artifact).
     *
     * @param song
     * @return
     */
    private SingEventOuterClass.SingEvent.Song convert(SingEvent.Song song) {
        switch (song) {
            case oldMacDonalds:
                return SingEventOuterClass.SingEvent.Song.OLD_MC_DONALDS;
            case ifYouAreHappy:
                return SingEventOuterClass.SingEvent.Song.IF_YOU_ARE_HAPPY;
            case NinetyNineBottles:
                return SingEventOuterClass.SingEvent.Song.NINETY_NINE_BOTTLES;
        }
        throw new IllegalArgumentException(song.toString());
    }

    @Override
    public void handleEvent(SpeakEvent event) {
        final SpeakEventOuterClass.SpeakEvent pb =
                SpeakEventOuterClass.SpeakEvent.newBuilder()
                        .setAgentId(event.getAgentId().toString())
                        .setText(event.getMessage())
                        .setTime(event.getTime())
                        .build();

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
