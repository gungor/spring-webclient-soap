package webclient.soap.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapEnvelopeRequest {

    private String headerContent;
    private Object body;

    public SoapEnvelopeRequest(String headerContent, Object body) {
        this.headerContent = headerContent;
        this.body = body;
    }

    public SoapEnvelopeRequest(Object body) {
        this.body = body;
    }

    public String getHeaderContent(){
        return headerContent;
    }

    public Object getBody() {
        return body;
    }
}
