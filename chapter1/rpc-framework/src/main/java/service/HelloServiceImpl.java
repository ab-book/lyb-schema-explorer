package service;

/**
 * @author liyebing created on 16/12/4.
 * @version $Id$
 */
public class HelloServiceImpl implements HelloService {

    public String sayHello(String content) {
        return "hello," + content;
    }


}
