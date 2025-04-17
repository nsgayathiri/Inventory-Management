# Inventory Management System

A simple inventory management system using Java and MySQL.

## Overview

The **Inventory Management System** is a Java-based console application that enables both administrators and users to interact with an inventory of products.

This system supports interaction with a **MySQL database** using **JDBC** for storing and retrieving product and user data. The application relies on the **MySQL connector (JAR file)** to establish a connection between Java and the MySQL database.

---

## Key Features

### Admin Operations:
- Add new products and restock existing ones.
- View and manage inventory.
- View customer purchase details.
- View out-of-stock products.
- Generate monthly and yearly profit/loss reports.
- View customer bills and manage transactions.

### User Operations:
- New user registration.
- Login for existing users.
- Purchase products based on available inventory.

---

## Prerequisites

- **Java Development Kit (JDK)** installed.
- **MySQL database** set up.
- **MySQL Connector JAR file** (`mysql-connector-j-9.2.0.jar`).

---

## Compile and Run Commands

To compile and run the application, follow these steps:

### 1. Compile the Code

Run the following command to compile the Java source files:

```bash
javac -cp ".;lib/mysql-connector-j-9.2.0.jar" -d bin src/*.java
```
### 2. Run the Code

After successful compilation, run the application using the following command:
```bash
java -cp ".;lib/mysql-connector-j-9.2.0.jar;bin" Main
```


