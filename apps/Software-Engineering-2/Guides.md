# Guides
1. Convention
2. Checkstyle


## 1. Adding Concentional commits to the project

### Commit types
- Permitted types : 'feat', 'fix', 'perf', 'refactor', 'style', 'test', 'build', 'ops', 'docs', 'merge'

- Install and copy to .git/hooks
```shell
npm install --global git-conventional-commits 
cp commit-msg .git/hooks/commit-msg
```

## 2. Checkstyle:
 ```shell
mvn checkstyle:checkstyle  
```
```
mvn checkstyle:checkstyle   | grep "Checkstyle"
```
Produces a check style report in target/checkstyle-result.xml and opens it
```shell
mvn site
open target/site/checkstyle.html 

```
## 3. SonarQube:

```
export sonarlogintokken=.....................
```
 ```
 mvn verify sonar:sonar -Dsonar.login=$sonarlogintokken    
 ```

Link to Swagger UI:(after login)
http://localhost:8080/swagger-ui/index.html