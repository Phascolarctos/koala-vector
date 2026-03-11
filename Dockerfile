FROM gradle:9.4.0-jdk25 AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src

RUN gradle clean build --no-daemon

FROM ghcr.io/graalvm/native-image-community:25-muslib as native

WORKDIR /native

COPY --from=build /app/build/libs/*.jar App.jar

RUN native-image -Ob --no-fallback --initialize-at-build-time --enable-url-protocols=http -jar App.jar koala-vector

FROM scratch

WORKDIR /opt/app

# Copy the native executable
COPY --from=native /native/koala-vector koala-vector

EXPOSE 8080

ENTRYPOINT ["./koala-vector"]