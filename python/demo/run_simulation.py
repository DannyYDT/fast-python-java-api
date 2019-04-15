import jpype
import jpype.imports as jimport

jimport.registerDomain('ch')
jpype.addClassPath("../../java/target/demo-python-java-api-1.0-SNAPSHOT.jar")
jpype.startJVM(jpype.get_default_jvm_path(), "-Djava.class.path=%s" % jpype.getClassPath())

from ch.dubernet.demopythonapi.simulation import Simulation
from ch.dubernet.demopythonapi.simulation.events import LoggingEventHandler

if __name__ == "__main__":
    simulation = Simulation(10,10)
    simulation.getEvents().addEventHandler(LoggingEventHandler())
    simulation.run()