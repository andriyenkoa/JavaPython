import psycopg2

# Database connection parameters
db_params = {
    "dbname": "mydatabase",
    "user": "myuser",
    "password": "mypassword",
    "host": "localhost",
    "port": "5433",
}

# Connect to the database
conn = psycopg2.connect(**db_params)
cursor = conn.cursor()

# Query to select all data from the user_details table
query = "SELECT * FROM user_details WHERE gender = 'Female';"

# Execute the query
cursor.execute(query)

# Fetch all the results
result = cursor.fetchall()

# Print the results
for row in result:
    print(row)

# Close the cursor and connection
cursor.close()
conn.close()
