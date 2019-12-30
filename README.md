# Logger Utils

A library to allow applications to consistently log requests & responses.
This library provides
* A filter **RequestLoggerFilter** that logs Request start/end details
* Mechanism for reading and generating a unique identifier(**traceId**) for requests
* Consistent logging grammar

### Diagrams 
* ToDo

## Development
**ALL DEVELOPMENT SHOULD INCLUDE NEW/UPDATED TESTS**

* ToDo 
* ToDo 

### Project Structure
| Directory           | Description     |
| ------------------- |:---------------:|
|./src/test           | Test source code|
|./src/test/resources | Test related resources: log4j2-test.xml|
|./src/main           | Application source code|

### Build Locally
* Executing `mvn clean install` compiles, runs unit tests and creates the below jar file to be used by other applications:
    * Executable jar e.g: *target/logger-utils-...jar*

### Usage of the library in other Modules/Applications
To make use of the library in applications, follow the below steps:

* Add the dependency to the pom.<br/>
	`<dependency>`<br/>
        &nbsp;&nbsp;&nbsp;`<groupId>com.jn.projects.servet.utils</groupId>`<br/>
        &nbsp;&nbsp;&nbsp;`<artifactId>logger-utils</artifactId>`<br/>
    	&nbsp;&nbsp;&nbsp;`<version>...</version>`<br/>
    `</dependency>`
* Add RequestLoggerFilter to the app's ServletContextHandler:
 	E.g., :<br/>
 	`ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS);`<br/>
 	`context.addFilter(RequestLoggerFilter.class, "/*", EnumSet.allOf(DispatcherType.class));`
* Make use of the logger to print logs at various levels:
 	E.g.,<br/>
 	`private static ILogger LOGGER = LoggerFactory.getLogger(Application.class);`<br/>
 	`LOGGER.info("Some message");`
* One of the main purposes of plugging in this library into an application is the usage of traceId it generates to track the requests between modules.<br/>
 	When an application makes a call to other module, add the traceId to the request header so that when the request lands in the called application, passed traceId will be read in its filter.<br/> 
 	TraceId can be obtained by calling this method of the library:<br/>
 	`String traceId = LoggerFactory.getTraceIdInstance().getTraceId();`<br/>
 	 Example usage:<br>
 	`httpGet.addHeader(LoggerConstants.HTTP_HEADER_TRACE_ID, LoggerFactory.getTraceIdInstance().getTraceId());`
 	`HttpResponse response = httpclient.execute(httpGet);`
* The implementing applications should make sure of adding log4j2.xml in the etc folder. For pattern layout grammar, please refer [log4j2-test.xml](./src/test/resources/log4j2-test.xml)<br/>
	Quick reference of patternLayout:<br/>
	`<PatternLayout pattern="%date{yyyy-MM-dd'T'hh:mm:ss.SSSXX} log_level=%-5p [%X{X-TRACEID}] - %m%n" />`

 
 