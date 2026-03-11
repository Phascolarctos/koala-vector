FROM eclipse-temurin:25-alpine-3.23 AS builder

WORKDIR /build

COPY gradle ./gradle
COPY gradlew .
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod u+x ./gradlew

COPY src ./src
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:25-alpine-3.23 AS jre-builder
WORKDIR /jre-gen

RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.desktop,java.management,java.naming,java.security.jgss,java.security.sasl,java.sql,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /runtime

FROM alpine:3.23
LABEL maintainer="koala-vector"

# 设置环境变量
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

# 从前两个阶段拷贝产物
COPY --from=jre-builder /runtime $JAVA_HOME
WORKDIR /app

COPY --from=builder /build/build/libs/*-all.jar app.jar

RUN apk add --no-cache gcompat

EXPOSE 8080

ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Dfile.encoding=UTF-8", \
            "-jar", "app.jar"]