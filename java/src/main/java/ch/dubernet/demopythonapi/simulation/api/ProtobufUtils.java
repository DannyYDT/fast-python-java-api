package ch.dubernet.demopythonapi.simulation.api;

import ch.dubernet.demopythonapi.simulation.events.JumpEvent;
import ch.dubernet.demopythonapi.simulation.events.SingEvent;
import ch.dubernet.demopythonapi.simulation.events.SpeakEvent;
import ch.dubernet.demopythonapi.simulation.protobuf.JumpEventOuterClass;
import ch.dubernet.demopythonapi.simulation.protobuf.SingEventOuterClass;
import ch.dubernet.demopythonapi.simulation.protobuf.SpeakEventOuterClass;

public class ProtobufUtils {
    /**
     * Convert to protocol-buffer enum. There might be a cleaner way to do this, but using the protobuf generated enum
     * directly everywhere in the simulation definitely is not clean.
     * In particular, this approach here allows to add the protobuf connection as an extension above the simulation
     * (potentially in another maven artifact).
     *
     * @param song
     * @return
     */
    public static SingEventOuterClass.SingEvent.Song convert(SingEvent.Song song) {
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

    public static SpeakEventOuterClass.SpeakEvent toProtocolBuffer(SpeakEvent event) {
        return SpeakEventOuterClass.SpeakEvent.newBuilder()
                .setAgentId(event.getAgentId().toString())
                .setText(event.getMessage())
                .setTime(event.getTime())
                .build();
    }

    public static SingEventOuterClass.SingEvent toProtocolBuffer(SingEvent event) {
        return SingEventOuterClass.SingEvent.newBuilder()
                .setAgentId(event.getAgentId().toString())
                .setSong(convert(event.getSong()))
                .setTime(event.getTime())
                .build();
    }

    public static JumpEventOuterClass.JumpEvent toProtocolBuffer(JumpEvent event) {
        return JumpEventOuterClass.JumpEvent.newBuilder()
                .setAgentId(event.getAgentId().toString())
                .setHeightM(event.getHeight_m())
                .setTime(event.getTime())
                .build();
    }
}
