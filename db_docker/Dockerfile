FROM mysql:latest

MAINTAINER me

ENV MYSQL_DATABASE dbpaymybuddy
ENV MYSQL_ROOT_PASSWORD password

ADD paymybuddyDBMySQL.sql /docker-entrypoint-initdb.d

EXPOSE 3306

