import jpype
import jpype.imports as jimport

# This would be better separated somewhere else
# in any case, path to JAR should be defined somewhere more "central"
jimport.registerDomain('ch')
jpype.addClassPath("../../java/target/demo-python-java-api-1.0-SNAPSHOT-jar-with-dependencies.jar")
jpype.startJVM(jpype.get_default_jvm_path(), "-Djava.class.path=%s" % jpype.getClassPath())

from ch.dubernet.demopythonapi.simulation.api import ProtobufAdapter, ProtobufBufferedAdapter

from api.protobuf.JumpEvent_pb2 import JumpEvent
from api.protobuf.SingEvent_pb2 import SingEvent
from api.protobuf.SpeakEvent_pb2 import SpeakEvent
from api.protobuf.EventBuffer_pb2 import EventBuffer, EventContainer

# Simple method: simply functions to wrap instances

def create_event_handler(handler):
    class ProtobufHandler:
        def notifyStart(self):
            if hasattr(handler, "notifyStart"):
                handler.notifyStart()

        def notifyEnd(self):
            if hasattr(handler, "notifyEnd"):
                handler.notifyEnd()

        def handleJumpEventMessage(self, message):
            if hasattr(handler, "handleJumpEvent"):
                event = JumpEvent()
                event.ParseFromString(message[:])
                handler.handleJumpEvent(event)

        def handleSingEventMessage(self, message):
            if hasattr(handler, "handleSingEvent"):
                event = SingEvent()
                event.ParseFromString(message[:])
                handler.handleSingEvent(event)

        def handleSpeakEventMessage(self, message):
            if hasattr(handler, "handleSpeakEvent"):
                event = SpeakEvent()
                event.ParseFromString(message[:])
                handler.handleSpeakEvent(event)

    impl = jpype.JProxy("ch.dubernet.demopythonapi.simulation.api.ProtobufEventHandler", inst=ProtobufHandler())
    return ProtobufAdapter(impl)


def create_buffered_event_handler(handler, buffer_size):
    class ProtobufHandler:
        def notifyStart(self):
            if hasattr(handler, "notifyStart"):
                handler.notifyStart()

        def notifyEnd(self):
            if hasattr(handler, "notifyEnd"):
                handler.notifyEnd()

        def handleEventBuffer(self, message):
            buffer = EventBuffer()
            buffer.ParseFromString(message[:])

            for event in buffer.events:
                event_type = event.WhichOneof("event")
                if event_type == "jumpEvent" and hasattr(handler, "handleJumpEvent"):
                    handler.handleJumpEvent(event.jumpEvent)
                elif event_type == "singEvent" and hasattr(handler, "handleSingEvent"):
                    handler.handleSingEvent(event.singEvent)
                elif event_type == "speakEvent" and hasattr(handler, "handleSpeakEvent"):
                    handler.handleSpeakEvent(event.speakEvent)

    impl = jpype.JProxy("ch.dubernet.demopythonapi.simulation.api.ProtobufEventBufferHandler", inst=ProtobufHandler())
    return ProtobufBufferedAdapter(impl, buffer_size)


# Fancy method: class decorators

def event_handler(handler_class):
    """
    Decorator to annotate a class and transform it to a Protobuf-mediated event handler.
    There are some hoops we need to jump through, because we want to "return" a Java class, not a Python one...
    The way we solve this is to return a constructor method for the Java object, that exposes the same
    interface as the python class constructor.
    This should be transparent to the user in 99% of cases.

    :param handler_class:
    :return:
    """
    def create_adapter(*args, **kwargs):
       return create_event_handler(handler_class(*args, **kwargs))

    return create_adapter


def buffered_event_handler(buffer_size):
    """
    Decorator to annotate a class and transform it to a buffered Protobuf-mediated event handler.
    There are some hoops we need to jump through, because we want to "return" a Java class, not a Python one...
    The way we solve this is to return a constructor method for the Java object, that exposes the same
    interface as the python class constructor.
    This should be transparent to the user in 99% of cases.

    :param buffer_size:
    :return:
    """
    # the function is actually a decorator constructor, because we need arguments. This is the actual decorator.
    def decorator(handler_class):
        def create_adapter(*args, **kwargs):
            return create_buffered_event_handler(handler_class(*args, **kwargs), buffer_size)

        return create_adapter

    return decorator
