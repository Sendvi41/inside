# Test Task from inside company
This project is a test task from the company "inside".

##  🚀 Getting Started
This section provides a high-level quick start guide

### Prerequisites
- [Docker Desktop](https://docs.docker.com/desktop/) 4.3.0
- [Java](https://www.oracle.com/java/technologies/) 11
- [Maven](https://maven.apache.org/) 3.8.3
- [PostgreSQL](https://www.postgresql.org/) 13.4 or higher
- [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/)

**Step 1. Set up environment variables**
- ```DB_URL```  - url to postgres
- ```DB_USER```  - postgres user 
- ```DB_PASS```  - postgres password

**Step 2. Configure DB**

You can configure DB on your local machine, or you can use Docker to run DB-instance

*Configure local on your local machine*

Create DB ```insideDB``` on 5432 port

*Configure local using Docker*

Install Docker to launch environment for application
- Use the command ```docker-compose up``` in terminal
- You can also run the file```docker-compose.yml``` in project's folder

Default environment of docker-compose
- `POSTGRES_PASSWORD`: password
- `POSTGRES_USER`: postgres
- `POSTGRES_DB`: insideDB

**Step 3. Build app**
> mvn compile

**Step 4. Run**

_Idea_:
- create SpringBoot configuration
- set up environment variables to configuration
- run

### Examples of curl requests
You can find example-request in file ```curl-requests.txt```

### Mocks
Application has 2 user mocks in DB to test functionality easier.

Name| Password
------|------------
IVANCOOL | 0000
Petr1234| 0000

### API

> POST http://{URL}/token

This endpoint is needed to get the JWT token


*Request body*
```javascript
{
    "name": "IVANCOOL",
    "password": "0000"
}
```

*Response body*

```javascript
{
     "token": "yQVml-quhuGD76YxEuyJztw"
}
```

*Responses*

Code| Description
------|------------
200 Ok | The token has been received
400 Bad Request| User name or password entered incorrectly

> POST http://{URL}/user/message

Adding a message to a user

*Headers*

Authorization: `Bearer_`{your token}

*Request body*
```javascript
{
    "name": "IVANCOOL",
    "message": "Hello guys"
}
```

*Response body*

```javascript
{
    "messageId": "9",
    "status": "SUCCESS"
}
```

*Responses*

Code| Description
------|------------
200 Ok | Message was added
401 Unauthorized| JWT Token is invalid
404 Not found| User is not found

> POST http://{URL}/messages

Getting a certain number of messages for a user

Pattern of Item `amount`:  `history `number

Examples: `history 1` `history 5` `history 10`

*Headers*

Authorization: `Bearer_`{your token}

*Request body*
```javascript
{
    "name": "IVANCOOL",
    "amount": "history 10"  
}
```

*Response body*

```javascript
[
    {
        "id": 10,
        "message": "Hello guys 9",
        "creationDate": "2022-05-15T05:31:30.520676"
    }
]
```

*Responses*

Code| Description
------|------------
200 Ok | Messages received
401 Unauthorized| JWT Token is invalid
400 Bad Request| Pattern of message isn't correct. Correct format 'history 10'
404 Not found| User doesn't exist with name {NAME}
