# ExplorViz Discovery Agent: UpdateService

This repository holds the UpdateService for the Discovery Agent. The UpdateService manages a list of Rules for the rule base engine of the Discovery Agent. 

## Setup Development / Usage
1. Modify `watch.directory` in the `explorviz.properties` file.
2. Start the `UpdateService.jar` next to the backend.


## Attention
ExplorViz services (including the UpdateService) use [Redis](https://redis.io/) as id generator for distributed and (tba) scalable services.
For your development, you must ensure that a Redis instance is available and running on your machine.
You can use the provided [docker-compose file](https://github.com/ExplorViz/explorviz-backend/tree/dev-1/docker-compose) for an easy setup.