# Rest API Documentation

<hr>

## /api/v1/users

```json
URL       : /api/v1/users/new
Method    : POST
Auth      : No
Response  : -

Req. body example:
{
    "username": "John",
    "email": "johndoe@gmail.com",
    "password": "johndoe"
}
```

```json
URL       : /api/v1/users/login
Method    : POST
Auth      : No
Response  : The users' token

Req. body example:
{
    "username": "John",
    "password": "johndoe"
}

Response example: 
{
    "username": "johndoe",
    "token": "nh_u3xhI#UJFN$#37Fbn#jhb319p8n-FJNf$uhsf"
}
```
