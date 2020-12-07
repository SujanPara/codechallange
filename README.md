##Make sure you have install following
```
Mysql
 username: root
 password: password

Java 8
Maven
```
##Git repo
```
git clone https://github.com/SujanPara/codechallange.git
```

## Running application
Execute following command from project root directory
```
mvn clean install liquibase:update package
mvn package spring-boot:run
```

## Building the application
Execute following command from project root directory

```
mvn clean install
mvn clean install -DskipTests=true
mvn clean liquibase:update package
```
## Postman collections & endpoints list
/resources/postman_collection/CodeChallange.postman_collection.json

