package cxf.service;

import javax.jws.WebService;

/**
 * @author liyebing created on 16/12/4.
 * @version $Id$
 */
@WebService
public interface HelloService {

    public String sayHello(String content);

}
