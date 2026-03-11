plugins {
    id("java")
}

group = "top.monkeyfans.vector"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // undertow
    implementation("io.undertow:undertow-core:2.4.0.RC1")
    implementation("io.undertow:undertow-servlet:2.3.23.Final")
    implementation("io.undertow:undertow-websockets-jsr:2.3.23.Final")
    // logback
    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("net.logstash.logback:logstash-logback-encoder:9.0")
    // junit5
    testImplementation(platform("org.junit:junit-bom:6.1.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}