plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.2"
    id("org.graalvm.buildtools.native") version "0.11.1"
}

group = "top.monkeyfans.vector"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    targetCompatibility = JavaVersion.VERSION_25
    sourceCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "top.monkeyfans.vector.KoalaVector"
    }
}

tasks.compileJava {
    options.release = 25
}
graalvmNative {
    metadataRepository{
        enabled.set(true)
    }
    binaries.all {
        buildArgs.add("--exact-reachability-metadata")
        runtimeArgs.add("-XX:MissingRegistrationReportingMode=Warn")
    }
    binaries {
        named("main") {
            imageName.set("koala-vector")
            mainClass.set("top.monkeyfans.vector.KoalaVector")
            buildArgs.add("-O3")
//            buildArgs.add("--gc=G1")
            buildArgs.add("--no-fallback")
            buildArgs.add("--static")
            buildArgs.add("--libc=musl")
            buildArgs.add("--initialize-at-run-time=ch.qos.logback,org.slf4j")
            buildArgs.add("--enable-url-protocols=http")
            jvmArgs.add("-Dundertow.options.server.ALLOW_ENCODED_SLASH=true")
        }
        named("test") {
            quickBuild.set(true)
            debug.set(true)
        }
    }
}
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