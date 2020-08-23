package webclient.soap.client;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .flatMap(
                                                errorResponseBody ->
                                                        Mono.error(
                                                                new ResponseStatusException(
                                                                        clientResponse.statusCode(),
                                                                        errorResponseBody))))

                .bodyToMono(GetCountryResponse.class)
                .doOnSuccess( (GetCountryResponse response) -> {
                    System.out.println("success");
                    System.out.println("capital : " + response.getCountry().getCapital());
                })
                .doOnError(ResponseStatusException.class, error -> {
                    System.out.println( "error : "+ error );
                })
                .subscribe();

    }



}
