# SGlab SCADA Simulator

[](https://github.com/lucijamatulin/SGLab-SCADA-simulator/blob/main/assets/demo_gif.gif)

## General information

Gradle distribution is shipped in this project.

In this readme, Gradle is referred to as `gradle`, 
but the actual command is `gradlew.bat` and in Linux `./gradlew`.

## Development tools and frameworks

- Java 11
- IDE (IntelliJ IDEA Ultimate/Community)

## Project setup

1. Clone Git repository
2. Import project to your IDE as Gradle project
3. Gradle will automatically download all necessary libraries

## Project structure

Project tree:

```
sglab-scada-simulator
| - src/main/java - source code
| - src/main/resources - resources, such as configuration files or static assets
| - build.gradle.kts - project Gradle configuration file written in Kotlin
| - settings.gradle.kts - project settings file written in Kotlin
| - gradle.properties - project variables, such as project version
| - gradlew & gradlew.bat - Gradle executable file for running Gradle tasks
```
## Running project

1. Start `run` Gradle task from your IDE or 
2. or run this command from terminal: `gradle run`
2. or run main function in Java application in your IDE

## Run tests

You can run automatic tests with command: `gradle test`

## Project build

You can build project at once with Gradle command: `gradle build`.
New JAR will be build in `sglab-scada-simulator/build/libs` directory.

## Project release

Releasing the project consists of building the distribution, tagging commits 
and pushing stable source code to `master` branch:

1. Building a project distribution with command: `gradle distZip`.
2. TBD

ZIP distribution consists of:

```
sglab-scada-simulator-<version>.zip
| - bin
|   | - sglab-scada-simulator - Linux bash script for running the simulator
|   | - sglab-scada-simulator.bat - Windiws batch script for running the simulator
| - lib
|   | - sglab-scada-simulator-<version>.jar - simulator executable file
```

## Production

TBD
