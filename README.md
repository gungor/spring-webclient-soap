:source-highlighter: prettify

This guide helps you run Spring WebFlux example to call SOAP services.

== Run Soap Webservice
====
[source,bash]
----
git clone https://github.com/spring-guides/gs-soap-service.git
cd gs-soap-service/complete
mvn clean install
java -Dserver.port=8081 -jar target/producing-web-service-0.0.1-SNAPSHOT.jar
----
====

== Run WebClient example
====
[source,bash]
----
git clone https://github.com/gungor/spring-webclient-soap.git
cd spring-webclient-soap
mvn clean install
java -jar target/spring-webclient-soap-1.0-SNAPSHOT.jar
----
====

== Test
====
[source,bash]
----
curl -X POST http://localhost:8080/countrydetails/Poland
curl -X POST http://localhost:8080/countrydetails/Spain
----
====

Watch logs of spring-webclient-soap, it retrieves country details and logs capital.
You can see how it works here: https://gungor.github.io/article/2020/08/23/soap-call-with-spring-webflux.html