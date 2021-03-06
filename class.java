package com.xxxxxxx;
import javax.xml.soap.*;

// jdk 1.6
public class Main {

    // SAAJ - SOAP Client Testing
    public static void main(String args[]) {

        String soapEndpointUrl = "http://..... .svc";

        // lo obtenemos del WSDL
        String soapAction = "http://tempuri.org/....";

        callSoapWebService(soapEndpointUrl, soapAction);
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        
        SOAPElement soapBodyElem = soapBody.addChildElement("MetodoAEjecutar", myNamespace);
          // defino variables 
          SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("endpoint", myNamespace);
                                      // public ws de ejemplo para testear
          soapBodyElem1.addTextNode("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/ListOfCountryNamesByCode");
          
          SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("", myNamespace);
          soapBodyElem1.addTextNode(" ");
    }

    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();

        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}
