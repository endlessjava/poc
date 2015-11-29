package com.endless.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.endless.service.PocService", serviceName = "POCService", portName = "POCServicePort", targetNamespace = PocService.NAMESPACE)
public class PocServiceImpl implements PocService {

    @WebMethod
    public void helloWorld() {
        System.out.println("Hello World");
    }
}
