# CodeAlpha_LibraryManagementSystem
# CodeAlpha - Library Book Management System

A robust console-based Library Management System developed in Java that demonstrates Core OOP concepts, custom exception handling, collection indexing, and file persistence.

## Features
- **Catalog Management:** Add, delete, and view books with ISBN indexing.
- **Search Engine:** Filter by book title, author, or ISBN.
- **Circulation Management:** Issue and return workflows with overdue fine calculations ($2.50/day after 7 days).
- **Custom Exceptions:** Prevents double-issuing and invalid catalog lookups using `BookNotFoundException` and `BookNotAvailableException`.
- **Data Persistence:** Automatic CSV import/export using `BufferedReader` and `BufferedWriter`.

## How to Run
```bash
javac LibraryManagementSystem.java
java LibraryManagementSystem
