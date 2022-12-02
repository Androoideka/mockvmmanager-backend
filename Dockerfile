# syntax=docker/dockerfile:1
ARG APP_NAME=vm-manager

FROM androoideka/alpine-angular:latest AS angular
ARG APP_NAME
COPY --from=frontend . /repo
RUN (cd /repo && npm install && ng build --configuration production) \
    && mv "/repo/dist/${APP_NAME}" /build

FROM androoideka/alpine-java:latest AS spring
COPY . /repo
COPY --from=angular /build /repo/src/main/resources/public
RUN (cd /repo && mvn clean package -Pportfolio)

FROM scratch AS exporter
COPY --from=spring /repo/target/vm-manager.jar .