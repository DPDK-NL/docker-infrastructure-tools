ARG ALPINE_TAG=3.21
ARG PHP_VERSION=8.3
ARG PHP_ALPINE=83

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

FROM php:"${PHP_VERSION}-fpm-alpine"
ENV JAVA_HOME=/opt/java/openjdk
ENV PHP_V="php83"
COPY --from=jre-build /javaruntime ${JAVA_HOME}
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

RUN apk update && \
    apk add --no-cache \
    bash \
    curl \
    freetype-dev \
    icu-dev \
    libjpeg-turbo-dev \
    libmcrypt-dev \
    libpng-dev \
    libxslt-dev \
    libzip-dev \
    openssh-client \
    patch \
    wget \
    zip \
    git \
    py3-pip \
    ${PHP_V} \
    ${PHP_V}-cli \
    ${PHP_V}-ctype \
    ${PHP_V}-curl \
    ${PHP_V}-dom \
    ${PHP_V}-fileinfo \
    ${PHP_V}-fpm \
    ${PHP_V}-gd \
    ${PHP_V}-intl \
    ${PHP_V}-mbstring \
    ${PHP_V}-opcache \
    ${PHP_V}-openssl \
    ${PHP_V}-phar \
    ${PHP_V}-session \
    ${PHP_V}-tokenizer \
    ${PHP_V}-soap \
    ${PHP_V}-xml \
    ${PHP_V}-xmlreader \
    ${PHP_V}-xmlwriter \
    ${PHP_V}-simplexml \
    ${PHP_V}-zip \
    ${PHP_V}-pdo \
    ${PHP_V}-pdo_sqlite \
    ${PHP_V}-sqlite3 \
    ${PHP_V}-pdo_mysql \
    ${PHP_V}-mysqlnd \
    ${PHP_V}-mysqli \
    ${PHP_V}-pdo_pgsql \
    ${PHP_V}-pgsql \
    ${PHP_V}-mongodb \
    ${PHP_V}-redis && \
    pip3 install ansible --break-system-packages && \
    addgroup -g 1000 dpdk && \
    adduser -s /bin/bash -u 1000 -S -G dpdk dpdk && \
    rm -fr /var/cache/apk/*

ENV PATH "${JAVA_HOME}/bin:${PATH}"
USER dpdk
ENTRYPOINT [ "" ]
