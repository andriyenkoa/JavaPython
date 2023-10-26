import unittest

import psycopg2


class DatabaseTestCase(unittest.TestCase):
    def setUp(self):
        # Establish a connection to the test database
        self.conn = psycopg2.connect(
            dbname='mydatabase',
            user='myuser',
            password='mypassword',
            host='localhost',
            port='5432',
        )
        self.cur = self.conn.cursor()

    def test_query(self):
        self.cur.execute("SELECT * FROM user_details")
        result = self.cur.fetchall()
        self.assertTrue(len(result) > 0)

    def test_query_female(self):
        self.cur.execute("SELECT * FROM user_details WHERE gender = 'Female'")
        result = self.cur.fetchall()
        self.assertTrue(len(result) > 0)

    def test_insert_and_delete(self):
        self.cur.execute("INSERT INTO user_details (user_id, username, first_name, last_name, gender, pwd, status) "
                         "VALUES (101, 'andrey', 'andrey', 'andriyenko', 'Male', '8fbfdb81391ef264ae8b0df7e7e91d45', 1)")
        self.cur.execute("SELECT * FROM user_details WHERE user_id = 101")
        result = self.cur.fetchall()
        self.assertTrue(len(result) == 1)
        self.cur.execute("DELETE FROM user_details WHERE user_id = 101")
        self.cur.execute("SELECT * FROM user_details WHERE user_id = 101")
        result = self.cur.fetchall()
        self.assertTrue(len(result) == 0)

    def test_update(self):
        self.cur.execute(
            "UPDATE user_details SET user_id = 101 WHERE user_id = 100")
        self.cur.execute("SELECT * FROM user_details WHERE user_id = 101")
        result = self.cur.fetchall()
        self.assertTrue(len(result) == 1)
        self.cur.execute(
            "UPDATE user_details SET user_id = 100 WHERE user_id = 101")

    def tearDown(self):
        # Close the cursor and connection, and clean up the test database
        self.cur.close()
        self.conn.close()
