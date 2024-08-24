FROM haproxy:alpine

USER root
RUN apk add --no-cache bash

COPY ../wait-for-it.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/wait-for-it.sh

COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg