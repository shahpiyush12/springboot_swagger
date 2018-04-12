Spring Boot with Jersey and Swagger Example
=============================================
This is an example on how to integrate Spring Boot, with Jersey, Swagger, and Swager UI.  

### Changes Needed

#### Swagger Configuration
You need the following swagger dependency:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-jersey2-jaxrs</artifactId>
    <version>${swagger.jersey2.version}</version>
</dependency>
```
Here are the Swagger configuration changes required to generate the 
`swagger.json` by the `io.swagger:swagger-jersey2-jaxrs` library:

```java
@Configuration
public class JerseyConfiguration extends ResourceConfig {

    @Autowired
    public JerseyConfiguration() throws UnknownHostException {
        register(BookController.class);
        configureSwagger();
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

    public void configureSwagger() {
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger");
        config.setTitle("Spring, Jersey, and Swagger Example");
        config.setVersion("1.0.0");
        config.setBasePath("/");
        config.setResourcePackage("com.pge");
        config.setScan(true);
    }
}
```

#### Swagger UI Static Resources
1. Download the Swagger UI static resources from [here](https://github.com/swagger-api/swagger-ui)
1. Copy the downloaded resources to `/target/classes/static` folder.
1. Rename the `index.html` in `/target/classes/static` to `swagger-ui.html`.
1. Replace the `http://petstore.swagger.io/v2/swagger.json` string in
`swagger-ui.html` to `swagger.json`.

In this example, all these changes are executed dynamically during the 
build process by the plugins specified in `pom.xml`.

By default, Spring Boot configures the Jersey servlet container as a [servlet](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-jersey).

> By default Jersey will be set up as a Servlet in a @Bean of type ServletRegistrationBean named jerseyServletRegistration. You can disable or override that bean by creating one of your own with the same name. You can also use a Filter instead of a Servlet by setting spring.jersey.type=filter (in which case the @Bean to replace or override is jerseyFilterRegistration).

It needs to be changed into a filter in order to pickup the Swagger static 
resources. It can be achieved by modifying the `application.yml`file:

```yaml
spring:
  jersey:
    type: filter
```

You also need the following dependency to view `swagger-ui.html`, 

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>4.3.12.RELEASE</version>
</dependency>
```

### Build
To build the JAR, execute the following command from the parent directory:

```
mvn clean install
```

### Run
To run the application fromm command line,

```
java -jar target/springboot-jersey-swagger-1.0.0.jar
```

### Access Swagger Endpoints

##### Swagger UI
You can view the Swagger UI at `http://localhost:8080/swagger-ui.html`.

![](./img/swagger-ui.png)

##### Swagger JSON
You can view Swagger JSON doc at `http://localhost:8080/swagger.json`


http://localhost:8080/books/ae291f04-8b5d-4058-853b-db3f43e22e96
