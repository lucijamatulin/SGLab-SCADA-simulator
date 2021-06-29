plugins {
    id("java")
    id("application")
    id("org.sonarqube") version "3.0"
    id("jacoco")
}

group = "hr.fer.zvne"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    val jUnitVersion = "5.3.1"
    val jacksonVersion = "2.12.1"
    val javalinVersion = "3.13.7"
    val slf4jVersion = "1.7.30"
    val logbackVersion = "1.2.3"

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")

    /* Web */
    implementation("io.javalin:javalin:$javalinVersion")

    /* Logging */
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

}

application {
    mainClassName = "hr.fer.zvne.scada.simulator.Main"
}

tasks.jar {
    manifest {
        attributes(Pair("Main-Class", "hr.fer.zvne.scada.simulator.Main"))
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

sonarqube {
    properties {
        property("sonar.host.url", "https://applygit.zvne.fer.hr/sonar")
        property("sonar.login", project.properties["sonarUser"]?.toString() ?: "")
        property("sonar.password", project.properties["sonarPassword"]?.toString() ?: "")
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.sonarqube {
    dependsOn(tasks.test)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
    }
}

