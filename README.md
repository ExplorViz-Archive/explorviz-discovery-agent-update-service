# ExplorViz Discovery Agent: UpdateService

This repository holds the UpdateService for the Discovery Agent. The UpdateService manages a list of Rules for the rule base engine of the Discovery Agent. 

## Usage
1. Modify `watch.directory` in the `explorviz.properties` file with the path to the rulesfolder.
2. Start the `build/libs/explorviz-backend-extension-discovery-agent-update-service.jar` next to the backend. The folder `Rules` contains already written rules for the  [sampleApplication](https://github.com/ExplorViz/sampleApplication) and the [JPetStore](https://github.com/mybatis/jpetstore-6).


## Setup Development
1. Download the latest [Eclipse IDE for Java Developers](http://www.eclipse.org/downloads/eclipse-packages/) (ExplorViz requires JDK 8 compliance).
2. Clone this repository.
3. Import project into Eclipse: via `Import -> Gradle -> Existing Gradle project -> path/to/the/repo`.
4. Run: `Rightclick on project -> Run as -> Java Application`.