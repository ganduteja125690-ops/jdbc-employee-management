# jdbc-employee-management
JDBC application to manage employee records using Statement and PreparedStatement

## Overview
This project demonstrates a Java JDBC application for managing employee records in a MySQL database. It includes examples of:
- Using **Statement** object to retrieve all employee records
- Using **PreparedStatement** object to insert new employee records

## Database Schema

### Table: `emp`
```sql
CREATE TABLE emp (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  city VARCHAR(50),
  age INT
);
```

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Database Server
- MySQL JDBC Driver (mysql-connector-java)
- IDE (Eclipse, IntelliJ IDEA, or NetBeans) or command line

## Setup Instructions

### 1. Create the Database
```sql
CREATE DATABASE YOUR_DATABASE;
USE YOUR_DATABASE;

CREATE TABLE emp (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  city VARCHAR(50),
  age INT
);
```

### 2. Configure Database Connection
Update the following variables in `EmployeeJDBCApp.java`:
```java
String url = "jdbc:mysql://localhost:3306/YOUR_DATABASE"; // Replace YOUR_DATABASE
String user = "YOUR_USERNAME";                           // Replace YOUR_USERNAME
String password = "YOUR_PASSWORD";                       // Replace YOUR_PASSWORD
```

### 3. Add MySQL JDBC Driver
- Download MySQL Connector/J from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
- Add the JAR file to your project classpath

### 4. Compile and Run
```bash
javac EmployeeJDBCApp.java
java EmployeeJDBCApp
```

## Features

### a) Display All Employee Records (Statement)
The application uses a `Statement` object to execute a SELECT query and display all employee records:
```java
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
```

### b) Insert Employee Record (PreparedStatement)
The application uses a `PreparedStatement` object to safely insert a new employee record:
```java
PreparedStatement pstmt = con.prepareStatement("INSERT INTO emp (id, name, city, age) VALUES (?, ?, ?, ?)");
pstmt.setInt(1, 101);
pstmt.setString(2, "John Doe");
pstmt.setString(3, "Delhi");
pstmt.setInt(4, 25);
pstmt.executeUpdate();
```

## Sample Output
```
All Employee Records:
101	John Doe	Delhi	25
Employee inserted successfully!
```

## Notes
- PreparedStatement is preferred over Statement for parameterized queries as it prevents SQL injection attacks
- The application uses try-with-resources to automatically close database connections
- Make sure MySQL server is running before executing the application

## License
This project is created for educational purposes.
