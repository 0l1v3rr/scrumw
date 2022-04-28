# Rest API Documentation

- [Rest API Documentation](#rest-api-documentation)
  - [/api/v1/users](#apiv1users)
  - [/api/v1/projects](#apiv1projects)
  - [/api/v1/issues](#apiv1issues)

<hr>

## /api/v1/users

```js
// URL       : /api/v1/users/new
// Method    : POST
// Auth      : No
// Response  : -

// Req. body example:
{
    "username": "John",
    "email": "johndoe@gmail.com",
    "password": "johndoe"
}
```

```js
// URL       : /api/v1/users/login
// Method    : POST
// Auth      : No
// Response  : The users' token

// Req. body example:
{
    "username": "John",
    "password": "johndoe"
}

// Response example: 
{
    "username": "johndoe",
    "token": "nh_u3xhI#UJFN$#37Fbn#jhb319p8n-FJNf$uhsf"
}
```

```js
// URL       : /api/v1/users/token/{token}
// Method    : GET
// Auth      : No
// Response  : The user with the specified token

// Response example: 
{
    "id": "1",
    "username": "johndoe",
    "email": "johndoe@gmail.com",
    "reg_date": "2022-04-15"
}
```

<hr>

## /api/v1/projects
```js
// URL       : /api/v1/projects/{username}
// Method    : GET
// Auth      : Optional
// Response  : If authenticated, then all of the projects, else just the public ones

// Response example: 
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

```js
// URL       : /api/v1/projects
// Method    : POST
// Auth      : Yes
// Response  : -

// Req. body example: 
{
    "username": "test",
    "projectName": "project-1",
    "projectDescription": "This is a cool project.",
    "isPublic": true
}
```

```js
// URL       : /api/v1/projects/{id}
// Method    : DELETE
// Auth      : Yes
// Response  : -
```

```js
// URL       : /api/v1/projects/{username}/{projectName}
// Method    : GET
// Auth      : Optional
// Response  : The project with the specified username and projectName.

// Response example: 
{
    "id": 1,
    "username": "test",
    "projectName": "project-1",
    "projectDescription": "This is a cool project.",
    "isPublic": true
}
```

```js
// URL       : /api/v1/projects/{username}/count
// Method    : GET
// Auth      : Yes
// Response  : The amount of the projects the provided username has.

// Response example: 
{
    "count": 7,
}
```

```js
// URL       : /api/v1/projects/{username}/count/private
// Method    : GET
// Auth      : Yes
// Response  : The amount of the private projects the provided username has.

// Response example: 
{
    "count": 3,
}
```

## /api/v1/issues

```js
// URL       : /api/v1/issues/{username}/latest
// Method    : GET
// Auth      : Yes
// Response  : The latest 3 issues the user has.
```

```js
// URL       : /api/v1/issues/{username}/{projectName}
// Method    : GET
// Auth      : If the project is empty, no auth.
// Response  : The latest issues the project has.
```

```js
// URL       : /api/v1/issues/
// Method    : POST
// Auth      : No
// Req body  : An Issue object.
```

```js
// URL       : /api/v1/issues/{id}
// Method    : DELETE
// Auth      : Yes
```