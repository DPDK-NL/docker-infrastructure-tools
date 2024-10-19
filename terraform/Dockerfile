ARG ALPINE_TAG=3.19.1

FROM alpine:"${ALPINE_TAG}" AS jre-build

ARG JAVA_VERSION=17.0.10_7

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


FROM google/cloud-sdk:473.0.0-alpine

ENV JAVA_HOME=/opt/java/openjdk
COPY --from=jre-build /javaruntime ${JAVA_HOME}
ADD https://releases.hashicorp.com/terraform/1.8.2/terraform_1.8.2_linux_amd64.zip /tmp/
ADD https://releases.hashicorp.com/packer/1.11.2/packer_1.11.2_linux_amd64.zip
/tmp/
COPY files/d-terraform-entrypoint.sh /usr/bin/entrypoint.sh
RUN apk update && apk add bash curl wget git py3-pip \
  && cd /tmp/ && unzip terraform*.zip && mv terraform /usr/sbin/terraform \
  && cd /tmp/ && unzip packer*.zip && mv packer /usr/sbin/terraform \
  && pip install pymongo ansible --break-system-packages \
  && rm -fr /var/cache/apk/*
ENV PATH "${JAVA_HOME}/bin:${PATH}"

ENTRYPOINT [ "entrypoint.sh" ]

