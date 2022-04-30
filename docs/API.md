# Rest API Documentation

- [Rest API Documentation](#rest-api-documentation)
  - [Users](#users)
    - [/api/v1/users/new](#apiv1usersnew)
    - [/api/v1/users/login](#apiv1userslogin)
    - [/api/v1/users/token/{token}](#apiv1userstokentoken)
  - [Projects](#projects)
    - [/api/v1/projects/{username}](#apiv1projectsusername)
    - [/api/v1/projects/{username}/latest](#apiv1projectsusernamelatest)
    - [/api/v1/projects](#apiv1projects)
    - [/api/v1/projects/{id}](#apiv1projectsid)
    - [/api/v1/projects/{username}/{projectName}](#apiv1projectsusernameprojectname)
    - [/api/v1/projects/{username}/count](#apiv1projectsusernamecount)
    - [/api/v1/projects/{username}/count/private](#apiv1projectsusernamecountprivate)
  - [Issues](#issues)
    - [/api/v1/issues/{username}/latest](#apiv1issuesusernamelatest)
    - [/api/v1/issues/{username}/{projectName}](#apiv1issuesusernameprojectname)
    - [/api/v1/issues/{id}](#apiv1issuesid)
    - [/api/v1/issues](#apiv1issues)
    - [/api/v1/issues/{id}](#apiv1issuesid-1)
    - [/api/v1/issues/{username}/count](#apiv1issuesusernamecount)
    - [/api/v1/issues/{username}/count/closed](#apiv1issuesusernamecountclosed)
    - [/api/v1/issues/{username}/count/open](#apiv1issuesusernamecountopen)

<hr>

## Users

### /api/v1/users/new
Adds a new user to the databse. (registration)  
If the username or the email is taken, throws and error. (409)
- **Method:** POST
- **Request:** A user object, see below.
  - ```json
    {
        "username": "John",
        "email": "johndoe@gmail.com",
        "password": "johndoe"
    }
    ```
- **Response:** -

### /api/v1/users/login
Check if the user is in the database, if so, checks if the given password is corrent.  
If the user with the given ID does not exist, throws an error. (404)  
If the user exists, and the password is incorrenct, throws an error. (401)
- **Method:** POST
- **Request:** A username and a password
  - ```json
    {
        "username": "John",
        "password": "johndoe"
    }
    ```
- **Response:** The token
  - ```json
    {
        "username": "johndoe",
        "token": "nh_u3xhI#UJFN$#37Fbn#jhb319p8n-FJNf$uhsf"
    }
    ```

### /api/v1/users/token/{token}
Returns a user with the given token.
- **Method:** GET
- **Response:** The user with the specified token
  - ```json
    {
        "id": "1",
        "username": "johndoe",
        "email": "johndoe@gmail.com",
        "reg_date": "2022-04-15"
    }
    ```

<hr>

## Projects

### /api/v1/projects/{username}
Returns the project this user has.  
If the user is not authenticated, it will return the public projects.  
If the user is authenticated, it will return all of the projects.
- **Method:** GET
- **Response:** If authenticated, then all of the projects, else just the public ones
  - ```json
    [
        {
            "id": 1,
            "username": "test",
            "projectName": "project-1",
            "projectDescription": "This is a cool project.",
            "isPublic": true
        },
        {
            "id": 2,
            "username": "test",
            "projectName": "project-2",
            "projectDescription": "This is a cool project 2.",
            "isPublic": false
        }
    ]
    ```

### /api/v1/projects/{username}/latest
Returns the lates three project the user has.
- **Method:** GET
- **Auth:** Yes
- **Response:** 
  - ```json
    [
        {
            "id": 1,
            "username": "test",
            "projectName": "project-1",
            "projectDescription": "This is a cool project.",
            "isPublic": true
        },
        ...
    ]
    ```

### /api/v1/projects
Adds a project to the database.
- **Method:** POST
- **Auth:** Yes
- **Request:** A project object
  - ```json
    {
        "username": "test",
        "projectName": "project-1",
        "projectDescription": "This is a cool project.",
        "isPublic": true
    }
    ```
- **Response:** -

### /api/v1/projects/{id}
Deletes a project from the database.  
Only the owner of the project can delete it.
- **Method:** DELETE
- **Auth:** Yes
- **Response:** -

### /api/v1/projects/{username}/{projectName}
Returns a project from the database.  
If the project is public, then authenticaion is not required. Else it is.
- **Method:** GET
- **Response:** The project with the specified username and projectName.
  - ```json
    {
        "id": 1,
        "username": "test",
        "projectName": "project-1",
        "projectDescription": "This is a cool project.",
        "isPublic": true
    }
    ```

### /api/v1/projects/{username}/count
Returns the number of project the given username has.
- **Method:** GET
- **Response:** 
  - ```json
    {
        "count": 7,
    }
    ```

### /api/v1/projects/{username}/count/private
Returns the number of private project the given username has.  
- **Method:** GET
- **Response:** 
  - ```json
    {
        "count": 3,
    }
    ```

<hr>

## Issues

### /api/v1/issues/{username}/latest
Returns the latest 3 issues this user has.
- **Method:** GET
- **Auth:** Yes
- **Response:** 
  - ```json
    [
        {
            "id": 1,
            "projectOwner": "test",
            "projectName": "test",
            "issueTitle": "Test issue",
            "issueDescription": "Issue description",
            "isOpen": true,
            "openedBy": "test",
            "opened": "2022-02-02"
        },
        {
            "id": 2,
            "projectOwner": "test",
            "projectName": "test",
            "issueTitle": "Test issue",
            "issueDescription": "Issue description",
            "isOpen": false,
            "openedBy": "test",
            "closedBy": "john",
            "opened": "2022-02-02",
            "closed": "2022-02-03"
        }
    ]
    ```

### /api/v1/issues/{username}/{projectName}
- **Method:** GET
- **Auth:** If the project is private, the auth is needed.
- **Response:** The issues the project has.
  - ```json
    [
        {
            "id": 1,
            "projectOwner": "test",
            "projectName": "test",
            "issueTitle": "Test issue",
            "issueDescription": "Issue description",
            "isOpen": true,
            "openedBy": "test",
            "opened": "2022-02-02"
        },
        {
            "id": 2,
            "projectOwner": "test",
            "projectName": "test",
            "issueTitle": "Test issue",
            "issueDescription": "Issue description",
            "isOpen": false,
            "openedBy": "test",
            "closedBy": "john",
            "opened": "2022-02-02",
            "closed": "2022-02-03"
        }
    ]
    ```

### /api/v1/issues/{id}
Returns the issue with the given id.  
If the project the issue belongs to is private, auth is needed.
- **Method:** GET
- **Auth:** If the project is private, the auth is needed.
- **Response:** The requested issue
  - ```json
    {
        "id": 1,
        "projectOwner": "test",
        "projectName": "test",
        "issueTitle": "Test issue",
        "issueDescription": "Issue description",
        "isOpen": true,
        "openedBy": "test",
        "opened": "2022-02-02"
    }
  ```

### /api/v1/issues
Adds a new issue to the database.  
If the project is private, auth is needed.
- **Method:** POST
- **Auth:** Only if the project is private
- **Request:** An issue object.
  - ```json
    {
        "id": 2,
        "projectOwner": "test",
        "projectName": "test",
        "issueTitle": "Test issue",
        "issueDescription": "Issue description",
        "isOpen": false,
        "openedBy": "test",
        "closedBy": "john",
        "opened": "2022-02-02",
        "closed": "2022-02-03"
    }
    ```
- **Response:** -

### /api/v1/issues/{id}
Deletes an issue from the database.
- **Method:** DELETE
- **Auth:** Yes
- **Response:** -

### /api/v1/issues/{username}/count
Returns the amount of issues the user has ever opened
- **Method:** GET
- **Auth:** No
- **Response:** Count object
  - ```json
    {
        "count": 12,
    }
    ```
### /api/v1/issues/{username}/count/closed
Returns the amount of closed issues the user has.
- **Method:** GET
- **Auth:** No
- **Response:** Count object
  - ```json
    {
        "count": 8,
    }
    ```

### /api/v1/issues/{username}/count/open
Returns the amount of open issues the user has.
- **Method:** GET
- **Auth:** No
- **Response:** Count object
  - ```json
    {
        "count": 4,
    }
    ```
