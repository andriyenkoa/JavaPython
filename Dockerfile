# Use the official PostgreSQL image
FROM postgres:latest

# Set the PostgreSQL environment variables (you can customize these)
ENV POSTGRES_USER myuser
ENV POSTGRES_PASSWORD mypassword
ENV POSTGRES_DB mydatabase

