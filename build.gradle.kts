import java.lang.System.getenv

plugins {
    id("java")
    id("application") // adds the 'run' task
    id("edu.sc.seis.launch4j") version "3.0.5"
}

group = "com.zenmo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("javax.json:javax.json-api:1.0")
    implementation("org.glassfish:javax.json:1.1")
    implementation("org.apache.commons:commons-csv:1.10.0")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass = "com.zenmo.Main"
}

launch4j {
    mainClassName = "com.zenmo.Main"
    headerType = "console"
    cmdLine = getenv("ENERGIEKE_REGIO_API_KEY")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.zenmo.Main"
    }
    val dependencies = configurations.runtimeClasspath.get().map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
