> If you find there are something which could be improved, please create a Merge Request. 
> Thank you so much!

# I. Introduction

This project shows example code to connect between client app and server app via REST API.

Technical stack:
Spring Boot
Spring Cloud Feign: for client app connects to server app conveniently.
Spring Rest Doc: generate API documents for server


You can import the whole project with all modules into your IDE, or you can import individual modules separately.

1.  `pro01-simple`: The simple connection between `client` & `server`. There's no handler for Http Headers.

# II. Build projects
Run the command line, it will complie the source code, build project, and then run tests.
```
mvn clean install 
```

If you want to build without testing, run the command line:
```
mvn clean install -DskipTests 
```

View the API Documents:
Open this URL on the browser: http://localhost:8081/swagger-ui.html
