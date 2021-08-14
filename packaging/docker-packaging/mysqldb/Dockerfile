FROM mysql:latest
COPY db.sql /docker-entrypoint-initdb.d/
COPY db_restore.sh /docker-entrypoint-initdb.d/