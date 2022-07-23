# Scrumw API - v2

- [Scrumw API - v2](#scrumw-api---v2)
  - [Entities](#entities)
    - [User](#user)
    - [Project](#project)
  - [Endpoints](#endpoints)
    - [Users](#users)
      - [POST: /api/v2/users](#post-apiv2users)
      - [POST: /api/v2/login](#post-apiv2login)
    - [Projects](#projects)
      - [POST: /api/v2/projects](#post-apiv2projects)
      - [GET: /api/v2/projects/{username}](#get-apiv2projectsusername)
      - [GET: /api/v2/projects/{username}/latest](#get-apiv2projectsusernamelatest)

<hr>

## Entities

### User
```js
{
    "id":               long,
    "username":         string, // unique
    "email":            string,
    "password":         string,
    "imgUrl"            string,
    "registrationDate": datetime
}
```

### Project
```js
{
    "id":                 long,
    "owner":              string, // project owner
    "projectName":        string,
    "projectDescription": string,
    "isPublic":           boolean,
    "created":            datetime
}
```

<hr>

## Endpoints

### Users

#### POST: /api/v2/users
- Method: `POST`
- Description: `Registers a new user`
- Auth needed: `false`
- Request: `User object`
- Response: `The created user`

#### POST: /api/v2/login
- Method: `POST`
- Description: `Logs in the user`
- Auth needed: `false`
- Request: `Form: User object (username and password)`
- Response: `The access token`

### Projects

#### POST: /api/v2/projects
- Method: `POST`
- Description: `Creates a new project`
- Auth needed: `true`
- Request: `Project object`
- Response: `The created project`

#### GET: /api/v2/projects/{username}
- Method: `GET`
- Description: `Returns all the projects the user has.`
- Auth needed: `true`
- Response: `An array of the projects`

#### GET: /api/v2/projects/{username}/latest
- Method: `GET`
- Description: `Returns the latest 'n' projects the user has.`
- Auth needed: `true`
- Query param: `'limit': number, optional, default is 3`
- Response: `An array of the projects`