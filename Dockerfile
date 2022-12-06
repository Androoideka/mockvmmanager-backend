# syntax=docker/dockerfile:1
ARG APP_NAME=vm-manager

FROM androoideka/alpine-angular:latest AS angular
ARG APP_NAME
COPY --from=frontend . /repo
RUN (cd /repo && npm install && ng build --configuration production) \
    && mv "/repo/dist/${APP_NAME}" /build

FROM androoideka/alpine-java:latest AS spring
ARG APP_NAME
COPY . /repo
COPY --from=angular /build /repo/src/main/resources/public
RUN (cd /repo && mvn clean package -Pportfolio) && param="" \
    && { ! jar -tf "/repo/target/${APP_NAME}.jar" | grep -q BOOT-INF/lib \
    || jar -xf "/repo/target/${APP_NAME}.jar" \
    && param="--class-path=/BOOT-INF/lib/*"; } \
    && jdeps --ignore-missing-deps --print-module-deps --recursive --multi-release 17 \
    $param "/repo/target/${APP_NAME}.jar" > deps.txt

FROM androoideka/alpine-java-winpack:latest AS packager
ARG APP_NAME
COPY --from=spring "/repo/target/${APP_NAME}.jar" /app/jar/
COPY --from=spring deps.txt /app/
RUN wine64 $JAVA_WIN/bin/jlink.exe \
    --add-modules `cat /app/deps.txt` \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /app/jre \
    && wine64 $JAVA_WIN/bin/jpackage.exe \ 
    --type app-image \
    --win-console \
    --input /app/jar \
    --name "${APP_NAME}" \
    --main-jar "${APP_NAME}.jar" \
    --runtime-image /app/jre \
    --dest /opt/app \
    && (cd "/opt/app/${APP_NAME}" && zip -r - .) > "${APP_NAME}.zip"

FROM scratch AS exporter
ARG APP_NAME
COPY --from=packager "${APP_NAME}.zip" .