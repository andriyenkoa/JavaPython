FROM postgres:latest

COPY sampleSQL.sql /docker-entrypoint-initdb.d/


ENV POSTGRES_USER myuser
ENV POSTGRES_PASSWORD mypassword
ENV POSTGRES_DB mydatabase

