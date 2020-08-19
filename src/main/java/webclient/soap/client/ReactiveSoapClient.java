package webclient.soap.client;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@Component
public class ReactiveSoapClient {

    private WebClient webClient;
    private String soapServiceUrl;

    public ReactiveSoapClient(WebClient webClient,
                              @Value("${soap.service.url}") String soapServiceUrl) {
        this.webClient = webClient;
        this.soapServiceUrl = soapServiceUrl;
    }

    public void call(GetCountryRequest getCountryRequest) throws SOAPException, ParserConfigurationException, IOException {

        webClient.post()
                .uri( soapServiceUrl )
                .contentType(MediaType.TEXT_XML)
                .body( Mono.just(getCountryRequest) , GetCountryRequest.class  )
                .exchange()
                .filter( (ClientResponse response) -> { return true; } )
                .doOnSuccess( (ClientResponse response) -> {
                    if( response.statusCode().is5xxServerError() ){
                        response.toEntity(String.class).doOnSuccess(
                                error -> {
                                    System.out.println("error : "+ error);
                                }).subscribe();
                    }

                    if( response.statusCode().is2xxSuccessful() ){
                        response.toEntity(GetCountryResponse.class).doOnSuccess(
                                getCountryResponse -> {
                                    System.out.println("success : "+ getCountryResponse.getBody().getCountry().getCapital());
                                }).subscribe();
                    }
                })
                .doOnError( (Throwable error) -> {
                    System.out.println( "error : "+ error );
                })
                .subscribe();

    }



}
