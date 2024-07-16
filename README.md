# Library Management
This project implements a Tag Library Management System designed to simplify the search for books based on their tags. Each book is identified by a unique ISBN (International Standard Book Number) and categorized with various tags such as Fantasy, Science, Drama, etc.

## Features
The application is built on top of a PostgreSQL database and supports the following functionalities:

### 1. Store Command
The store command is used to add a new book to the library or update the tags of an existing book. If the store command is used on an existing book, all the tags of that book are updated by replacing the old tags with the new ones.

Requirements:
  - The ISBN must be a 13-digit numeric string.
  - Each book must have at least one tag.
  - Endpoints:
#### POST 
http://localhost:9090/store
    Body:
    {
        "isbn": "1111111111111",
        "tags": ["Science", "Fiction"]
    }
    
### 2. Search Command
The search command retrieves all books that contain a specific tag or a list of tags. For a book to be included in the results, it must contain all the tags provided in the search command.

Requirements:
  - There must be at least one tag provided.
  - Endpoints:

#### POST 
http://localhost:9090/searchByTag
    Body:
    {
        "tags": ["Science"]
    }

## Getting Started
To get started with the project, follow these steps:
  1. Clone the repository:  git clone https://github.com/yourusername/library-management.git
  2. Navigate to the project directory: cd library-management
  3. Set up your PostgreSQL database and configure the application properties accordingly.
  4. Build and run the application.

## Running Tests
To run the tests, use the following command:  mvn test (Using Maven)
