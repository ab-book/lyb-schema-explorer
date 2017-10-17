package ares.remoting.test;

/**
 * @author liyebing created on 16/10/5.
 * @version $Id$
 */
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String somebody) {
        return "hello " + somebody + "!";
    }


}
