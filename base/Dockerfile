ARG ALPINE_TAG=3.19.1

FROM alpine:"${ALPINE_TAG}" AS jre-build

ARG JAVA_VERSION=17.0.13_11

SHELL ["/bin/ash", "-o", "pipefail", "-c"]

COPY files/jdk-download-url.sh /usr/bin/jdk-download-url.sh
COPY files/jdk-download.sh /usr/bin/jdk-download.sh

RUN apk add --no-cache \
  ca-certificates \
  jq \
  curl \
  && rm -fr /var/cache/apk/* \
  && /usr/bin/jdk-download.sh alpine

ENV PATH=/opt/jdk-${JAVA_VERSION}/bin:$PATH

RUN jlink \
  --add-modules ALL-MODULE-PATH \
  --strip-java-debug-attributes \
  --no-man-pages \
  --no-header-files \
  --compress=2 \
  --output /javaruntime


FROM alpine:"${ALPINE_TAG}"

ENV JAVA_HOME=/opt/java/openjdk
COPY --from=jre-build /javaruntime ${JAVA_HOME}
COPY files/entrypoint.sh /usr/bin/entrypoint.sh
RUN apk update \
  && apk add --no-cache docker-cli \
  && rm -fr /var/cache/apk/*
ENV PATH "${JAVA_HOME}/bin:${PATH}"

ENTRYPOINT [ "entrypoint.sh" ]

