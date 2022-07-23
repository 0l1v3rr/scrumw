# Scrumw API - v2

- [Scrumw API - v2](#scrumw-api---v2)
  - [Entities](#entities)
    - [User](#user)
    - [Project](#project)
  - [Endpoints](#endpoints)
    - [Users](#users)
      - [GET: /api/v2/users/{username}](#get-apiv2usersusername)
      - [POST: /api/v2/users](#post-apiv2users)
      - [POST: /api/v2/login](#post-apiv2login)
    - [Projects](#projects)
      - [GET: /api/v2/projects/{username}](#get-apiv2projectsusername)
      - [GET: /api/v2/projects/{username}/latest](#get-apiv2projectsusernamelatest)
      - [GET: /api/v2/projects/{username}/{projectName}](#get-apiv2projectsusernameprojectname)
      - [GET: /api/v2/projects/id/{projectId}](#get-apiv2projectsidprojectid)
      - [POST: /api/v2/projects](#post-apiv2projects)
      - [PUT: /api/v2/projects/{projectId}](#put-apiv2projectsprojectid)
      - [PATCH: /api/v2/projects/{projectId}](#patch-apiv2projectsprojectid)
      - [DELETE: /api/v2/projects/{projectId}](#delete-apiv2projectsprojectid)

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

#### GET: /api/v2/users/{username}
- Method: `GET`
- Description: `Returns the found user with the given username.`
- Auth needed: `false`
- Response: `The found user`

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

#### GET: /api/v2/projects/{username}/{projectName}
- Method: `GET`
- Description: `Returns the found project by the given username and projectName.`
- Auth needed: `true`
- Response: `The found project.`

#### GET: /api/v2/projects/id/{projectId}
- Method: `GET`
- Description: `Returns the found project by the given id.`
- Auth needed: `true`
- Response: `The found project.`

#### POST: /api/v2/projects
- Method: `POST`
- Description: `Creates a new project`
- Auth needed: `true`
- Request: `Project object`
- Response: `The created project`

#### PUT: /api/v2/projects/{projectId}
- Method: `PUT`
- Description: `Updates a new project`
- Auth needed: `true`
- Request: `Project object (only the fields you want to update)`
- Response: `The updated project`

#### PATCH: /api/v2/projects/{projectId}
- Method: `PATCH`
- Description: `Changes the visibility of the provided project to its opposite`
- Auth needed: `true`
- Response: `The updated project`

#### DELETE: /api/v2/projects/{projectId}
- Method: `DELETE`
- Description: `Deletes the project with the given ID`
- Auth needed: `true`