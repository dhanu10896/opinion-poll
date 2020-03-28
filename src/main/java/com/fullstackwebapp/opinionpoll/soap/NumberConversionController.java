package com.fullstackwebapp.opinionpoll.soap;

import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberConversionController {
    @GetMapping("/numberToWord/{number}")
    public ResponseEntity<NumberToWordsResponse> numberToWord(@PathVariable(value = "number") Long number) {
        NumberClient numberClient = new NumberClient();

        //create and setup marshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        //provide location to the ObjectFacory
        marshaller.setContextPath("com.dataaccess.webservicesserver");

        //add marshaller to the client
        numberClient.setMarshaller(marshaller);
        numberClient.setUnmarshaller(marshaller);

        //retrieve the word format of the number
        NumberToWordsResponse response = numberClient.getWords("3454");

        return new ResponseEntity(response,HttpStatus.OK);
    }
}
