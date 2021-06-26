package webclient.soap.controller;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import org.springframework.web.bind.annotation.*;
import webclient.soap.client.ReactiveSoapClient;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@RestController
public class Controller {

    private ReactiveSoapClient reactiveSoapClient;

    public Controller(ReactiveSoapClient reactiveSoapClient) {
        this.reactiveSoapClient = reactiveSoapClient;
    }

    @PostMapping(value = "/countrydetails/{country}")
    public void callSoap(@PathVariable String country) throws SOAPException, ParserConfigurationException, IOException {
        System.out.println("country : "+ country);
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);
        reactiveSoapClient.call( request, null );
    }

    @PostMapping(value = "/countrydetailsWithHeader/{country}")
    public void callSoapWithHeader(@PathVariable String country) throws SOAPException, ParserConfigurationException, IOException {
        System.out.println("country : "+ country);
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);

        String headerContent = "<aut:usernamePassword xmlns:aut=\"Authentication\" aut:user=\"test\" aut:password=\"pw\" />";

        reactiveSoapClient.call( request, headerContent );
    }
}
