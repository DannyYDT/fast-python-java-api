import jpype
import jpype.imports as jimport

# This would be better separated somewhere else
# in any case, path to JAR should be defined somewhere more "central"
jimport.registerDomain('ch')
jpype.addClassPath("../../java/target/demo-python-java-api-1.0-SNAPSHOT-jar-with-dependencies.jar")
jpype.startJVM(jpype.get_default_jvm_path(), "-Djava.class.path=%s" % jpype.getClassPath())

from ch.dubernet.demopythonapi.simulation.api import ProtobufAdapter

from api.protobuf.JumpEvent_pb2 import JumpEvent
from api.protobuf.SingEvent_pb2 import SingEvent
from api.protobuf.SpeakEvent_pb2 import SpeakEvent

def python_event_handler(handler):
    """
    Annotation to annotate python classes that "implement" and event handler

    :param handler:
    :return:
    """
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
