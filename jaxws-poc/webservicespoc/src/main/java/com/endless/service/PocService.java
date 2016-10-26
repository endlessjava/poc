package com.endless.service;

import javax.jws.WebService;

@WebService(name = "POC", targetNamespace = PocService.NAMESPACE)
public interface PocService {

    public static String NAMESPACE = "http://com.endless/poc";

    public void helloWorld();
}
