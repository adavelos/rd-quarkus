# Reference Data Application (rd-app)

## Quarkus Version
This codebase contains an implementation of the application using the Quarkus framework.

## Instructions
1. Build: `mvn clean install`
2. Run 'RD Entry Service': `mvn clean install -pl services/entry/service quarkus:dev`

> Skip unit tests: `-DskipTests=true`

3. Debug: default port = 5005
4. Multi-Module: in order to integrate with Quarkus, a 'beans.xml' must be added on the 3rd party module

## PostgreSQL in Quarkus DEV Mode:

  - when run in DEV mode (quarkus:dev), the 'testcontainers' automatically starts a PGSQL instance
  - connect to the PGSQL docker instance interactively: `docker exec -it [IMAGE_NAME] /bin/bash`
  - login as *postgres*: `su - postgres`
  - user name is *quarkus* and database name is *postgres*
    - connect command: `psql -d postgres -U quarkus`
    - connect to *default* schema: `\c default`
    - run: `select * from entry`

## Docker

### Manually
- Create Network
  - `docker network create rdapp_network`
- Build Entry Service image:
  - `cd services/entry/service` 
  - `docker build --tag rd_quarkus_entry -f src\main\docker\Dockerfile.jvm .`
- Build PostgreSQL image:
  - The customized image is an extension of the standard PGSQL image, plus it executes the init SQL scripts to create user & schema  
  - `cd db/postgres` 
  - `docker build --tag=postgres_rdapp -f Dockerfile`
- Run PostgreSQL:
  - `docker run -v pgdata:/var/lib/postgresql/data --network rdapp_network --rm --hostname postgres --name postgres_rdapp -e POSTGRES_PASSWORD=password -d -p 5432:5432 rd_postgres_rdapp`
- Run Entry Service:
  - `docker run --network rdapp_network --rm --name rd_quarkus_entry_instance  -p 8080:8080 -p 5005:5005 rd_quarkus_entry`
- Change application and re-build image
  - implement code changes 
  - run `maven clean install` on the changed service (i.e. entry/service)
  - re-build Docker image
  - stop running docker instance / start a new instance

### Docker Compose

**Steps**:
- from the root directory
- build application: `mvn clean install`
- build all images: `docker compose build`
- start all containers: `docker compose up`
- stop all containers: `docker compose down`

> ***Attention***: in order to maintain data, the application must not include the '*drop-and-create*' instruction in hibernate properties

### Postgres

Debug:
- `docker exec -it rd-quarkus-postgres_rdapp-1 /bin/bash`
- `su - postgres`
- `psql -d rd_app -u rd_user`

DB Change:
- Each time the image starts, it automatically detects that the database has been created and does not create it again
- If we need to re-create the DB (because schema is updated):
  - Delete docker volume: `docker volume rm rd-quarkus_rdpgdata`
  - Re-Build Images (for changed scripts to take effect): `docker compose build`
  - Re-Start: `docker compose up`
  - The volume will be recreated automatically

## Rest Client

### Micro Profile REST Client
1. Client JAR: Define an interface with the API annotated with `@RegisterRestClient` and the same relative path `@Path(...)`
2. Standalone JAR: Run standalone mode (`@QuarkusMain` invokes a class that extends `@QuarkusApplication`
3. Inject the entry client into the test class using the `@RestClient` qualifier: there is no need for implementation class - quarkus will generate an implementation
4. Standalone JAR Config: define the URL of the REST API. Attention: the URL must not include the path of the REST resource:
  - `quarkus.rest-client."client.interface.full.class.name".url=http://localhost:8080`
> the double quotes in the property are required, otherwise the property will not be recognized by the MP REST Client
6. Standalone JAR Config: disable server mode `quarkus.http.host-enabled=false`
7. Define a client exception mapper to handle error status codes. Implements: `ResponseExceptionMapper<Exception>`. 
Declare the exception mapper in the REST client interface: `@RegisterProvider(ClientExceptionMapper.class)`

### JAX-RS REST Client
1. Inject a 'javax.ws.rs.client.Client' 
2. Synchronous Call: : 
```
   String uri = String.format("%s/%s/%s", url, entity, entry);
   EntryJso entry = client.target(uri)
      .request()
      .get(EntryJso.class);
```

3. Asynchronous Call:
```
    CompletionStage<List<EntryJso>> getEntry(String entity, String entry) {
        String uri = String.format("%s/%s/%s", url, entity, entry);
        return client.target(uri)
                .request()
                .rx()
                .get(new GenericType<>() {
                });
    }
```

### PICO CLI

- Quarkus provides an extension of PICO CLI tailored to Quarkus ecosystem
- Entry point is a `@Command` which implements either `Runnable` or `Callable`
- Command Line Arguments are of 2 types:
  - Option Parameters: `exec -c=param`
  - Positional Parameters: `exec param1 param2`
  - Options and parameters are mapped to automatically resolved standard types (String, int, File etc.)
- Options can be clustered: `exec -cvf`; in that case, options are mapped to boolean

![](https://picocli.info/images/OptionsAndParameters2.png)