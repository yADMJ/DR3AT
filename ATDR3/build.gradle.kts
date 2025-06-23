plugins {
    id("java")
    id("application")
}

group = "Ex"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.0")

    // Logger
    implementation("org.slf4j:slf4j-simple:2.0.16")
}

application {
    mainClass.set("Ex.App")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "Ex.App"
    }
}

tasks.register<JavaExec>("runCliente") {
    group = "application"
    description = "Executa a classe Cliente separadamente"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("Ex.client.Cliente")
}
