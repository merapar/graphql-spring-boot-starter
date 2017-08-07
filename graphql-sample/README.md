# GraphQL Spring boot starter sample

This sample demonstrates some of the features of the GraphQL spring boot starter project. 

The purpose of this readme is to get you started quickly. 

The endpoint can be found at `http://localhost:8080/v1/graphql`

## Graphql clients
One example of a rest client with graphql support is [insomnia](https://insomnia.rest), [graphiql](https://github.com/graphql/graphiql) and [curl](https://curl.haxx.se/) can also be used.

## Examples

Each example starts with a graphql request, a curl command and shows the expected outcome.

### Example Hello world

Request: 

```graphql 
{
  hello
}
```

```bash
curl --request POST \
  --url http://localhost:8080/v1/graphql \
  --header 'content-type: application/json' \
  --data '{"query":"{hello}","variables":"{}"}'     
```

Expected response:

```json
{
  "data": {
    "hello": "world"
  }
}
```

### Example query roles

Request:

```graphql
query {
  roles {
    id
    name
  }
}
```

```bash
curl --request POST \
  --url http://localhost:8080/v1/graphql \
  --header 'content-type: application/json' \
  --data '{"query":"query {roles {id name}}","variables":"{}"}'
```

Expected response:

```json
{
  "data": {
    "roles": []
  }
}
```

### Example add a role

Request:

```graphql
mutation addRoleMutation {
  addRole(input: {
    id: 1
    name: "root"		
  }) {
    id
  }
}
```

```bash
curl --request POST \
  --url http://localhost:8080/v1/graphql \
  --header 'content-type: application/json' \
  --data '{"query":"mutation addRoleMutation {addRole(input: {id: 1 name: \"root\"}) {id}}","variables":"{}"}'
```

Expected response:

```json
{
  "data": {
    "addRole": {
      "id": 1
    }
  }
}
```

### Example add a user

Request:

```graphql
mutation addUserMutation {
  addUser(input: {
    id: 1234
    name: "New user"	
  }) {
    roles {
      name
    }
  }
}
```

```bash
curl --request POST \
  --url http://localhost:8080/v1/graphql \
  --header 'content-type: application/json' \
  --data '{"query":"mutation addUserMutation {addUser(input: {id: 1234 name: \"New user\"}) {roles{name}}}","variables":"{}"}'
```

Expected response:

```json
{
  "data": {
    "addUser": {
      "roles": [
        {
          "name": "Admin"
        }
      ]
    }
  }
}
```

:heavy_exclamation_mark: The response will be Admin in the roles because the sample is set-up in that way. When getting a user the role will always be Admin. Please consult the source code for all implementation details of this sample.
