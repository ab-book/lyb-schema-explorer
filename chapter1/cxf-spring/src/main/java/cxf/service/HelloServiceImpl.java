package cxf.service;

import javax.jws.WebService;

/**
 * @author liyebing created on 16/12/4.
 * @version $Id$
 */
@WebService(endpointInterface = "cxf.service.HelloService")
public class HelloServiceImpl implements HelloService {

    public String sayHello(String content) {
        return "hello," + content;
    }
}
